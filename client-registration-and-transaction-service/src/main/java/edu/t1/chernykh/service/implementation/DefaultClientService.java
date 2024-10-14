package edu.t1.chernykh.service.implementation;

import edu.t1.chernykh.entity.Client;
import edu.t1.chernykh.repository.ClientRepository;
import edu.t1.chernykh.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultClientService implements ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public DefaultClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }
}
