package br.com.fiap.msclients.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.msclients.infrastructure.persistence.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    @Query("SELECT a FROM AddressEntity a WHERE upper(a.zipCode) = upper(:zipCode)")
    AddressEntity findByZipCode(@Param("zipCode") String zipCode);
}
