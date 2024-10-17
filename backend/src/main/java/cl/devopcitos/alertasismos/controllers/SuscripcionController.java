package cl.devopcitos.alertasismos.controllers;

import cl.devopcitos.alertasismos.models.Localidad;
import cl.devopcitos.alertasismos.models.Suscripcion;
import cl.devopcitos.alertasismos.repositories.LocalidadRepository;
import cl.devopcitos.alertasismos.services.SuscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suscripciones")
public class SuscripcionController {
  @Autowired
  private SuscripcionService suscripcionService;

  @Autowired
  private LocalidadRepository localidadRepository;

  @GetMapping
  public List<Suscripcion> getAllSuscripcions(){
    return suscripcionService.getAllSuscripcions();
  }

  @PostMapping
  public Suscripcion createSuscripcion(@RequestBody Suscripcion suscripcion){
    return suscripcionService.createSuscripcion(suscripcion);
  }

  @GetMapping("/{id}")
  public Suscripcion oneSuscripcion(@PathVariable Long id){
    return suscripcionService.getSuscripcionById(id);
  }

  @PutMapping("/{id}")
  public Suscripcion updateSuscripcion(@PathVariable Long id, @RequestBody Suscripcion suscripcion){
    Suscripcion updatedSuscripcion = suscripcionService.getSuscripcionById(id);
    updatedSuscripcion.setLocalidad(suscripcion.getLocalidad());
    updatedSuscripcion.setEmail(suscripcion.getEmail());
    updatedSuscripcion.setNombre(suscripcion.getNombre());
    return suscripcionService.createSuscripcion(updatedSuscripcion);
  }

  @PatchMapping("/{id}")
  public Suscripcion updateSuscripcionParcial(@PathVariable Long id, @RequestBody Suscripcion suscripcionParcial) {
    return suscripcionService.updateSuscripcionParcial(id, suscripcionParcial);
  }

  @DeleteMapping("/{id}")
  public Boolean deleteSuscripcion(@PathVariable Long id){
    suscripcionService.deleteSuscripcion(id);
    return true;
  }

  // Endpoint para crear una suscripción
  @PostMapping("/create")
  public ResponseEntity<String> createSuscripcion(@RequestBody SuscripcionDTO suscripcionDTO) {
    // Buscar la localidad por su ID
    Optional<Localidad> localidadOpt = localidadRepository.findById(suscripcionDTO.getLocalidadId());

    if (localidadOpt.isPresent()) {
      // Crear la suscripción
      try {
        Suscripcion nuevaSuscripcion = new Suscripcion(
                localidadOpt.get(),
                suscripcionDTO.getEmail(),
                suscripcionDTO.getNombre(),
                true
        );

        suscripcionService.createSuscripcion(nuevaSuscripcion);
        return ResponseEntity.ok("Suscripción creada exitosamente.");
      } catch (IllegalStateException e) {
        return ResponseEntity.status(400).body("Error: " + e.getMessage());
      }
    } else {
      return ResponseEntity.status(404).body("Localidad no encontrada.");
    }
  }

  // DTO para recibir los datos del formulario
  public static class SuscripcionDTO {
    private Long localidadId;
    private String email;
    private String nombre;

    public Long getLocalidadId() {
      return localidadId;
    }

    public void setLocalidadId(Long localidadId) {
      this.localidadId = localidadId;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getNombre() {
      return nombre;
    }

    public void setNombre(String nombre) {
      this.nombre = nombre;
    }
  }
}
