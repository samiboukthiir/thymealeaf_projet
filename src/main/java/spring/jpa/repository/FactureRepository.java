package spring.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import spring.jpa.model.Devise;
import spring.jpa.model.Facture;

import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
	Page<Facture> findByClientIdLike(Long clientId, Pageable pageable);

}
