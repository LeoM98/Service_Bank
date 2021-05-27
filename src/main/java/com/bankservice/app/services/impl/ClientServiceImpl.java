package com.bankservice.app.services.impl;

import com.bankservice.app.assemblers.ClientAssembler;
import com.bankservice.app.domain.dto.ClientDTO;
import com.bankservice.app.domain.model.Cliente;
import com.bankservice.app.exceptions.ClientNotFoundException;
import com.bankservice.app.exceptions.ClientsNotFoundException;
import com.bankservice.app.exceptions.DatesException;
import com.bankservice.app.exceptions.PhoneException;
import com.bankservice.app.infraestructure.jpa.repositories.ClientRepository;
import com.bankservice.app.services.ClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository repository;
    private ClientAssembler assembler;

    @Autowired
    public ClientServiceImpl(ClientRepository repository, ClientAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Override
    public List<ClientDTO> findAll() {
        List<ClientDTO> clientDTOS = repository.findAll()
                .stream().map(x-> assembler.clientToClientDto(x))
                .collect(Collectors.toList());

        if (clientDTOS.isEmpty())
            throw new ClientsNotFoundException("Any clients was found");

        return clientDTOS;
    }

    @Override
    public ClientDTO save(Cliente cliente) {

        Cliente clienteN = cliente;
        if(clienteN.getCreated().after(new Date()))
            throw new DatesException("Date cannot be bigger than actual");

        if(clienteN.getPhone().length()!=10)
            throw new PhoneException("Phone it can be 10 size");

        repository.save(clienteN);
        ClientDTO clientDTO = assembler.clientToClientDto(clienteN);
        return clientDTO;
    }

    @Override
    public ClientDTO findById(Long id) {

        Optional<Cliente> cliente = repository.findById(id);
        if(!cliente.isPresent())
            throw new ClientNotFoundException("Any client was found to show");

        ClientDTO clientDTO = assembler.clientToClientDto(cliente.get());
        return clientDTO;
    }

    @Override
    public void delete(Long id) {

        Optional<Cliente> cliente = repository.findById(id);
        if(!cliente.isPresent())
            throw new ClientNotFoundException("An error was happens to delete a client");
        cliente.ifPresent(x->repository.deleteById(x.getId()));
    }

    @Override
    public ClientDTO update(Cliente cliente) {

        Cliente clienteN = repository.findById(cliente.getId()).orElse(null);
        if(clienteN == null){
            throw new ClientNotFoundException("Any client was found to update");
        }

        BeanUtils.copyProperties(cliente,clienteN);

        if (clienteN.getCreated().after(new Date())) {
            throw new DatesException("Date cannot be bigger than actual");
        }else if (clienteN.getPhone().length() != 10)
            throw new PhoneException("Phone it can be 10 size");

        repository.save(clienteN);
        ClientDTO clientDTO = assembler.clientToClientDto(clienteN);
        return clientDTO;
    }
}
