package tqs_project.DETICafe.DTO;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetailsDTO {
    private int foodId;
    private Integer quantity;
    private String name;
    private Map<String, Boolean> orderDetails;
}
