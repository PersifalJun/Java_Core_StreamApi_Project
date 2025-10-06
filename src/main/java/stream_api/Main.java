package stream_api;

import stream_api.model.Customer;
import stream_api.model.Order;
import stream_api.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        //Инициализация
        Product product1 = new Product(1L, "Laptop ", "Books", new BigDecimal("899.99"));
        Product product2 = new Product(2L, "Mouse", "Children's products", new BigDecimal("19.90"));
        Product product3 = new Product(3L, "Mechanical Keyboard", "Toys", new BigDecimal("79.00"));
        Product product4 = new Product(4L, "Office Chair", "Books", new BigDecimal("99.50"));
        Product product5 = new Product(5L, "Desk", "Toys", new BigDecimal("349.00"));

        Set<Product> products = Set.of(product1,product2,product3,product4,product5);


        Order order1 = new Order(
                11L,
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 5),
                "NEW",
                Set.of(product1, product2, product3));

        Order order2 = new Order(
                12L,
                LocalDate.of(2025, 3, 2),
                LocalDate.of(2025, 3, 10),
                "PROCESSING",
                Set.of(product2, product3, product4));

        Order order3 = new Order(
                13L,
                LocalDate.of(2025, 3, 3),
                LocalDate.of(2025, 3, 4),
                "DELIVERED",
                Set.of(product3, product4, product5));

        Order order4 = new Order(
                14L,
                LocalDate.of(2025, 3, 6),
                LocalDate.of(2025, 3, 7),
                "CANCELLED",
                Set.of(product1, product2, product5));
        Order order5 = new Order(
                15L,
                LocalDate.of(2025, 3, 8),
                LocalDate.of(2025, 3, 9),
                "NEW",
                Set.of(product2, product3, product4));
        Order order6 = new Order(
                16L,
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2025, 4, 1),
                "NEW",
                Set.of(product4, product5, product1));

        Order order7 = new Order(
                17L,
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2025, 4, 1),
                "PROCESSING",
                Set.of(product1, product2, product3));

        Order order8 = new Order(
                18L,
                LocalDate.of(2025, 2, 14),
                LocalDate.of(2025, 4, 15),
                "DELIVERED",
                Set.of(product3, product4, product5));

        Order order9 = new Order(
                19L,
                LocalDate.of(2025, 2, 16),
                LocalDate.of(2025, 4, 17),
                "CANCELLED",
                Set.of(product1, product2, product5));
        Order order10 = new Order(
                20L,
                LocalDate.of(2025, 2, 18),
                LocalDate.of(2025, 4, 19),
                "NEW",
                Set.of(product1, product2, product4));

        Set<Order> orders = Set.of(order1,order2,order3,order4,order5,order6,order7,order8,order9,order10);

        Customer customer1 = new Customer(101L,"Oleg",1L,Set.of(order1,order2,order3,order4,order5));
        Customer customer2 = new Customer(102L,"Dima",2L,Set.of(order6,order7,order8,order9,order10));
        Customer customer3 = new Customer(103L,"Kolya",3L,Set.of(order1,order3,order5,order7,order9));
        Customer customer4 = new Customer(104L,"Evgeniy",4L,Set.of(order2,order4,order6,order8,order1));
        Customer customer5 = new Customer(105L,"Alex",5L,Set.of(order1,order4,order7,order8,order9));
        List<Customer> customers = List.of(customer1,customer2,customer3,customer4,customer5);


        //1 задание
        List<Product> booksWith100Price = products.stream()
                .filter(x-> x.getPrice().compareTo(BigDecimal.valueOf(100)) >0)
                .filter(x->x.getCategory().equals("Books"))
                .collect(Collectors.toList());

        booksWith100Price.forEach(System.out::println);

        System.out.println("-".repeat(100));

        //2 задание
        List<Order> ordersChildrensProducts = orders.stream()
                .filter(x->x.getProducts().
                        stream().
                        anyMatch(p -> "Children's products".equals(p.getCategory())))
                .collect(Collectors.toList());

        ordersChildrensProducts.forEach(System.out::println);

        System.out.println("-".repeat(100));

        //3 задание
        BigDecimal sumtoysWithSale10PerCent = products
                .stream()
                .filter(x->x.getCategory().equals("Toys"))
                .map(x->x.getPrice().multiply(BigDecimal.valueOf(0.9)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        System.out.println(sumtoysWithSale10PerCent);

        List<Product> toysWithSale10PerCent = products
                .stream()
                .filter(x->x.getCategory().equals("Toys"))
                .collect(Collectors.toList());

        toysWithSale10PerCent.forEach(System.out::println);

        System.out.println("-".repeat(100));

        //4 задание

    }
}
