package br.com.fiap.msclients.infrastructure.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiap.msclients.infrastructure.persistence.entity.AddressEntity;
import br.com.fiap.msclients.infrastructure.persistence.entity.ClientEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    private ClientEntity createAndSaveClient(String cpf, String email, String name) {
        AddressEntity address = new AddressEntity();
        address.setZipCode("12345-678");
        address.setStreet("Rua Teste");
        address.setNumber("100");
        address.setComplement("Apto 101");
        address = addressRepository.save(address);

        ClientEntity client = new ClientEntity();
        client.setName(name);
        client.setCpf(cpf);
        client.setEmail(email);
        client.setBirth(LocalDate.of(1990, 1, 1)); // Corrigido: adicionando data de nascimento
        client.setAddress(address);

        return clientRepository.save(client);
    }

    @Test
    @DisplayName("Deve encontrar cliente pelo CPF (case insensitive)")
    void shouldFindClientByCpfIgnoreCase() {
        ClientEntity client = createAndSaveClient("12345678900", "joao@email.com", "João da Silva");

        ClientEntity found = clientRepository.findByCpf("12345678900");

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("João da Silva");
    }

    @Test
    @DisplayName("Deve retornar true se CPF existir (case insensitive)")
    void shouldReturnTrueIfCpfExistsIgnoreCase() {
        createAndSaveClient("98765432100", "maria@email.com", "Maria Oliveira");

        Boolean exists = clientRepository.existsByCpf("98765432100");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve encontrar cliente pelo e-mail (case insensitive)")
    void shouldFindClientByEmailIgnoreCase() {
        createAndSaveClient("33322211100", "ana@email.com", "Ana Paula");

        ClientEntity found = clientRepository.findByEmail("ANA@email.com");

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Ana Paula");
    }

    @Test
    @DisplayName("Deve encontrar cliente por nome contendo trecho (case insensitive)")
    void shouldFindClientByNameContainingIgnoreCase() {
        createAndSaveClient("55566677788", "lucas@email.com", "Lucas Andrade");

        ClientEntity found = clientRepository.findByName("lucas");

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("lucas@email.com");
    }

    @Test
    @DisplayName("Deve retornar null se CPF não for encontrado")
    void shouldReturnNullWhenCpfNotFound() {
        ClientEntity found = clientRepository.findByCpf("00000000000");

        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Deve retornar null se email não for encontrado")
    void shouldReturnNullWhenEmailNotFound() {
        ClientEntity found = clientRepository.findByEmail("naoexiste@email.com");

        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Deve retornar null se nome não for encontrado")
    void shouldReturnNullWhenNameNotFound() {
        ClientEntity found = clientRepository.findByName("Zezinho");

        assertThat(found).isNull();
    }
}
