package tn.esprit.autoloc.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import tn.esprit.autoloc.dto.request.ClientRequestDTO;
import tn.esprit.autoloc.dto.response.ClientResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.service.IClientService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IClientService clientService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        ClientController controller = new ClientController(clientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testCreateClient_Success() throws Exception {
        ClientRequestDTO requestDTO = ClientRequestDTO.builder()
                .nom("Ben Ali")
                .prenom("Ahmed")
                .email("ahmed.benali@esprit.tn")
                .telephone("+216 20 123 456")
                .numPermis("TUN-12345678")
                .dateInscription(LocalDate.of(2025, 1, 15))
                .build();

        ClientResponseDTO responseDTO = ClientResponseDTO.builder()
                .idClient(1L)
                .nom("Ben Ali")
                .prenom("Ahmed")
                .email("ahmed.benali@esprit.tn")
                .telephone("+216 20 123 456")
                .numPermis("TUN-12345678")
                .dateInscription(LocalDate.of(2025, 1, 15))
                .build();

        when(clientService.create(any(ClientRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idClient").value(1L))
                .andExpect(jsonPath("$.nom").value("Ben Ali"))
                .andExpect(jsonPath("$.email").value("ahmed.benali@esprit.tn"));
    }

    @Test
    void testCreateClient_ValidationError_MissingNomAndInvalidEmail() throws Exception {
        ClientRequestDTO invalidRequest = ClientRequestDTO.builder()
                .nom("") // @NotBlank error
                .prenom("Ahmed")
                .email("email-invalide") // @Email error
                .telephone("+216 20 123 456")
                .numPermis("TUN-12345678")
                .dateInscription(LocalDate.of(2025, 1, 15))
                .build();

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.errors.nom").exists())
                .andExpect(jsonPath("$.errors.email").exists());
    }

    @Test
    void testFindById_NotFound() throws Exception {
        when(clientService.findById(999L)).thenThrow(new ResourceNotFoundException("Client", 999L));

        mockMvc.perform(get("/clients/999"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void testFindAll_Success() throws Exception {
        ClientResponseDTO c1 = ClientResponseDTO.builder().idClient(1L).nom("Ben Ali").build();
        ClientResponseDTO c2 = ClientResponseDTO.builder().idClient(2L).nom("Trabelsi").build();

        when(clientService.findAll()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nom").value("Ben Ali"))
                .andExpect(jsonPath("$[1].nom").value("Trabelsi"));
    }
}
