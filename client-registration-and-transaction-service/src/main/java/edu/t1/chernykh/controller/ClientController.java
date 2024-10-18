package edu.t1.chernykh.controller;

import edu.t1.chernykh.dto.ClientDto;
import edu.t1.chernykh.entity.Client;
import edu.t1.chernykh.repository.ClientRepository;
import edu.t1.chernykh.util.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientController(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody ClientDto clientDto){
        Client client = clientMapper.toClient(clientDto);
        clientRepository.save(client);

        return ResponseEntity.ok("Client successfully created");
    }
}