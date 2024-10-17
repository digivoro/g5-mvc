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
import java.util.*;

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
        HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(sismos, headers);

        // Hacer un POST a la API de creación de localidades
        String url = "http://localhost:8080/api/localidades/create-locality";
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        // Usar un Set para almacenar las IDs de las localidades de los nuevos sismos, evitando duplicados
        Set<Long> localidadesIds = new HashSet<>();

        if (response.getStatusCode().is2xxSuccessful()) {
            logger.info("Respuesta de create-locality exitosa: {}", response.getBody());

            List<Sismo> sismosAInsertar = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (Map<String, Object> sismoData : sismos) {
                String refGeografica = (String) sismoData.get("RefGeografica");
                Optional<Localidad> localidad = localidadRepository.findByNombre(sismoService.extraerLocalidad(refGeografica));

                if (localidad.isPresent()) {
                    Sismo nuevoSismo = new Sismo();
                    nuevoSismo.setLocalidadId(localidad.get().getId());
                    nuevoSismo.setLocalidad(localidad.get().getNombre());
                    nuevoSismo.setFecha(LocalDateTime.parse((String) sismoData.get("Fecha"), formatter));
                    nuevoSismo.setProfundidad(Double.parseDouble(sismoData.get("Profundidad").toString()));
                    nuevoSismo.setMagnitud(Double.parseDouble(sismoData.get("Magnitud").toString()));

                    // Verificar si el sismo ya fue procesado (comparar ignorando FechaUpdate)
                    if (!sismoService.sismoYaProcesado(nuevoSismo)) {
                        sismosAInsertar.add(nuevoSismo);
                        sismoService.agregarSismoProcesado(nuevoSismo);

                        // Agregar la ID de la localidad al Set, lo que garantiza que no se repitan
                        localidadesIds.add(localidad.get().getId());
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

            // Aquí puedes utilizar el Set `localidadesIds` con las IDs de las localidades de los sismos nuevos
            logger.info("IDs de las localidades (sin duplicados) de los sismos registrados: {}", localidadesIds);

            return ResponseEntity.ok("Sismos y localidades procesados correctamente.");
        } else {
            logger.error("Error en create-locality: {}", response.getBody());
            return ResponseEntity.status(response.getStatusCode()).body("Error al crear localidades: " + response.getBody());
        }
    }



}
