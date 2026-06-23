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
        //Получите список продуктов из категории "Books" с ценой более 100.
        System.out.println("Задание 1 ");
        System.out.println();
        List<Product> booksWithOneHundredPrice = products.stream()
                .filter(Objects::nonNull)
                .filter(x-> x.getPrice().compareTo(BigDecimal.valueOf(100)) >0)
                .filter(x->x.getCategory().equals("Books"))
                .toList();
        booksWithOneHundredPrice.forEach(System.out::println);
        System.out.println("-".repeat(100));

        //2 задание
        //Получите список заказов с продуктами из категории "Children's products".
        System.out.println("Задание 2 ");
        System.out.println();
        List<Order> ordersChildrensProducts = orders.stream()
                .filter(Objects::nonNull)
                .filter(x->x.getProducts().
                        stream().
                        anyMatch(p -> "Children's products".equals(p.getCategory())))
                .collect(toList());
        ordersChildrensProducts.forEach(System.out::println);
        System.out.println("-".repeat(100));

        //3 задание
        //Получите список продуктов из категории "Toys" и примените скидку 10% и получите сумму всех
        //продуктов.
        System.out.println("Задание 3 ");
        System.out.println();
        List<Product> toys = products.stream()
                .filter(Objects::nonNull)
                .filter(product -> "Toys".equals(product.getCategory()))
                .collect(toList());
        BigDecimal totalWithDiscount = toys.stream()
                .filter(Objects::nonNull)
                .map(product -> product.getPrice()
                        .multiply(new BigDecimal("0.9")))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(totalWithDiscount);
        toys.forEach(System.out::println);
        System.out.println("-".repeat(100));

        //4 задание
        //Получите список продуктов, заказанных клиентом второго уровня между 01-фев-2021 и 01-апр-2021.
        System.out.println("Задание 4 ");
        System.out.println();
        List<Product> twoLvlClientListProducts = customers.stream()
                .filter(Objects::nonNull)
                .filter(customer->customer.getLevel() == 2L)
                .flatMap(x-> Stream.of(x.getOrders()))
                .filter(Objects::nonNull)
                .flatMap(x->x.stream()
                        .filter(Objects::nonNull)
                        .filter(j-> !j.getOrderDate().isBefore(LocalDate.of(2021, 2, 1)) && !j.getOrderDate().isAfter( LocalDate.of(2021, 4, 1)))
                        .flatMap(i-> i.getProducts().stream()))
                .toList();
        twoLvlClientListProducts.forEach(System.out::println);
        System.out.println("-".repeat(100));

        //5 задание
        //Получите топ 2 самые дешевые продукты из категории "Books".
        System.out.println("Задание 5 ");
        System.out.println();
        List<Product> twoCheapBooks = products.stream()
                .filter(Objects::nonNull)
                .filter(x->"Book".equals(x.getCategory()))
                .sorted(Comparator.comparing(Product::getPrice))
                .limit(2)
                .collect(toList());
        twoCheapBooks.forEach(System.out::println);
        System.out.println("-".repeat(100));

        //6 задание
        //Получите 3 самых последних сделанных заказа
        System.out.println("Задание 6 ");
        System.out.println();
        List<Order> threeLatestOrders = orders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .collect(toList());
        threeLatestOrders.forEach(System.out::println);
        System.out.println("-".repeat(100));

        //7 задание
        //Получите список заказов, сделанных 15-марта-2021, выведите id заказов в консоль и затем верните
        //список их продуктов.
        System.out.println("Задание 7 ");
        System.out.println();
        List<Order> sevenMarchOrders = orders.stream()
                .filter(Objects::nonNull)
                .filter(x->x.getOrderDate().equals(LocalDate.of(2021, 3, 15)))
                .collect(toList());
        sevenMarchOrders.forEach(x-> System.out.println(x.getId()));
        List<Product> productFromSevenMarchOrders = sevenMarchOrders.stream()
                .filter(Objects::nonNull)
                .flatMap(x->x.getProducts().stream())
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Product::getId))
                .collect(toList());
        productFromSevenMarchOrders.forEach(System.out::println);
        System.out.println("-".repeat(100));

        //8 задание
        //Рассчитайте общую сумму всех заказов, сделанных в феврале 2021.
        System.out.println("Задание 8 ");
        System.out.println();
        BigDecimal totalPriceForFebruary2021Orders = orders.stream()
                .filter(Objects::nonNull)
                .filter(x-> !x.getOrderDate().isBefore(LocalDate.of(2021, 2, 1)) && !x.getOrderDate().isAfter(LocalDate.of(2021, 2, 28)))
                .flatMap(x->x.getProducts().stream())
                .filter(Objects::nonNull)
                .map(Product::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println(totalPriceForFebruary2021Orders);
        System.out.println("-".repeat(100));

        //9 задание
        //Рассчитайте средний платеж по заказам, сделанным 14-марта-2021.
        System.out.println("Задание 9 ");
        System.out.println();

        List<Product> fourteenTwentyTwentyOneMarchProducts = orders.stream()
                .filter(Objects::nonNull)
                .filter(x -> x.getOrderDate().equals(LocalDate.of(2021, 3, 14)))
                .flatMap(x -> x.getProducts().stream())
                .filter(Objects::nonNull)
                .collect(toList());


        if (fourteenTwentyTwentyOneMarchProducts.isEmpty()) {
            System.out.println("Нет заказов на указанную дату");
        } else {

            BigDecimal sum = fourteenTwentyTwentyOneMarchProducts.stream()
                    .map(Product::getPrice)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);


            BigDecimal AvgPriceForMarchTwentyTwentyOneOrders = sum.divide(
                    BigDecimal.valueOf(fourteenTwentyTwentyOneMarchProducts.size()),
                    2,
                    RoundingMode.HALF_UP
            );

            System.out.println(AvgPriceForMarchTwentyTwentyOneOrders);
        }

        System.out.println("-".repeat(100));

        //10 задание
        //Получите набор статистических данных (сумма, среднее, максимум, минимум, количество) для всех
        //продуктов категории "Книги".
        System.out.println("Задание 10 ");
        System.out.println();
        DoubleSummaryStatistics stats = products.stream()
                .filter(Objects::nonNull)
                .filter(product -> "Books".equals(product.getCategory()))
                .map(Product::getPrice)
                .filter(Objects::nonNull)
                .collect(Collectors.summarizingDouble(BigDecimal::doubleValue));
        System.out.println("Статистика по книгам:");
        System.out.println("Количество: " + stats.getCount());
        System.out.printf("Сумма: %.2f%n", stats.getSum());
        System.out.printf("Средняя цена:%.2f%n", stats.getAverage());
        System.out.printf("Максимальная цена: %.2f%n", stats.getMax());
        System.out.printf("Минимальная цена:%.2f%n", stats.getMin());

        System.out.println("-".repeat(100));

        //11 задание
        //Получите данные Map<Long, Integer> → key - id заказа, value - кол-во товаров в заказе
        System.out.println("Задание 11 ");
        System.out.println();

        Map<Long, Integer> orderProductsMap = orders.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Order::getId,                                   // id не должен быть null
                        o -> {
                            Set<Product> ps = o.getProducts();
                            return ps == null ? 0 : ps.size();          // или см. вариант ниже для ненулевых
                        },
                        (left, right) -> left,                          // при дубликате id берём первое значение
                        LinkedHashMap::new
                ));

        orderProductsMap.forEach((key, value) -> System.out.printf("Заказ #%d: %d товаров%n", key, value) );
        System.out.println("-".repeat(100));

        //12 задание
        //Создайте Map<Customer, List<Order>> → key - покупатель, value - список его заказов
        System.out.println("Задание 12 ");
        System.out.println();
        Map<Customer,List<Order>> customerOrdersMap = customers.stream()
                .filter(Objects::nonNull)
                .collect(toMap(
                        Function.identity(),
                        c -> c.getOrders() == null ? Collections.emptyList()
                                : c.getOrders().stream().collect(toList())
                ));
        for(Map.Entry<Customer,List<Order>> entry : customerOrdersMap.entrySet()){
            System.out.println("Key: "+ entry.getKey() + "\n Value: " + entry.getValue());
        }
        System.out.println("-".repeat(100));

        //13 задание
        //Создайте Map<Order, Double> → key - заказ, value - общая сумма продуктов заказа.
        System.out.println("Задание 13 ");
        System.out.println();

        Map<Order, BigDecimal> orderTotalPriceOfProduct = orders.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Function.identity(),
                        o -> {
                            Set<Product> ps = o.getProducts();
                            if (ps == null) return BigDecimal.ZERO;
                            return ps.stream()
                                    .filter(Objects::nonNull)
                                    .map(Product::getPrice)
                                    .filter(Objects::nonNull)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                        },
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
        orderTotalPriceOfProduct.forEach((order, total) ->
                System.out.printf(
                        "Заказ #%d: %s%n",
                        order.getId(),
                        total.setScale(2, RoundingMode.HALF_UP).toPlainString()
                )
        );
        System.out.println("-".repeat(100));

        //14 задание
        //Получите Map<String, List<String>> → key - категория, value - список названий товаров в категории
        System.out.println("Задание 14 ");
        System.out.println();
        Map<String, List<String>> namesByCategoryMap = products.stream()
                .filter(Objects::nonNull)
                .filter(p -> p.getCategory() != null && p.getName() != null)
                .collect(groupingBy(
                        Product::getCategory,
                        mapping(Product::getName, toList())
                ));
        for(Map.Entry<String, List<String>> entry : namesByCategoryMap.entrySet()){
            System.out.println("Key: "+ entry.getKey() + "\n Value: " + entry.getValue());
        }
        System.out.println("-".repeat(100));

        //15 задание
        //Получите Map<String, Product> → самый дорогой продукт по каждой категории.
        Map<String, Product> theMostExpProductInCategory = products.stream()
                .filter(Objects::nonNull)
                .filter(p -> p.getCategory() != null && p.getPrice() != null)
                .collect(Collectors.toMap(
                        Product::getCategory,
                        Function.identity(),
                        (p1, p2) -> p1.getPrice().compareTo(p2.getPrice()) >= 0 ? p1 : p2));

        theMostExpProductInCategory.forEach((k, v) ->
                System.out.println("Key: " + k + "\n Value: " + v));
    }
}
