package br.com.fiap.msclients.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.fiap.msclients.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE upper(c.cpf) = upper(:cpf)")
    Client findByCpf(@Param("cpf") String cpf);

    @Query("SELECT c FROM Client c WHERE upper(c.cpf) = upper(:cpf)")
    Boolean existsCPF(@Param("cpf") String cpf);    

    @Query("SELECT c FROM Client c WHERE upper(c.email) = upper(:email)")
    Client findByEmail(@Param("email") String email);

    @Query("SELECT c FROM Client c WHERE upper(c.name) like concat('%', upper(:name), '%')")
    Client findByName(@Param("name") String name);
 
}
