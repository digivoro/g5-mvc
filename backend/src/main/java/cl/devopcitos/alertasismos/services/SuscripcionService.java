package cl.devopcitos.alertasismos.services;

import cl.devopcitos.alertasismos.models.Suscripcion;
import cl.devopcitos.alertasismos.repositories.SuscripcionRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuscripcionService {
  @Autowired
  private SuscripcionRepository suscripcionRepository;

  //create
  public Suscripcion createSuscripcion(Suscripcion suscripcion){
    return suscripcionRepository.save(suscripcion);
  }

  //read
  public List<Suscripcion> getAllSuscripcions(){
    return suscripcionRepository.findAll();
  }

  public Suscripcion getSuscripcionById(Long id){
    Optional<Suscripcion> suscripcion = suscripcionRepository.findById(id);
    return suscripcion.orElse(null);
  }

  //update
  public Suscripcion updateSuscripcion(Long id, Suscripcion suscripcion){
    Suscripcion newSuscripcion = getSuscripcionById(id);
    if (newSuscripcion != null){
      newSuscripcion.setLocalidad(suscripcion.getLocalidad());
      newSuscripcion.setEmail(suscripcion.getEmail());
      newSuscripcion.setNombre(suscripcion.getNombre());
      newSuscripcion.setActivo(suscripcion.getActivo());
      return suscripcionRepository.save(newSuscripcion);
    }
    return null;
  }

  //delete
  public boolean deleteSuscripcion(Long id){
    if (suscripcionRepository.existsById(id)) {
      suscripcionRepository.deleteById(id);
      return true;
    }
    return false;
  }
}
