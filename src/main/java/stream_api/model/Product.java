package stream_api.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Product {
    private final Long id;
    private final String name;
    private final String category;
    private final BigDecimal price;

}