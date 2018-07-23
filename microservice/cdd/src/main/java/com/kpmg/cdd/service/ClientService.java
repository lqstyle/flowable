package com.kpmg.cdd.service;

import com.kpmg.cdd.entity.Client;
import com.kpmg.cdd.repository.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: client interface
 * @date 07/07/2018 10:14 AM
 */
@Service
public class ClientService {

    @Autowired
    private ClientMapper clientMapper;

    public List<Map<String, Object>> getClientList(Map<String, Object> map) throws Exception {
        return clientMapper.getClientList(map);
    }

    public int saveClient(Client client) {
        return clientMapper.insert(client);
    }

    public List<Client> selectByExample(Client client) {
        return clientMapper.selectByExample(client);
    }

    public Client getSingleClient(String caseId) {
        return clientMapper.getSingleClient(caseId);
    }

    public void updateByPrimaryKey(Client client) {
        clientMapper.updateByPrimaryKey(client);
    }
}
