package stream_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor

public class Customer {
    private final Long id;
    private final String name;
    private final Long level;
    private final Set<Order> orders;

}
