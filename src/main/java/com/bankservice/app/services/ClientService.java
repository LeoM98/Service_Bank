package com.bankservice.app.services;

import com.bankservice.app.domain.dto.ClientDTO;
import com.bankservice.app.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<ClientDTO> findAll();
    ClientDTO save(Cliente cliente);
    ClientDTO findById(Long id);
    void delete(Long id);
    ClientDTO update(Cliente cliente);
}
