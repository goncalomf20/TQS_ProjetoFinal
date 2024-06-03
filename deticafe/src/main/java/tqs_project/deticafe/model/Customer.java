package tqs_project.deticafe.model;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "costumer_id")
    private Long costumerId;

    @Column(name = "nmec", nullable = false)
    private int nmec;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phonenumber;

    @Column(name = "email", nullable = false)
    private String email;

    public Customer(int nmec, String name, String phonenumber, String email) {
        this.nmec = nmec;
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
    }

    
}
