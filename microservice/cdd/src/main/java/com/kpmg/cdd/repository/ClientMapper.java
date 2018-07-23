package com.kpmg.cdd.repository;

import java.util.List;
import java.util.Map;

import com.kpmg.cdd.entity.Client;import org.apache.ibatis.annotations.Param;

/**
 * Function Description: respository floor
 *
 * @param:
 * @return:
 * @author: lucasliang
 * @date: 07/07/2018
 */
public interface ClientMapper {

    List<Map<String, Object>> getClientList(Map<String, Object> map) throws Exception;

    int insert(Client client);

    List<Client> selectByExample(Client client);

    Client getSingleClient(String caseId);

    int updateClientState(Map<String, Object> map);

    void updateByPrimaryKey(Client client);

    void updateResultStateById(@Param("resultState")String resultState, @Param("id")String id);
}