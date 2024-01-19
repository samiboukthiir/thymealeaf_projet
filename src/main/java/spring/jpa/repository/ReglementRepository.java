package spring.jpa.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.jpa.model.Reglement;

@Repository
public interface ReglementRepository extends JpaRepository<Reglement, Long> {

    
}
