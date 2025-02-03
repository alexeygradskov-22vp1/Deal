package ru.gav.creditbank.deal.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.shaded.com.google.common.base.Predicate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gav.creditbank.deal.dto.ClientDto;
import ru.gav.creditbank.deal.dto.CreditDto;
import ru.gav.creditbank.deal.dto.StatementDto;
import ru.gav.creditbank.deal.exception.NoSuchElementInDatabaseException;
import ru.gav.creditbank.deal.exception.WebClientReturnNullException;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;
import ru.gav.creditbank.deal.logic.impl.DealServiceImpl;
import ru.gav.creditbank.deal.mappers.Extractor;
import ru.gav.creditbank.deal.service.ClientService;
import ru.gav.creditbank.deal.service.CreditService;
import ru.gav.creditbank.deal.service.DossierService;
import ru.gav.creditbank.deal.service.StatementService;
import ru.gav.deal.model.FinishRegistrationRequestDto;
import ru.gav.deal.model.LoanOfferDto;
import ru.gav.deal.model.LoanStatementRequestDto;
import ru.gav.deal.model.ScoringDataDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("/application-test.yaml")
public class DealServiceTest {
    private LoanStatementRequestDto loanStatementRequestDto;
    private ClientDto clientDto;
    private StatementDto statementDto;
    private List<LoanOfferDto> loanOfferDto;
    private ScoringDataDto scoringDataDto;
    private FinishRegistrationRequestDto finishRegistrationRequestDto;
    private CreditDto creditDto;
    private DossierService dossierService;

    private String offersEndpoint = "calculate/offers";

    @Mock
    private StatementService statementService;
    @Mock
    private ClientService clientService;
    @Mock
    private CreditService creditService;


    @Mock
    private Extractor extractor;

    @Mock
    private WebClient webClient = mock(WebClient.class);

    private DealServiceImpl dealService;

    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    private WebClient.RequestHeadersSpec requestHeadersMock;
    private WebClient.RequestBodySpec requestBodyMock;
    private WebClient.ResponseSpec responseMock;


