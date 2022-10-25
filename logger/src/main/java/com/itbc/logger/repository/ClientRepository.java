package com.itbc.logger.repository;

import com.itbc.logger.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    public Client findByName(String account);

    public Client findByPassword(String password);
}
