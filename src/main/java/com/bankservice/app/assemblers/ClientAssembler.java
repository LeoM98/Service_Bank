package com.bankservice.app.assemblers;

import com.bankservice.app.domain.dto.ClientDTO;
import com.bankservice.app.domain.model.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientAssembler {

    ClientDTO clientToClientDto(Cliente cliente);
    Cliente mapClienteToCliente(Cliente cliente);

}
