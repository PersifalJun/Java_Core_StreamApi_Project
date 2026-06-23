package stream_api.model;

import org.w3c.dom.ls.LSOutput;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamApiTrainingDemo {

    public static void main(String[] args) {
        //Инициализация
        Product product1 = new Product(1L, "Laptop ", "Books", new BigDecimal("899.99"));
        Product product2 = new Product(2L, "Mouse", "Children's products", new BigDecimal("19.90"));
        Product product3 = new Product(3L, "Mechanical Keyboard", "Toys", new BigDecimal("79.00"));
        Product product4 = new Product(4L, "Office Chair", "Books", new BigDecimal("99.50"));
        Product product5 = new Product(5L, "Desk", "Toys", new BigDecimal("349.00"));

        Set<Product> products = Set.of(product1, product2, product3, product4, product5);

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

        Set<Order> orders = Set.of(order1, order2, order3, order4, order5, order6, order7, order8, order9, order10, order11);
        Customer customer1 = new Customer(101L, "Oleg", 1L, Set.of(order1, order2, order3, order4, order5));
        Customer customer2 = new Customer(102L, "Dima", 2L, Set.of(order6, order7, order8, order9, order10));
        Customer customer3 = new Customer(103L, "Kolya", 3L, Set.of(order1, order3, order5, order7, order9));
        Customer customer4 = new Customer(104L, "Evgeniy", 4L, Set.of(order2, order4, order6, order8, order11));
        Customer customer5 = new Customer(105L, "Alex", 5L, Set.of(order1, order4, order7, order8, order11));
        List<Customer> customers = List.of(customer1, customer2, customer3, customer4, customer5);

        // 1.Получите список продуктов из категории "Books" с ценой более 100.
        System.out.println("1 task");
        List<Product> booksProduct = products.stream()
                .filter(Objects::nonNull)
                .filter(product -> "Books".equals(product.getCategory()))
                .filter(product -> product.getPrice().compareTo(BigDecimal.valueOf(100)) > 0)
                .toList();

        booksProduct.forEach(System.out::println);
        System.out.println("-".repeat(100));
        System.out.println();

        //2.Получите список заказов с продуктами из категории "Children's products".
        System.out.println("2 task");
        List<Order> childrenProducts = orders.stream()
                .filter(Objects::nonNull)
                .filter(order -> order.getProducts().stream().anyMatch(product -> "Children's products".equals(product.getCategory())))
                .toList();

        childrenProducts.forEach(System.out::println);
        System.out.println("-".repeat(100));
        System.out.println();

        //3. Получите список продуктов из категории "Toys" и примените скидку 10% и получите сумму всех
        //продуктов.
        System.out.println("3 task");
        List<Product> toysProducts = products.stream()
                .filter(Objects::nonNull)
                .filter(product -> "Toys".equals(product.getCategory()))
                .toList();

        toysProducts.forEach(System.out::println);
        System.out.println();

        BigDecimal sumOfToysProducts = toysProducts.stream()
                .map(Product::getPrice)
                .map(price -> price.multiply(BigDecimal.valueOf(0.9)))
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Sum of toys with 10% discount is: " + sumOfToysProducts);
        System.out.println("-".repeat(100));
        System.out.println();

        //4. Получите список продуктов, заказанных клиентом второго уровня между 01-фев-2021 и 01-апр-2021.
        System.out.println("4 task");
        LocalDate start = LocalDate.of(2021, 2, 1);
        LocalDate end = LocalDate.of(2021, 4, 1);

        List<Product> productsOrderedByUserWith2Lvl = customers.stream()
                .filter(Objects::nonNull)
                .filter(customer -> Objects.equals(customer.getLevel(), 2L))
                .flatMap(customer -> customer.getOrders().stream())
                .filter(Objects::nonNull)
                .filter(order ->
                        !order.getOrderDate().isBefore(start)
                                && !order.getOrderDate().isAfter(end))
                .flatMap(order -> order.getProducts().stream())
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        //5. Получите топ 2 самые дешевые продукты из категории "Books".
        System.out.println("5 task");
        List<Product> cheapestProducts = products.stream()
                .filter(Objects::nonNull)
                .filter(product -> "Books".equals(product.getCategory()))
                .sorted(Comparator.comparing(Product::getPrice))
                .limit(2)
                .toList();

        cheapestProducts.forEach(System.out::println);
        System.out.println("-".repeat(100));
        System.out.println();

        //6. Получите 3 самых последних сделанных заказа.
        System.out.println("6 task");
        List<Order> recentOrders = orders.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .toList();

        recentOrders.forEach(System.out::println);
        System.out.println("-".repeat(100));
        System.out.println();

        // 7. Получите список заказов, сделанных 15-марта-2021, выведите id заказов в консоль и затем верните
        //список их продуктов.
        System.out.println("7 task");
        List<Order> ordersOn15March = orders.stream()
                .filter(Objects::nonNull)
                .filter(order -> order.getOrderDate().equals(LocalDate.of(2021, 3, 15)))
                .toList();
        ordersOn15March.forEach(System.out::println);
        ordersOn15March.stream().map(Order::getId).filter(Objects::nonNull).forEach(System.out::println);
        List<Product> productsFromOrders = ordersOn15March.stream()
                .flatMap(order -> order.getProducts().stream())
                .filter(Objects::nonNull)
                .toList();
        productsFromOrders.forEach(System.out::println);
        System.out.println("-".repeat(100));
        System.out.println();

        //8. Рассчитайте общую сумму всех заказов, сделанных в феврале 2021.
        System.out.println("8 task");
        YearMonth february2021 = YearMonth.of(2021, 2);

        BigDecimal sumOfOrdersOn2021February = orders.stream()
                .filter(Objects::nonNull)
                .filter(order ->
                        YearMonth.from(order.getOrderDate()).equals(february2021))
                .flatMap(order -> order.getProducts().stream())
                .filter(Objects::nonNull)
                .map(Product::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total sum for orders on February 2021: "+sumOfOrdersOn2021February);
        System.out.println("-".repeat(100));
        System.out.println();

        //9. Рассчитайте средний платеж по заказам, сделанным 14-марта-2021.
        System.out.println("9 task");
        List<BigDecimal> orderTotals = orders.stream()
                .filter(Objects::nonNull)
                .filter(order->order.getOrderDate().equals(LocalDate.of(2021,3,14)))
                .map(order-> order.getProducts().stream()
                        .map(Product::getPrice)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO,BigDecimal::add))
                .toList();

        BigDecimal avgPurchase = orderTotals.isEmpty() ?
                BigDecimal.ZERO :
                orderTotals.stream()
                        .reduce(BigDecimal.ZERO, BigDecimal::add).
                        divide(BigDecimal.valueOf(orderTotals.size()), 2, BigDecimal.ROUND_HALF_UP);

        System.out.println("Avg purchase for orders on February 2021: "+avgPurchase);
        System.out.println("-".repeat(100));
        System.out.println();
        //10. Получите набор статистических данных (сумма, среднее, максимум, минимум, количество) для всех
        //продуктов категории "Книги".
        System.out.println("10 task");
        DoubleSummaryStatistics stats = products.stream()
                .filter(Objects::nonNull)
                .filter(product->"Books".equals(product.getCategory()))
                .map(Product::getPrice)
                .filter(Objects::nonNull)
                .collect(Collectors.summarizingDouble(BigDecimal::doubleValue));

//        Или так
//        DoubleSummaryStatistics stats = products.stream()
//                .filter(Objects::nonNull)
//                .filter(product -> "Books".equals(product.getCategory()))
//                .map(Product::getPrice)
//                .filter(Objects::nonNull)
//                .mapToDouble(BigDecimal::doubleValue)
//                .summaryStatistics();

        System.out.println("Sum: "+stats.getSum());
        System.out.println("Min: "+stats.getMin());
        System.out.println("Max: "+stats.getMax());
        System.out.println("Avg: "+stats.getAverage());
        System.out.println("Count: "+stats.getCount());
        System.out.println("-".repeat(100));
        System.out.println();

    //11.Получите данные Map<Long, Integer> → key - id заказа, value - кол-во товаров в заказе
        System.out.println("11 task");
        Map<Long,Integer> result = orders.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Order::getId,
                        order->{
                            Set<Product> productsFromOrder = order.getProducts();
                            return productsFromOrder == null ? 0 : productsFromOrder.size();
                        },
                        (left, right) -> left,
                        LinkedHashMap::new
                ));

        result.forEach((key, value) -> System.out.printf("Заказ #%d: %d товаров%n", key, value) );
        System.out.println("-".repeat(100));
        System.out.println();

        //12.Создайте Map<Customer, List<Order>> → key - покупатель, value - список его заказов
        System.out.println("12 task");
        Map<Customer,List<Order>> resultMap = customers.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Function.identity(),
                        customer -> customer.getOrders() == null
                                ? List.of()
                                : customer.getOrders().stream()
                                .filter(Objects::nonNull)
                                .toList()
                ));

        resultMap.forEach((key, value) -> {
                    System.out.println("Посетитель с id: " + key.getId());
                    System.out.println("Заказы:");
                    value.forEach(System.out::println);
                    System.out.println();
                });
        System.out.println("-".repeat(100));
        System.out.println();

        //13.Создайте Map<Order, Double> → key - заказ, value - общая сумма продуктов заказа.
        System.out.println("13 task");
        Map<Order, Double> orderMap= orders.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Function.identity(),
                        order->{
                            Set<Product> productsFromOrder = order.getProducts();
                            BigDecimal totalSum = productsFromOrder == null ? BigDecimal.ZERO : productsFromOrder.stream()
                                                            .filter(Objects::nonNull)
                                                            .map(Product::getPrice)
                                                            .filter(Objects::nonNull)
                                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                            return totalSum.doubleValue();
                        }
                ));

        orderMap.forEach((key, value) -> System.out.printf("Заказ #%d,  сумма товаров: %s%n", key.getId(), value) );
        System.out.println("-".repeat(100));
        System.out.println();

        //14.Получите Map<String, List<String>> → key - категория, value - список названий товаров в категории
        System.out.println("14 task");
        Map<String, List<String>> categoryMap = products.stream()
                .filter(Objects::nonNull)
                .filter(product -> product.getCategory()!=null && product.getName()!=null)
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.mapping(Product::getName, Collectors.toList())
                ));
        categoryMap.forEach((key, value) -> {
            System.out.println("Категория: " + key);
            System.out.println("Список названий товаров:");
            value.forEach(System.out::println);
            System.out.println();
        });
        //15 задание
        //Получите Map<String, Product> → самый дорогой продукт по каждой категории.
        System.out.println("15 task");
        Map<String, Product> productMap = products.stream()
                .filter(Objects::nonNull)
                .filter(product ->
                        product.getCategory() != null
                                && product.getPrice() != null)
                .collect(Collectors.toMap(
                        Product::getCategory,
                        Function.identity(),
                        (p1, p2) -> p1.getPrice().compareTo(p2.getPrice()) >= 0 ? p1 : p2,
                        LinkedHashMap::new
                ));

        productMap.forEach((key, value) -> System.out.printf("Категория #%s,  самый дорогой продукт: %s%n", key, value.getName()) );
        System.out.println("-".repeat(100));
        System.out.println();


    }
}
