package tqs_project.deticafe.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name="staff")
public class Staff{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phonenumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "type", columnDefinition = "ENUM('WAITER', 'CHEF')", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    public Staff(String name, String phonenumber, String email, Type type) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
        this.type = type;
    }

    
}