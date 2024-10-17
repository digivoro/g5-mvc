package cl.devopcitos.alertasismos.repositories;

import cl.devopcitos.alertasismos.models.Suscripcion;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {

    // Buscar si ya existe una suscripción con el mismo correo y localidad
    Optional<Suscripcion> findByEmailAndLocalidadId(String email, Long localidadId);

    List<Suscripcion> findByLocalidadIdIn(List<Long> localidadIds);
}
