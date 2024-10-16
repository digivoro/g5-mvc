package cl.devopcitos.alertasismos.controllers;

import cl.devopcitos.alertasismos.models.Localidad;
import cl.devopcitos.alertasismos.repositories.LocalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/localidades")
public class LocalidadController {

    @Autowired
    private LocalidadRepository localidadRepository;

    @GetMapping
    public List<Localidad> getAllLocalidades() {
        return localidadRepository.findAll();
    }

    // Función para extraer y guardar las localidades desde los sismos
    @PostMapping("/create-locality")
    public void createLocality(@RequestBody List<Map<String, String>> sismos) {
        // Crear un arreglo para almacenar las localidades que serán insertadas
        List<Localidad> localidadesAInsertar = new ArrayList<>();

        // Procesar cada sismo recibido
        for (Map<String, String> sismo : sismos) {
            // Obtener el campo 'RefGeografica'
            String refGeografica = sismo.get("RefGeografica");
            if (refGeografica != null) {
                // Extraer la localidad
                String localidadExtraida = extraerLocalidad(refGeografica);

                // Verificar si la localidad ya existe en la base de datos
                Optional<Localidad> localidadExistente = localidadRepository.findByNombre(localidadExtraida);

                // Si la localidad no existe, agregarla al arreglo para insertar
                if (localidadExistente.isEmpty()) {
                    Localidad nuevaLocalidad = new Localidad(localidadExtraida);
                    localidadesAInsertar.add(nuevaLocalidad);
                } else {
                    // Localidad ya existe, imprimir un mensaje
                    System.out.println("La localidad " + localidadExtraida + " ya existe en la base de datos.");
                }
            }
        }

        // Guardar todas las nuevas localidades que no existían
        if (!localidadesAInsertar.isEmpty()) {
            localidadRepository.saveAll(localidadesAInsertar);
            System.out.println("Localidades insertadas: " + localidadesAInsertar);
        }
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
