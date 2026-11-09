package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.request.ClientRequestDTO;
import tn.esprit.autoloc.dto.response.ClientResponseDTO;

import java.util.List;

public interface IClientService {

    ClientResponseDTO create(ClientRequestDTO dto);

    ClientResponseDTO findById(Long id);

    List<ClientResponseDTO> findAll();

    ClientResponseDTO update(Long id, ClientRequestDTO dto);

    void deleteById(Long id);
}