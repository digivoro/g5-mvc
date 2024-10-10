package cl.devopcitos.alertasismos.repositories;

import cl.devopcitos.alertasismos.models.Suscripcion;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
  
}
