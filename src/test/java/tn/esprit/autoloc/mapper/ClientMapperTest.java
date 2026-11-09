package tn.esprit.autoloc.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.dto.request.ClientRequestDTO;
import tn.esprit.autoloc.dto.response.ClientResponseDTO;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientMapperTest {

    private ClientMapper clientMapper;

    @BeforeEach
    void setUp() {
        clientMapper = new ClientMapper();
    }

    @Test
    void testToEntity() {
        ClientRequestDTO dto = ClientRequestDTO.builder()
                .nom("Ben Ali")
                .prenom("Ahmed")
                .email("ahmed.benali@esprit.tn")
                .telephone("+216 20 123 456")
                .numPermis("TUN-12345678")
                .dateInscription(LocalDate.of(2025, 1, 15))
                .build();

        Client entity = clientMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("Ben Ali", entity.getNom());
        assertEquals("Ahmed", entity.getPrenom());
        assertEquals("ahmed.benali@esprit.tn", entity.getEmail());
        assertEquals("+216 20 123 456", entity.getTelephone());
        assertEquals("TUN-12345678", entity.getNumPermis());
        assertEquals(LocalDate.of(2025, 1, 15), entity.getDateInscription());
    }

    @Test
    void testToResponseDTO() {
        Client entity = new Client();
        entity.setIdClient(1L);
        entity.setNom("Ben Ali");
        entity.setPrenom("Ahmed");
        entity.setEmail("ahmed.benali@esprit.tn");
        entity.setTelephone("+216 20 123 456");
        entity.setNumPermis("TUN-12345678");
        entity.setDateInscription(LocalDate.of(2025, 1, 15));

        ClientResponseDTO dto = clientMapper.toResponseDTO(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getIdClient());
        assertEquals("Ben Ali", dto.getNom());
        assertEquals("Ahmed", dto.getPrenom());
        assertEquals("ahmed.benali@esprit.tn", dto.getEmail());
        assertEquals("+216 20 123 456", dto.getTelephone());
        assertEquals("TUN-12345678", dto.getNumPermis());
        assertEquals(LocalDate.of(2025, 1, 15), dto.getDateInscription());
    }

    @Test
    void testToResponseDTOList() {
        Client entity1 = new Client();
        entity1.setIdClient(1L);
        entity1.setNom("Ben Ali");

        Client entity2 = new Client();
        entity2.setIdClient(2L);
        entity2.setNom("Trabelsi");

        List<ClientResponseDTO> dtos = clientMapper.toResponseDTOList(List.of(entity1, entity2));

        assertEquals(2, dtos.size());
        assertEquals(1L, dtos.get(0).getIdClient());
        assertEquals("Ben Ali", dtos.get(0).getNom());
        assertEquals(2L, dtos.get(1).getIdClient());
        assertEquals("Trabelsi", dtos.get(1).getNom());
    }

    @Test
    void testUpdateEntityFromDTO() {
        Client entity = new Client();
        entity.setIdClient(1L);
        entity.setNom("AncienNom");

        ClientRequestDTO dto = ClientRequestDTO.builder()
                .nom("NouveauNom")
                .prenom("NouveauPrenom")
                .email("nouveau@esprit.tn")
                .telephone("+216 99 999 999")
                .numPermis("TUN-99999999")
                .dateInscription(LocalDate.now())
                .build();

        clientMapper.updateEntityFromDTO(dto, entity);

        assertEquals(1L, entity.getIdClient());
        assertEquals("NouveauNom", entity.getNom());
        assertEquals("NouveauPrenom", entity.getPrenom());
        assertEquals("nouveau@esprit.tn", entity.getEmail());
    }
}
