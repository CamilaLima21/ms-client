package br.com.fiap.msclients.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.fiap.msclients.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>  {

    @Query("SELECT a FROM Address a WHERE upper(a.zipCode) = upper(:zipCode)")
    Address findByZipCode(@Param("zipCode") String zipCode);    
    
}
