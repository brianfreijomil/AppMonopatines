package domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    private Long ID;
    private String name;
    private String surname;
    private String e_mail;
    private String password;
    private Long phoneNumber;
    //private GPS Ubication;
    //private List<Account> accounts;
    private String role;
}
