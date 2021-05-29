package com.bankservice.app;

import com.bankservice.app.domain.enums.AccountType;
import com.bankservice.app.domain.enums.Identification;
import com.bankservice.app.domain.model.Cliente;
import com.bankservice.app.infraestructure.jpa.repositories.ClientRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
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

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        return objectMapper.readValue(json, clazz);
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

        MvcResult mvcResult = mvc.perform(post("/api/client/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("id".concat(":1,").concat("name").concat(":Leonardo,")
                .concat("lastname").concat(":Monsalvo,").concat("phone").concat(":3006366657,")
                .concat("address").concat(":Cartagena,").concat("accountType").concat(":"+String.valueOf(AccountType.AHORROS+","))
                .concat("identification").concat(":"+String.valueOf(Identification.CÉDULA+",")).concat("created")
                        .concat(":"+String.valueOf(LocalDate.now())))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void givenBadEndpoint_404() throws Exception {
        Cliente cliente = Cliente.builder().name("").build();
        String inputJson = mapToJson(cliente);
        MvcResult mvcResult = mvc.perform(post("/api/client")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).
                andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

}
