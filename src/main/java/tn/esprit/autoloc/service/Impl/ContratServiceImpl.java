package tn.esprit.autoloc.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.request.ContratRequestDTO;
import tn.esprit.autoloc.dto.response.ContratDTO;
import tn.esprit.autoloc.dto.response.ContratResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ContratMapper;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.service.IContratService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final ContratMapper contratMapper;

    @Override
    public ContratResponseDTO create(ContratRequestDTO dto) {
        if (dto.getMontantTotal() != null && dto.getMontantTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant total ne peut pas être négatif");
        }

        Contrat contrat = contratMapper.toEntity(dto);
        Contrat saved = contratRepository.save(contrat);
        return contratMapper.toResponseDTO(saved);
    }

    @Override
    public ContratResponseDTO findById(Long id) {
        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
        return contratMapper.toResponseDTO(contrat);
    }

    @Override
    public List<ContratResponseDTO> findAll() {
        return contratMapper.toResponseDTOList(contratRepository.findAll());
    }

    @Override
    public ContratResponseDTO update(Long id, ContratRequestDTO dto) {
        Contrat existant = contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));

        if (dto.getMontantTotal() != null && dto.getMontantTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant total ne peut pas être négatif");
        }

        contratMapper.updateEntityFromDTO(dto, existant);
        Contrat updated = contratRepository.save(existant);
        return contratMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin) {
        Contrat contrat = contratRepository.findFirstByDateSignature(dateSignature).get();
        Reservation reservation = reservationRepository.findFirstByDateDebutAndDateFin(dateDebut, dateFin).get();

        reservation.setContrat(contrat);
        reservationRepository.save(reservation);
        return new ContratDTO(contratMapper.toResponseDTO(contrat));
    }
}