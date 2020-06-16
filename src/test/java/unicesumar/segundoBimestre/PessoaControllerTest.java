package unicesumar.segundoBimestre;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
public class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PessoaController controller;

    @MockBean
    private PessoaService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Pessoa createPessoa() {

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Fulano da Silva");

        return pessoa;
    }

    @Test
    public void findAll() throws Exception {

        Pessoa pessoa = createPessoa();

        when(this.service.findAll()).thenReturn(Collections.singletonList(pessoa));

        this.mockMvc.perform(get("/api/pessoas"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id", hasItem(pessoa.getId().toString())))
                .andExpect(jsonPath("$.[*].nome", hasItem(pessoa.getNome())));

        verify(this.service).findAll();
        verifyNoMoreInteractions(this.service);
    }

    @Test
    public void findById() throws Exception {

        Pessoa pessoa = createPessoa();

        when(this.service.findById(pessoa.getId())).thenReturn(pessoa);

        this.mockMvc.perform(get("/api/pessoas/{id}", pessoa.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(pessoa.getId().toString())))
                .andExpect(jsonPath("$.nome", equalTo(pessoa.getNome())));

        verify(this.service).findById(pessoa.getId());
        verifyNoMoreInteractions(this.service);
    }

    @Test
    public void save() throws Exception {

        Pessoa pessoa = createPessoa();

        doNothing().when(this.service).save(pessoa);

        this.mockMvc.perform(post("/api/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoa)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        verify(this.service).save(any(Pessoa.class));
        verifyNoMoreInteractions(this.service);
    }

    @Test
    public void update() throws Exception {

        Pessoa pessoa = createPessoa();

        doNothing().when(this.service).save(pessoa);

        this.mockMvc.perform(put("/api/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoa)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(this.service).save(any(Pessoa.class));
        verifyNoMoreInteractions(this.service);
    }

    @Test
    public void deletePessoa() throws Exception {

        Pessoa pessoa = createPessoa();

        doNothing().when(this.service).delete(pessoa.getId());

        this.mockMvc.perform(delete("/api/pessoas/{id}", pessoa.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());

        verify(this.service).delete(pessoa.getId());
        verifyNoMoreInteractions(this.service);
    }
}
