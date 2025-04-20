package br.com.fiap.msclients.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.fiap.msclients.infrastructure.persistence.entity.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    @Query("SELECT c FROM ClientEntity c WHERE upper(c.cpf) = upper(:cpf)")
    ClientEntity findByCpf(@Param("cpf") String cpf);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ClientEntity c WHERE upper(c.cpf) = upper(:cpf)")
    Boolean existsByCpf(@Param("cpf") String cpf);    

    @Query("SELECT c FROM ClientEntity c WHERE upper(c.email) = upper(:email)")
    ClientEntity findByEmail(@Param("email") String email);

    @Query("SELECT c FROM ClientEntity c WHERE upper(c.name) like concat('%', upper(:name), '%')")
    ClientEntity findByName(@Param("name") String name);
}
