package com.bankservice.app.services;

import com.bankservice.app.domain.dto.ClientDTO;
import com.bankservice.app.domain.model.Cliente;

import java.util.List;

public interface ClientService {
    List<ClientDTO> findAll();
    ClientDTO save(Cliente cliente);
    ClientDTO findById(Long id);
    void delete(Long id);
    ClientDTO update(Cliente cliente);
    ClientDTO patchName(String name, Long id);
    ClientDTO addMoney(Long money, Long id);
    ClientDTO restMoney(Long money, Long id);

}
