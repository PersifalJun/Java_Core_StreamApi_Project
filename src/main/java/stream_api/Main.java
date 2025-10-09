package stream_api;

import stream_api.model.Customer;
import stream_api.model.Order;
import stream_api.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

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
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 10),
                "NEW",
                Set.of(product1, product2, product3));
        Order order2 = new Order(
                12L,
                LocalDate.of(2021, 2, 2),
                LocalDate.of(2021, 2, 12),
                "PROCESSING",
                Set.of(product2, product3, product4));
        Order order3 = new Order(
                13L,
                LocalDate.of(2021, 3, 15),
                LocalDate.of(2021, 3, 25),
                "DELIVERED",
                Set.of(product3, product4, product5));
        Order order4 = new Order(
                14L,
                LocalDate.of(2021, 4, 4),
                LocalDate.of(2021, 4, 14),
                "CANCELLED",
                Set.of(product1, product2, product5));
        Order order5 = new Order(
                15L,
                LocalDate.of(2021, 5, 5),
                LocalDate.of(2021, 5, 15),
                "NEW",
                Set.of(product2, product3, product4));
        Order order6 = new Order(
                16L,
                LocalDate.of(2021, 6, 6),
                LocalDate.of(2021, 6, 16),
                "NEW",
                Set.of(product4, product5, product1));
        Order order7 = new Order(
                17L,
                LocalDate.of(2021, 7, 7),
                LocalDate.of(2021, 7, 17),
                "PROCESSING",
                Set.of(product1, product2, product3));
        Order order8 = new Order(
                18L,
                LocalDate.of(2021, 8, 8),
                LocalDate.of(2021, 8, 18),
                "DELIVERED",
                Set.of(product3, product4, product5));
        Order order9 = new Order(
                19L,
                LocalDate.of(2021, 9, 9),
                LocalDate.of(2021, 9, 19),
                "CANCELLED",
                Set.of(product1, product2, product5));
        Order order10 = new Order(
                20L,
                LocalDate.of(2021, 10, 10),
                LocalDate.of(2021, 10, 20),
                "NEW",
                Set.of(product1, product2, product4));
        Order order11 = new Order(
                21L,
                LocalDate.of(2021, 3, 14),
                LocalDate.of(2021, 3, 24),
                "DELIVERED",
                Set.of(product1, product2, product5));

        Set<Order> orders = Set.of(order1,order2,order3,order4,order5,order6,order7,order8,order9,order10,order11);
        Customer customer1 = new Customer(101L,"Oleg",1L,Set.of(order1,order2,order3,order4,order5));
        Customer customer2 = new Customer(102L,"Dima",2L,Set.of(order6,order7,order8,order9,order10));
        Customer customer3 = new Customer(103L,"Kolya",3L,Set.of(order1,order3,order5,order7,order9));
        Customer customer4 = new Customer(104L,"Evgeniy",4L,Set.of(order2,order4,order6,order8,order11));
        Customer customer5 = new Customer(105L,"Alex",5L,Set.of(order1,order4,order7,order8,order11));
        List<Customer> customers = List.of(customer1,customer2,customer3,customer4,customer5);

        //1 задание
        System.out.println("Задание 1 ");
        System.out.println();
        List<Product> booksWith100Price = products.stream()
                .filter(x-> x.getPrice().compareTo(BigDecimal.valueOf(100)) >0)
                .filter(x->x.getCategory().equals("Books"))
                .collect(toList());
        booksWith100Price.forEach(System.out::println);
        System.out.println("-".repeat(100));

        //2 задание
        System.out.println("Задание 2 ");
        System.out.println();
        List<Order> ordersChildrensProducts = orders.stream()
                .filter(x->x.getProducts().
                        stream().
                        anyMatch(p -> "Children's products".equals(p.getCategory())))
                .collect(toList());
        ordersChildrensProducts.forEach(System.out::println);
        System.out.println("-".repeat(100));

        //3 задание
        System.out.println("Задание 3 ");
        System.out.println();
        BigDecimal sumtoysWithSale10PerCent = products
                .stream()
                .filter(x->x.getCategory().equals("Toys"))
                .map(x->x.getPrice().multiply(BigDecimal.valueOf(0.9)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(sumtoysWithSale10PerCent);
        List<Product> toysInProducts = products
                .stream()
                .filter(x->x.getCategory().equals("Toys"))
                .collect(toList());
        toysInProducts.forEach(System.out::println);
        System.out.println("-".repeat(100));

        //4 задание
        System.out.println("Задание 4 ");
        System.out.println();
        List<Product> twoLvlClientListProducts = customers.stream()
                .filter(customer->customer.getLevel() == 2L)
                .flatMap(x-> Stream.of(x.getOrders()))
                .flatMap(x->x.stream()
                        .filter(j-> !j.getOrderDate().isBefore(LocalDate.of(2021, 2, 1)) && !j.getOrderDate().isAfter( LocalDate.of(2021, 4, 1)))
                        .flatMap(i-> i.getProducts().stream()))
                .collect(toList());
        twoLvlClientListProducts.forEach(System.out::println);
        System.out.println("-".repeat(100));

        //5 задание
        System.out.println("Задание 5 ");
        System.out.println();
        List<Product> twoCheapBooks = products.stream()
                .filter(x->x.getCategory().equals("Books"))
                .sorted(Comparator.comparing(Product::getPrice))
                .limit(2)
                .collect(toList());
        twoCheapBooks.forEach(System.out::println);
        System.out.println("-".repeat(100));

        //6 задание
        System.out.println("Задание 6 ");
        System.out.println();
        List<Order> threeLatestOrders = orders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate))
                .skip(orders.size()-3)
                .collect(toList());
        threeLatestOrders.forEach(System.out::println);
        System.out.println("-".repeat(100));

        //7 задание
        System.out.println("Задание 7 ");
        System.out.println();
        List<Order> sevenMarchOrders = orders.stream()
                .filter(x->x.getOrderDate().equals(LocalDate.of(2021, 3, 15)))
                .collect(toList());
        sevenMarchOrders.forEach(x-> System.out.println(x.getId()));
        List<Product> productFromSevenMarchOrders = sevenMarchOrders.stream()
                .flatMap(x->x.getProducts().stream())
                .sorted(Comparator.comparing(Product::getId))
                .collect(toList());
        productFromSevenMarchOrders.forEach(System.out::println);
        System.out.println("-".repeat(100));

        //8 задание
        System.out.println("Задание 8 ");
        System.out.println();
        BigDecimal totalPriceForFebruary2021Orders = orders.stream()
                .filter(x-> !x.getOrderDate().isBefore(LocalDate.of(2021, 2, 1)) && !x.getOrderDate().isAfter(LocalDate.of(2021, 2, 28)))
                .flatMap(x->x.getProducts().stream())
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println(totalPriceForFebruary2021Orders);
        System.out.println("-".repeat(100));

        //9 задание
        System.out.println("Задание 9 ");
        System.out.println();
        List<Product> fourteen2021MarchProducts = orders.stream()
                .filter(x -> x.getOrderDate().equals(LocalDate.of(2021, 3, 14)))
                .flatMap(x->x.getProducts().stream())
                .collect(toList());
        BigDecimal AvgPriceForMarch2021Orders = fourteen2021MarchProducts.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(fourteen2021MarchProducts.size()),2, RoundingMode.HALF_UP);
        System.out.println(AvgPriceForMarch2021Orders);
        System.out.println("-".repeat(100));

        //10 задание
        System.out.println("Задание 10 ");
        System.out.println();
        List<Product> bookProducts = products.stream()
                .filter(x->x.getCategory().equals("Books"))
                .collect(toList());
        BigDecimal sumPriceForBookProducts =bookProducts.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal avgPriceForBookProducts = bookProducts.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(bookProducts.size()),2, RoundingMode.HALF_UP);
        BigDecimal maxPriceForBookProducts = bookProducts.stream()
                .map(Product::getPrice)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
        BigDecimal minPriceForBookProducts = bookProducts.stream()
                .map(Product::getPrice)
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
        Long countBookProducts =  bookProducts.stream().count();
        System.out.println(sumPriceForBookProducts);
        System.out.println(avgPriceForBookProducts);
        System.out.println(maxPriceForBookProducts);
        System.out.println(minPriceForBookProducts);
        System.out.println(countBookProducts);
        System.out.println("-".repeat(100));

        //11 задание
        System.out.println("Задание 11 ");
        System.out.println();

        Map<Long,Integer> orderProductsMap = orders.stream()
                .collect(Collectors.toMap(
                        Order::getId,
                       o -> o.getProducts() == null ? 0 : o.getProducts().size()));
        for(Map.Entry<Long,Integer> entry : orderProductsMap.entrySet()){
            System.out.println("Key: "+ entry.getKey() + "\n Value: " + entry.getValue());
        }
        System.out.println("-".repeat(100));

        //12 задание
        System.out.println("Задание 12 ");
        System.out.println();
        Map<Customer,List<Order>> customerOrdersMap = customers.stream()
                .collect(Collectors.toMap(
                        x->x,
                        c -> c.getOrders() == null ? Collections.emptyList()
                                : c.getOrders().stream().collect(toList())
                ));
        for(Map.Entry<Customer,List<Order>> entry : customerOrdersMap.entrySet()){
            System.out.println("Key: "+ entry.getKey() + "\n Value: " + entry.getValue());
        }
        System.out.println("-".repeat(100));

        //13 задание
        System.out.println("Задание 13 ");
        System.out.println();

        Map<Order,Double> orderTotalPriceOfProduct = orders.stream()
                .collect(Collectors.toMap(
                        x->x,
                        p->p.getProducts()
                                .stream()
                                .map(Product::getPrice)
                                .mapToDouble(BigDecimal::doubleValue)
                        .sum()
                ));
        for(Map.Entry<Order,Double> entry : orderTotalPriceOfProduct.entrySet()){
            System.out.println("Key: "+ entry.getKey() + "\n Value: " + entry.getValue());
        }
        System.out.println("-".repeat(100));

        //14 задание
        System.out.println("Задание 14 ");
        System.out.println();
        Map<String, List<String>> namesByCategoryMap = products.stream()
                .collect(groupingBy(
                        Product::getCategory,
                        mapping(Product::getName, toList())
                ));
        for(Map.Entry<String, List<String>> entry : namesByCategoryMap.entrySet()){
            System.out.println("Key: "+ entry.getKey() + "\n Value: " + entry.getValue());
        }
        System.out.println("-".repeat(100));

        //15 задание
        System.out.println("Задание 15 ");
        System.out.println();
        Map<String,Product> theMostExpProductInCategory =  products.stream()
                .collect(toMap(
                        Product::getCategory,
                        Function.identity(),
                        (p1, p2) -> p1.getPrice().compareTo(p2.getPrice()) >= 0 ? p1 : p2));
        for(Map.Entry<String,Product> entry : theMostExpProductInCategory.entrySet()){
            System.out.println("Key: "+ entry.getKey() + "\n Value: " + entry.getValue());
        }
    }
}
