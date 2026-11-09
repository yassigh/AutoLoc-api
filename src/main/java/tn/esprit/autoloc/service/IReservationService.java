package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.request.ReservationRequestDTO;
import tn.esprit.autoloc.dto.response.ReservationResponseDTO;

import java.util.List;

public interface IReservationService {

    ReservationResponseDTO create(ReservationRequestDTO dto);

    ReservationResponseDTO findById(Long id);

    List<ReservationResponseDTO> findAll();

    ReservationResponseDTO update(Long id, ReservationRequestDTO dto);

    void deleteById(Long id);
}