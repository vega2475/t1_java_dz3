package edu.t1.chernykh.util;

import edu.t1.chernykh.dto.ClientDto;
import edu.t1.chernykh.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public Client toClient(ClientDto clientDto){
        return new Client(clientDto.firstName(), clientDto.middleName(), clientDto.lastName());
    }
}
