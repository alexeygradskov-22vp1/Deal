package ru.gav.creditbank.deal.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.gav.creditbank.deal.dto.ClientDto;
import ru.gav.creditbank.deal.entity.client.Client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("/application-test.yaml")
public class ClientMapperTest {
    @Autowired
    ClientMapper clientMapper;

    private ClientDto clientDto;
    private Client client;

    @BeforeEach
    public void init() {
        try (InputStream clientDtoIS = ClientDto.class.getResourceAsStream("/test/data/client-dto.json");
             InputStream clientIS = Client.class.getResourceAsStream("/test/data/client.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.registerModule(new JsonNullableModule());
            clientDto = objectMapper.readValue(clientDtoIS, ClientDto.class);
            //init Client
            client = objectMapper.readValue(clientIS, Client.class);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @SneakyThrows
    @Test
    public void testMapToEntity() {
        Client clientTested = clientMapper.mapDtoToEntity(clientDto);
        var valStream = Stream.of(
                Optional.ofNullable(clientTested.getClientId()),
                Optional.ofNullable(clientTested.getLastName()),
                Optional.ofNullable(clientTested.getFirstName()),
                Optional.ofNullable(clientTested.getMiddleName()),
                Optional.ofNullable(clientTested.getBirthdate()),
                Optional.ofNullable(clientTested.getEmail()),
                Optional.ofNullable(clientTested.getDependentAmount()),
                Optional.ofNullable(clientTested.getPassport()),
                Optional.ofNullable(clientTested.getPassport().getNumber()),
                Optional.ofNullable(clientTested.getPassport().getSeries()));
        valStream.forEach(x -> assertTrue(x.isPresent()));
    }

    @SneakyThrows
    @Test
    public void testMapToDto() {
        ClientDto clientDtoTested = clientMapper.mapEntityToDto(client);
        var valStream = Stream.of(
                Optional.ofNullable(clientDtoTested.getClientId()),
                Optional.ofNullable(clientDtoTested.getLastName()),
                Optional.ofNullable(clientDtoTested.getFirstName()),
                Optional.ofNullable(clientDtoTested.getMiddleName()),
                Optional.ofNullable(clientDtoTested.getBirthdate()),
                Optional.ofNullable(clientDtoTested.getEmail()),
                Optional.ofNullable(clientDtoTested.getDependentAmount()),
                Optional.ofNullable(clientDtoTested.getPassport()),
                Optional.ofNullable(clientDtoTested.getPassport().getNumber()),
                Optional.ofNullable(clientDtoTested.getPassport().getSeries()));
        valStream.forEach(x -> assertTrue(x.isPresent()));
    }


    @SneakyThrows
    @Test
    public void testUpdate() {
        Client updated = clientMapper.update(clientDto, client);
        //В clientDto данные employment = null, поэтому обновление должно скипнуться, и данные старые останутся
        assertEquals(client.getEmployment(), updated.getEmployment());
        assertEquals(client.getEmployment().getStatus(), updated.getEmployment().getStatus());
        assertEquals(client.getEmployment().getEmployerInn(), updated.getEmployment().getEmployerInn());
        assertEquals(client.getEmployment().getSalary(), updated.getEmployment().getSalary());
        assertEquals(client.getEmployment().getWorkExperienceCurrent(), updated.getEmployment().getWorkExperienceCurrent());
        assertEquals(client.getEmployment().getWorkExperienceTotal(), updated.getEmployment().getWorkExperienceTotal());
    }


}
