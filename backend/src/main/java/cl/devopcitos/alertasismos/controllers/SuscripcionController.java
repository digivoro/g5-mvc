package cl.devopcitos.alertasismos.controllers;

import cl.devopcitos.alertasismos.models.Suscripcion;
import cl.devopcitos.alertasismos.services.SuscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/suscripciones")
public class SuscripcionController {
  @Autowired
  private SuscripcionService suscripcionService;

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
}
