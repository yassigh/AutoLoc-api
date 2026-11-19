package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.request.ContratRequestDTO;
import tn.esprit.autoloc.dto.response.ContratDTO;
import tn.esprit.autoloc.dto.response.ContratResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface IContratService {

    ContratResponseDTO create(ContratRequestDTO dto);

    ContratResponseDTO findById(Long id);

    List<ContratResponseDTO> findAll();

    ContratResponseDTO update(Long id, ContratRequestDTO dto);

    void deleteById(Long id);

    ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin);
}
