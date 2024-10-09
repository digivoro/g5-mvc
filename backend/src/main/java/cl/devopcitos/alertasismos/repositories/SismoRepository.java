package cl.devopcitos.alertasismos.repositories;

import cl.devopcitos.alertasismos.models.Sismo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SismoRepository extends JpaRepository<Sismo, Long> {

}
