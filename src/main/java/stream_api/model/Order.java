package stream_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class Order {
    private final Long id;
    private final LocalDate orderDate;
    private final LocalDate deliveryDate;
    private final String status;
    private final Set<Product> products;

}