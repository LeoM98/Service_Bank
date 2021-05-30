package com.bankservice.app;

import com.bankservice.app.Utils.LocalDateAdapter;
import com.bankservice.app.domain.enums.AccountType;
import com.bankservice.app.domain.enums.Identification;
import com.bankservice.app.domain.model.Cliente;
import com.bankservice.app.infraestructure.jpa.repositories.ClientRepository;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BankserviceApplication.class)
@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private WebApplicationContext wac;
    @Mock
    private ClientRepository repository;
    private MockMvc mvc;

    // It's so important to execute the test method
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(
                this.wac);
        this.mvc = builder.build();
    }

    @Test
    public void getAllClients_Status200() throws Exception {
        Cliente cliente = Cliente.builder().name("Leonardo").lastname("Monsalvo")
                .address("Cartagena").phone("3006366657")
                .accountType(AccountType.AHORROS).identification(Identification.CÉDULA)
                .created(LocalDate.now()).build();

        List<Cliente> clienteList = Lists.newArrayList(cliente);
        when(repository.findAll()).thenReturn(clienteList);

        MvcResult mvcResult = mvc.perform( MockMvcRequestBuilders
                .get("/api/client/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    public void getClienteById_Status200() throws Exception {
        Cliente cliente = Cliente.builder().name("Leonardo").lastname("Monsalvo")
                .address("Cartagena").phone("3006366657")
                .accountType(AccountType.AHORROS).identification(Identification.CÉDULA)
                .created(LocalDate.now()).build();

        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        MvcResult mvcResult = mvc.perform( MockMvcRequestBuilders
                .get("/api/client/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    public void givenClienteNull_getStatus200() throws Exception {
        Cliente cliente = new Cliente();
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        MvcResult mvcResult = mvc.perform( MockMvcRequestBuilders
                .get("/api/client/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    public void saveClient_getStatus_201() throws Exception{

        Cliente cliente = Cliente.builder().name("Fernando").lastname("Monsalvo")
                .address("Cartagena").phone("1111111111")
                .accountType(AccountType.AHORROS).identification(Identification.CÉDULA)
                .montoDinero(500000L).numeroIdentificacion("1212121212")
                .created(LocalDate.now()).build();

        String json = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(cliente);

        MvcResult mvcResult = mvc.perform(post("/api/client/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).
                andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void saveClient_EmptyName_getStatus_400() throws Exception{

        Cliente cliente = Cliente.builder().name("").lastname("Monsalvo")
                .address("Cartagena").phone("1111111111")
                .accountType(AccountType.AHORROS).identification(Identification.CÉDULA)
                .montoDinero(500000L).numeroIdentificacion("1212121212")
                .created(LocalDate.now()).build();

        String json = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(cliente);

        MvcResult mvcResult = mvc.perform(post("/api/client/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).
                andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void saveClient_EmptyLastName_getStatus_400() throws Exception{

        Cliente cliente = Cliente.builder().name("Leonardo").lastname("")
                .address("Cartagena").phone("1111111111")
                .accountType(AccountType.AHORROS).identification(Identification.CÉDULA)
                .montoDinero(500000L).numeroIdentificacion("1212121212")
                .created(LocalDate.now()).build();

        String json = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(cliente);

        MvcResult mvcResult = mvc.perform(post("/api/client/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).
                andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void saveClient_BadPhone_getStatus_400() throws Exception{

        Cliente cliente = Cliente.builder().name("Leonardo").lastname("Monsalvo")
                .address("Cartagena").phone("3354")
                .accountType(AccountType.AHORROS).identification(Identification.CÉDULA)
                .montoDinero(500000L).numeroIdentificacion("1212121212")
                .created(LocalDate.now()).build();

        String json = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(cliente);

        MvcResult mvcResult = mvc.perform(post("/api/client/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).
                andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void saveClient_EmptyAddress_getStatus_400() throws Exception{

        Cliente cliente = Cliente.builder().name("Leonardo").lastname("Monsalvo")
                .address("").phone("1111111111")
                .accountType(AccountType.AHORROS).identification(Identification.CÉDULA)
                .montoDinero(500000L).numeroIdentificacion("1212121212")
                .created(LocalDate.now()).build();

        String json = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(cliente);

        MvcResult mvcResult = mvc.perform(post("/api/client/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).
                andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void saveClient_BadAmountMoney_getStatus_400() throws Exception{

        Cliente cliente = Cliente.builder().name("Leonardo").lastname("Monsalvo")
                .address("Cartagena").phone("1111111111")
                .accountType(AccountType.AHORROS).identification(Identification.CÉDULA)
                .montoDinero(90000L).numeroIdentificacion("1212121212")
                .created(LocalDate.now()).build();

        String json = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(cliente);

        MvcResult mvcResult = mvc.perform(post("/api/client/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).
                andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void saveClient_BadIdentificationNumber_getStatus_400() throws Exception{

        Cliente cliente = Cliente.builder().name("Leonardo").lastname("Monsalvo")
                .address("Cartagena").phone("1111111111")
                .accountType(AccountType.AHORROS).identification(Identification.CÉDULA)
                .montoDinero(1000000L).numeroIdentificacion("1212121212111")
                .created(LocalDate.now()).build();

        String json = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(cliente);

        MvcResult mvcResult = mvc.perform(post("/api/client/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).
                andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void givenBadEndpoint_404() throws Exception {
        Cliente cliente = Cliente.builder().name("").build();
        String inputJson = new Gson().toJson(cliente);
        MvcResult mvcResult = mvc.perform(post("/api/client")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).
                andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

}
