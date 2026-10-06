package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Client;

import java.util.List;
import java.util.Optional;

public interface IClientService {

    Client add(Client client);

    Client update(Client client);

    List<Client> findAll();

    Optional<Client> findById(Long id);

    void deleteById(Long id);
}