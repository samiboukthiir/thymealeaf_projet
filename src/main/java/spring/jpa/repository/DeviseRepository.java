package spring.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.jpa.model.Devise;
import spring.jpa.model.Produit;

@Repository
public interface DeviseRepository extends JpaRepository<Devise, Long> {
    // Ajoutez des méthodes personnalisées si nécessaire
	Page<Devise> findByNomLike(String mc, Pageable pageable);
}
