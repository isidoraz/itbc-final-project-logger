package com.itbc.logger.model;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    // one client can have many logs
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private boolean isAdmin;

}
