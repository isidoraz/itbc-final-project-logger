package com.itbc.logger.repository;

import com.itbc.logger.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // optional - to avoid null pointer exception error
    Optional<Client> findByUsername(String username);
    Optional<Client> findByEmail(String email);

    List<Client> findByIsAdmin(boolean isAdmin);

}
