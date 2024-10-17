package cl.devopcitos.alertasismos.controllers;

import cl.devopcitos.alertasismos.models.Localidad;
import cl.devopcitos.alertasismos.models.Suscripcion;
import cl.devopcitos.alertasismos.repositories.LocalidadRepository;
import cl.devopcitos.alertasismos.repositories.SuscripcionRepository;
import cl.devopcitos.alertasismos.services.EmailService;
import cl.devopcitos.alertasismos.services.LocalidadService;
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

    @Autowired
    private SuscripcionRepository suscripcionRepository;

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

    @Autowired
    private LocalidadService localidadService;

    @Autowired
    private EmailService emailService;

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

        Set<Long> localidadesIds = new HashSet<>();

        if (response.getStatusCode().is2xxSuccessful()) {
            logger.info("Respuesta de create-locality exitosa:\n{}", response.getBody());

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

                    if (!sismoService.sismoYaProcesado(nuevoSismo)) {
                        sismosAInsertar.add(nuevoSismo);
                        sismoService.agregarSismoProcesado(nuevoSismo);
                        localidadesIds.add(localidad.get().getId());
                    } else {
                        logger.info("El sismo ya ha sido procesado: {}", nuevoSismo);
                    }
                } else {
                    logger.warn("No se encontró la localidad para {}", refGeografica);
                }
            }

            if (!sismosAInsertar.isEmpty()) {
                logger.info("Sismos insertados:\n{}", formatList(sismosAInsertar));
                sismoService.saveAll(sismosAInsertar);
            }

            if (!localidadesIds.isEmpty()) {
                List<Suscripcion> suscripciones = suscripcionRepository.findByLocalidadIdIn(new ArrayList<>(localidadesIds));
                List<Map<String, String>> correosYLocalidades = new ArrayList<>();

                // Enviar correos
                suscripciones.forEach(suscripcion -> {
                    Map<String, String> entry = new HashMap<>();
                    entry.put("email", suscripcion.getEmail());
                    entry.put("localidad", suscripcion.getLocalidad().getNombre());

                    correosYLocalidades.add(entry);

                    // Construir el cuerpo del correo con los datos del sismo
                    StringBuilder emailBody = new StringBuilder();
                    emailBody.append("Estimado suscriptor,\n\n");
                    emailBody.append("Se ha registrado un nuevo sismo en tu localidad: ").append(suscripcion.getLocalidad().getNombre()).append("\n");
                    emailBody.append("Detalles del sismo:\n");

                    // Añadir los detalles del sismo
                    sismosAInsertar.forEach(sismo -> {
                        if (sismo.getLocalidadId().equals(suscripcion.getLocalidad().getId())) {
                            emailBody.append("Fecha: ").append(sismo.getFecha()).append("\n");
                            emailBody.append("Profundidad: ").append(sismo.getProfundidad()).append(" km\n");
                            emailBody.append("Magnitud: ").append(sismo.getMagnitud()).append("\n");
                            emailBody.append("Referencia Geográfica: ").append(sismo.getLocalidad()).append("\n\n");
                        }
                    });

                    emailBody.append("Gracias por estar atento a las notificaciones.\n\n");
                    emailBody.append("Saludos,\nEquipo de Alerta Sismos");

                    // Enviar el correo
                    emailService.sendEmail(suscripcion.getEmail(), "Alerta de nuevo sismo en tu localidad", emailBody.toString());
                });


                logger.info("Correos y localidades asociados:\n{}", formatMapList(correosYLocalidades));
            }

            return ResponseEntity.ok("Sismos y localidades procesados correctamente.");
        } else {
            logger.error("Error en create-locality:\n{}", response.getBody());
            return ResponseEntity.status(response.getStatusCode()).body("Error al crear localidades: " + response.getBody());
        }
    }

    // Método para formatear listas de sismos para que se vean más legibles en el log
    private String formatList(List<Sismo> sismos) {
        StringBuilder formatted = new StringBuilder();
        for (Sismo sismo : sismos) {
            formatted.append(sismo).append("\n");
        }
        return formatted.toString();
    }

    // Método para formatear listas de mapas para que se vean más legibles en el log
    private String formatMapList(List<Map<String, String>> mapList) {
        StringBuilder formatted = new StringBuilder();
        for (Map<String, String> map : mapList) {
            formatted.append(map).append("\n");
        }
        return formatted.toString();
    }
}
