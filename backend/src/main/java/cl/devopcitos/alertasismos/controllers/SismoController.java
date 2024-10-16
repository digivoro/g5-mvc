package cl.devopcitos.alertasismos.controllers;

import cl.devopcitos.alertasismos.models.Localidad;
import cl.devopcitos.alertasismos.repositories.LocalidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cl.devopcitos.alertasismos.models.Sismo;
import cl.devopcitos.alertasismos.services.SismoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sismos")
public class SismoController {

    private static final Logger logger = LoggerFactory.getLogger(SismoController.class);

    // Lista temporal para almacenar los sismos ya procesados
    private List<Sismo> sismosProcesados = new ArrayList<>();
    private static final int MAX_SISMOS_PROCESADOS = 30;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SismoService sismoService;

    @Autowired
    private LocalidadRepository localidadRepository;

    @GetMapping
    public List<Sismo> getAllSismos(){
        return sismoService.getAllSismos();
    }

    @PostMapping
    public Sismo createSismo(@RequestBody Sismo sismo){
        return sismoService.createSismo(sismo);
    }

    @GetMapping("/{id}")
    public Sismo oneSismo(@PathVariable Long id){
        return sismoService.getSismoById(id);
    }

    @PutMapping("/{id}")
    public Sismo updateSismo(@PathVariable Long id, @RequestBody Sismo sismo){
        Sismo updatedSismo = sismoService.getSismoById(id);
        updatedSismo.setLocalidad(sismo.getLocalidad());
        updatedSismo.setMagnitud(sismo.getMagnitud());
        return sismoService.createSismo(updatedSismo);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSismo(@PathVariable Long id){
        sismoService.deleteSismo(id);
        return true;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createSismo(@RequestBody List<Map<String, Object>> sismos) {
        logger.info("Datos recibidos en create-sismo: {}", sismos);

        // Preparar los headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crear la entidad que contendrá los datos del JSON
        HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(sismos, headers);

        // Hacer un POST a la API de creación de localidades con el mismo JSON
        String url = "http://localhost:8080/api/localidades/create-locality";
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            logger.info("Respuesta de create-locality exitosa: {}", response.getBody());

            List<Sismo> sismosAInsertar = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (Map<String, Object> sismoData : sismos) {
                String refGeografica = (String) sismoData.get("RefGeografica");

                // Buscar el id y nombre de la localidad en la base de datos
                Optional<Localidad> localidad = localidadRepository.findByNombre(extraerLocalidad(refGeografica));

                if (localidad.isPresent()) {
                    Sismo nuevoSismo = new Sismo();
                    nuevoSismo.setLocalidadId(localidad.get().getId());  // Guardar el ID de la localidad
                    nuevoSismo.setLocalidad(localidad.get().getNombre());  // Guardar el nombre de la localidad
                    nuevoSismo.setFecha(LocalDateTime.parse((String) sismoData.get("Fecha"), formatter));
                    nuevoSismo.setProfundidad(Double.parseDouble(sismoData.get("Profundidad").toString()));
                    nuevoSismo.setMagnitud(Double.parseDouble(sismoData.get("Magnitud").toString()));

                    // Verificar si el sismo ya fue procesado (comparar ignorando FechaUpdate)
                    if (!sismoYaProcesado(nuevoSismo)) {
                        sismosAInsertar.add(nuevoSismo);
                        agregarSismoProcesado(nuevoSismo);  // Agregar el nuevo sismo a la lista con límite de 30
                    } else {
                        logger.info("El sismo ya ha sido procesado: {}", nuevoSismo);
                    }
                } else {
                    logger.warn("No se encontró la localidad para {}", refGeografica);
                }
            }

            // Guardar los sismos nuevos que no estaban procesados previamente
            if (!sismosAInsertar.isEmpty()) {
                sismoService.saveAll(sismosAInsertar);
                logger.info("Sismos insertados: {}", sismosAInsertar);
            }

            return ResponseEntity.ok("Sismos y localidades procesados correctamente.");
        } else {
            logger.error("Error en create-locality: {}", response.getBody());
            return ResponseEntity.status(response.getStatusCode()).body("Error al crear localidades: " + response.getBody());
        }
    }

    // Método para verificar si un sismo ya fue procesado, ignorando el campo FechaUpdate
    private boolean sismoYaProcesado(Sismo nuevoSismo) {
        for (Sismo sismoProcesado : sismosProcesados) {
            // Comparar todos los campos excepto 'FechaUpdate'
            if (sismoProcesado.getLocalidadId().equals(nuevoSismo.getLocalidadId()) &&
                    sismoProcesado.getFecha().equals(nuevoSismo.getFecha()) &&
                    sismoProcesado.getProfundidad().equals(nuevoSismo.getProfundidad()) &&
                    sismoProcesado.getMagnitud().equals(nuevoSismo.getMagnitud())) {
                return true; // Ya existe un sismo igual
            }
        }
        return false; // No se encontró ningún sismo igual
    }

    // Método para agregar un sismo a la lista de procesados, con límite de 30
    private void agregarSismoProcesado(Sismo nuevoSismo) {
        if (sismosProcesados.size() >= MAX_SISMOS_PROCESADOS) {
            // Remover el sismo más antiguo (el primero de la lista) si ya hay 30
            sismosProcesados.remove(0);
        }
        // Agregar el nuevo sismo
        sismosProcesados.add(nuevoSismo);
        logger.info("Sismo añadido a la lista de procesados: {}", nuevoSismo);
    }

    // Método auxiliar para extraer la localidad después de " de "
    private String extraerLocalidad(String refGeografica) {
        // Verificar si contiene la cadena " de "
        if (refGeografica != null && refGeografica.contains(" de ")) {
            // Extraer todo lo que está después de la PRIMERA aparición de " de "
            return refGeografica.substring(refGeografica.indexOf(" de ") + 4).trim();
        }
        // Si no contiene " de ", devolver la cadena completa
        return refGeografica;
    }

}
