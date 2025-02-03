package ru.gav.creditbank.deal.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.gav.creditbank.deal.dao.ClientDao;
import ru.gav.creditbank.deal.dto.ClientDto;
import ru.gav.creditbank.deal.entity.client.Client;
import ru.gav.creditbank.deal.mappers.ClientMapper;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("/application-test.yaml")
class ClientServiceImplTest {
    @Mock
    private ClientMapper clientMapper;
    @Mock
    private ClientDao clientDao;
    @InjectMocks
    private ClientServiceImpl clientService;

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

    @Test
    void save() {
        doReturn(client).when(clientMapper).mapDtoToEntity(clientDto);
        doReturn(client).when(clientDao).save(client);
        doReturn(clientDto).when(clientMapper).mapEntityToDto(client);
        assertEquals(clientDto, clientService.save(clientDto));
    }
}