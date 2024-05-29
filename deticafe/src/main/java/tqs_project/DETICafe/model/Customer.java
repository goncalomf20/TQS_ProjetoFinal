package tqs_project.DETICafe.model;

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
    private String phone_number;

    @Column(name = "email", nullable = false)
    private String email;

    public Customer(int nmec, String name, String phone_number, String email) {
        this.nmec = nmec;
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
    }

    
}
