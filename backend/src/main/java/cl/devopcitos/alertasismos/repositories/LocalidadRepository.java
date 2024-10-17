package cl.devopcitos.alertasismos.repositories;

import cl.devopcitos.alertasismos.models.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Long> {

    // Método para buscar una localidad por nombre
    Optional<Localidad> findByNombre(String nombre);

}
