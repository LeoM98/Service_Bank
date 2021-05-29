package com.bankservice.app.controller;

import com.bankservice.app.domain.dto.ClientDTO;
import com.bankservice.app.domain.model.Cliente;
import com.bankservice.app.services.impl.ClientServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private ClientServiceImpl service;

    public ClientController(ClientServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<ClientDTO>> listar(){
        List<ClientDTO> clientDTOS = service.findAll();
        return new ResponseEntity<>(clientDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
        ClientDTO clientDTOS = service.findById(id);
        return new ResponseEntity<>(clientDTOS, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ClientDTO> save(@Valid @RequestBody Cliente cliente){
        ClientDTO clientDTOS = service.save(cliente);
        return new ResponseEntity<>(clientDTOS, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<ClientDTO> update(@Valid @RequestBody Cliente cliente){
        ClientDTO clientDTOS = service.update(cliente);
        return new ResponseEntity<>(clientDTOS, HttpStatus.OK);
    }

    @PatchMapping("/{id}/{name}")
    public ResponseEntity<ClientDTO> patch(@PathVariable Long id, @Valid @PathVariable String name){
        ClientDTO clientDTOS = service.patchName(name, id);
        return new ResponseEntity<>(clientDTOS, HttpStatus.OK);
    }
}
