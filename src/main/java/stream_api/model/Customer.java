package stream_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Customer {
    private Long id;
    private String name;
    private Long level;
    private Set<Order> orders;
}
