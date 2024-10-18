package edu.t1.chernykh.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String login;

    @NotBlank
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public @NotBlank @Size(max = 20) String getLogin() {
        return login;
    }

    public void setLogin(@NotBlank @Size(max = 20) String login) {
        this.login = login;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public User() {}
}
