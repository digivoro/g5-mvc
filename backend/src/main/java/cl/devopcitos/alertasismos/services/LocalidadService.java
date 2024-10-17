package cl.devopcitos.alertasismos.services;

import cl.devopcitos.alertasismos.models.Localidad;
import cl.devopcitos.alertasismos.repositories.LocalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class LocalidadService {

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Método para buscar localidad por nombre
    public Optional<Localidad> buscarLocalidadPorNombre(String nombre) {
        return localidadRepository.findByNombre(nombre);
    }

    // Método para hacer el POST a la API de creación de localidades
    public ResponseEntity<String> procesarLocalidades(List<?> sismos) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<?>> request = new HttpEntity<>(sismos, headers);
        String url = "http://localhost:8080/api/localidades/create-locality";
        return restTemplate.postForEntity(url, request, String.class);
    }

    // Guardar o actualizar localidad
    public Localidad guardarLocalidad(Localidad localidad) {
        return localidadRepository.save(localidad);
    }

    // Método para obtener todas las localidades
    public List<Localidad> obtenerTodasLocalidades() {
        return localidadRepository.findAll();
    }
}