    @BeforeEach
    void mockWebClient() {
        requestBodyUriMock = mock(WebClient.RequestBodyUriSpec.class);
        requestHeadersMock = mock(WebClient.RequestHeadersSpec.class);
        requestBodyMock = mock(WebClient.RequestBodySpec.class);
        responseMock = mock(WebClient.ResponseSpec.class);

        try (InputStream loanStatementRequestStream =
                     LoanStatementRequestDto.class.getResourceAsStream("/test/data/loan-statement-request-dto.json");
             InputStream clientDtoStream = ClientDto.class.getResourceAsStream("/test/data/client-dto.json");
             InputStream statementDtoStream = StatementDto.class.getResourceAsStream("/test/data/statement-dto.json");
             InputStream inputStream = LoanOfferDto.class.getResourceAsStream("/test/data/loan-offers-dto.json");
             InputStream scoringDataStream = ScoringDataDto.class.getResourceAsStream("/test/data/scoring-data-dto.json");
             InputStream finishRegistrationStream =
                     FinishRegistrationRequestDto.class.getResourceAsStream("/test/data/finish-registration-dto.json");
             InputStream creditDtoStream =
                     CreditDto.class.getResourceAsStream("/test/data/credit-dto.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JsonNullableModule());
            objectMapper.registerModule(new JavaTimeModule());
            clientDto = objectMapper.readValue(clientDtoStream, ClientDto.class);
            loanStatementRequestDto = objectMapper.readValue(loanStatementRequestStream, LoanStatementRequestDto.class);
            statementDto = objectMapper.readValue(statementDtoStream, StatementDto.class);
            loanOfferDto = Arrays.stream(objectMapper.readValue(inputStream, LoanOfferDto[].class)).toList();
            scoringDataDto = objectMapper.readValue(scoringDataStream, ScoringDataDto.class);
            finishRegistrationRequestDto = objectMapper.readValue(finishRegistrationStream, FinishRegistrationRequestDto.class);
            creditDto = objectMapper.readValue(creditDtoStream, CreditDto.class);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Test
    public void calculateLoanOffersTestValid() {
        dealService = new DealServiceImpl(statementService, clientService, creditService, extractor, webClient, new ExceptionSupplier(), dossierService);
        Flux<LoanOfferDto> loanOfferDtoFlux = Flux.fromIterable(loanOfferDto);
        // Мокирование зависимостей.
        doReturn(clientDto).when(extractor).extractClientFromLoanStatement(loanStatementRequestDto);
        doReturn(clientDto).when(clientService).save(clientDto);
        doReturn(statementDto).when(statementService).save(any(StatementDto.class));
        doReturn(requestBodyUriMock).when(webClient).post();
        doReturn(requestBodyMock).when(requestBodyUriMock).uri(any(Function.class));
        doReturn(requestHeadersMock).when(requestBodyMock).body(any(BodyInserter.class));
        doReturn(responseMock).when(requestHeadersMock).retrieve();
        doReturn(loanOfferDtoFlux).when(responseMock).bodyToFlux(LoanOfferDto.class);
        List<LoanOfferDto> loanOfferDtoList = dealService.calculateLoanOffers(loanStatementRequestDto);
        assertEquals(loanOfferDto, loanOfferDtoList);
    }

    @Test
    public void selectOfferTest() {
        dealService = new DealServiceImpl(statementService, clientService, creditService, extractor, webClient, new ExceptionSupplier(),dossierService); //TODO Вынужденное решение использовать через самостоятельную инициализацию сервиса, а не через @Autowired
        doReturn(statementDto).when(statementService).getOne(statementDto.getStatementId());
        doNothing().when(statementService).update(statementDto);
    }

    @Test
    public void finishRegistrationTest() {
        dealService = new DealServiceImpl(statementService, clientService, creditService, extractor, webClient, new ExceptionSupplier(),dossierService);
        UUID statementId = statementDto.getStatementId();
        doReturn(statementDto).when(statementService).getOne(statementId);
        doReturn(scoringDataDto).when(extractor).extractScoringDataDtoFromFinishRegistrationDtoAndStatementDto(finishRegistrationRequestDto, statementDto);
        doReturn(requestBodyUriMock).when(webClient).post();
        doReturn(requestBodyMock).when(requestBodyUriMock).uri(any(Function.class));
        doReturn(requestHeadersMock).when(requestBodyMock).body(any(BodyInserter.class));
        doReturn(responseMock).when(requestHeadersMock).retrieve();
        doReturn(Mono.just(creditDto)).when(responseMock).bodyToMono(CreditDto.class);
        doReturn(creditDto).when(creditService).save(creditDto);
        doReturn(statementDto).when(statementService).save(statementDto);
        dealService.finishRegistration(finishRegistrationRequestDto, statementId.toString());

    }

    @Test
    public void finishRegistrationTestUnsuccessful() {
        dealService = new DealServiceImpl(statementService, clientService, creditService, extractor, webClient, new ExceptionSupplier(),dossierService);
        Mono<CreditDto> creditDtoMono = Mockito.mock(Mono.class);
        Optional<CreditDto> optionalMock = Mockito.mock(Optional.class);
        UUID statementId = statementDto.getStatementId();
        doReturn(statementDto).when(statementService).getOne(statementId);
        doReturn(scoringDataDto).when(extractor).extractScoringDataDtoFromFinishRegistrationDtoAndStatementDto(finishRegistrationRequestDto, statementDto);
        doReturn(requestBodyUriMock).when(webClient).post();
        doReturn(requestBodyMock).when(requestBodyUriMock).uri(any(Function.class));
        doReturn(requestHeadersMock).when(requestBodyMock).body(any(BodyInserter.class));
        doReturn(responseMock).when(requestHeadersMock).retrieve();
        doReturn(creditDtoMono).when(responseMock).bodyToMono(CreditDto.class);
        doReturn(creditDtoMono).when(creditDtoMono).doOnError(any(Consumer.class));
        doReturn(null).when(creditDtoMono).block();
        assertThrows(WebClientReturnNullException.class, ()->dealService.finishRegistration(finishRegistrationRequestDto, statementId.toString()));
    }

    @Test
    public void calculateLoanOffersTestValidUnsuccessful() {
        dealService = new DealServiceImpl(statementService, clientService, creditService, extractor, webClient, new ExceptionSupplier(),dossierService);
        Stream<LoanOfferDto> loanOfferDtoStream = Mockito.mock(Stream.class);
        Flux<LoanOfferDto> loanOfferDtoFlux = Mockito.mock(Flux.class);
        // Мокирование зависимостей.
        doReturn(clientDto).when(extractor).extractClientFromLoanStatement(loanStatementRequestDto);
        doReturn(clientDto).when(clientService).save(clientDto);
        doReturn(statementDto).when(statementService).save(any(StatementDto.class));
        doReturn(requestBodyUriMock).when(webClient).post();
        doReturn(requestBodyMock).when(requestBodyUriMock).uri(any(Function.class));
        doReturn(requestHeadersMock).when(requestBodyMock).body(any(BodyInserter.class));
        doReturn(responseMock).when(requestHeadersMock).retrieve();
        doReturn(loanOfferDtoFlux).when(responseMock).bodyToFlux(LoanOfferDto.class);
        doReturn(Flux.empty()).when(loanOfferDtoFlux).doOnError(any(Consumer.class));
        doReturn(Flux.empty()).when(loanOfferDtoFlux).filter(Objects::nonNull);
        doReturn(Flux.empty()).when(loanOfferDtoFlux).map(any(Function.class));
        doReturn(loanOfferDtoStream).when(loanOfferDtoFlux).toStream();
        doReturn(List.of()).when(loanOfferDtoStream).toList();
        List<LoanOfferDto> loanOfferDtoList = dealService.calculateLoanOffers(loanStatementRequestDto);
        assertEquals(loanOfferDtoList.size(), 0);
    }

    @Test
    public void selectOfferTestUnsuccessful() {
        dealService = new DealServiceImpl(statementService, clientService, creditService, extractor, webClient, new ExceptionSupplier(), dossierService); //TODO Вынужденное решение использовать через самостоятельную инициализацию сервиса, а не через @Autowired
        doThrow(NoSuchElementInDatabaseException.class).when(statementService).getOne(any(UUID.class));
        assertThrows(NoSuchElementInDatabaseException.class, ()->dealService.selectOffer(loanOfferDto.get(0)));
    }
}
