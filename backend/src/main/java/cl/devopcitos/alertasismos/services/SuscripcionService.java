package cl.devopcitos.alertasismos.services;

import cl.devopcitos.alertasismos.models.Suscripcion;
import cl.devopcitos.alertasismos.repositories.SuscripcionRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    System.out.println("Updating");
    System.out.println(suscripcion);
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

  public Suscripcion updateSuscripcionParcial(Long id, Suscripcion suscripcionParcial) {
    Suscripcion suscripcionExistente = suscripcionRepository.findById(id).orElseThrow(() -> new RuntimeException("Suscripción no encontrada"));
    copyNonNullAndNonEmptyProperties(suscripcionParcial, suscripcionExistente);
    return suscripcionRepository.save(suscripcionExistente);
  }
  
  private void copyNonNullAndNonEmptyProperties(Object source, Object target) {
    BeanWrapper src = new BeanWrapperImpl(source);
    BeanWrapper trg = new BeanWrapperImpl(target);

    for (java.beans.PropertyDescriptor pd : src.getPropertyDescriptors()) {
      String propertyName = pd.getName();
      if (pd.getReadMethod() != null && !"class".equals(propertyName)) {
        Object srcValue = src.getPropertyValue(propertyName);
        if (srcValue != null && !(srcValue instanceof String && !StringUtils.hasText((String) srcValue))) {
          trg.setPropertyValue(propertyName, srcValue);
        }
      }
    }
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
