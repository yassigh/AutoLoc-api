package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.request.EmployeRequestDTO;
import tn.esprit.autoloc.dto.response.EmployeResponseDTO;

import java.util.List;

public interface IEmployeService {

    EmployeResponseDTO create(EmployeRequestDTO dto);

    EmployeResponseDTO findById(Long id);

    List<EmployeResponseDTO> findAll();

    EmployeResponseDTO update(Long id, EmployeRequestDTO dto);

    void deleteById(Long id);
}