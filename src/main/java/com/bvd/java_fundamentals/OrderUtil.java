package com.bvd.java_fundamentals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/*
 * Implement the methods below so that the requirements are met.
 */

public class OrderUtil {

    private OrderUtil() {
    }

    // retrieve orders from csv lines
    public static List<Order> parseCsvLines(final List<String> lines) {
        /*
        Initial am incercat sa adaptez acesta metoda,
        List<List<String>> records = new ArrayList<>();
try (BufferedReader br = new BufferedReader(new FileReader("book.csv"))) {
    String line;
    while ((line = br.readLine()) != null) {
        String[] values = line.split(COMMA_DELIMITER);
        records.add(Arrays.asList(values));
    }
}
        dar am realizat dupa ca am doar 7 elemente si ca as putea
        face citirea dupa ele, doar ca nu imi pot da seama pe moment
         */


        List<Order> result = new ArrayList<>();
        for (String line : lines) {
            if (lines.isEmpty()) return result;
            String[] parts = line.split(",");
            if (parts.length != 7) {
                continue;
            }
            String orderId = parts[0].trim();
            String customerId = parts[1].trim();
            String dateStr = parts[2].trim();
            String productName = parts[3].trim();
            String category = parts[4].trim();
            String priceStr = parts[5].trim();
            String qtyStr = parts[6].trim();
            try {
                LocalDate date = LocalDate.parse(dateStr);
                BigDecimal unitPrice = new BigDecimal(priceStr);
                int quantity = Integer.parseInt(qtyStr);

                result.add(new Order(orderId, customerId, date, productName, category, unitPrice, quantity));
            } catch (Exception ignored) {
            }
        }

        return result;
    }

    // calculate revenue by day
    // revenue = unitPrice * quantity
    //Aici am incercat sa fac calculul dar sincer.. cum nu am putut testa nu am idee daca am gresit ceva, sper
    //ca logica macar sa fie ok
    public static Map<LocalDate, BigDecimal> revenueByDay(final List<Order> orders) {
        Map<LocalDate, BigDecimal> byDay = new TreeMap<>();
        if (orders.isEmpty()) return byDay;
        for (Order o : orders) {
            BigDecimal lineRevenue = o.getUnitPrice().multiply(BigDecimal.valueOf(o.getQuantity()));
            byDay.merge(o.getOrderDate(), lineRevenue, BigDecimal::add);
        }

        return byDay;
    }

    // get top "n" products by revenue
    public static List<Map.Entry<String, BigDecimal>> topProductsByRevenue(final List<Order> orders, final int n) {
        Map<String, BigDecimal> revenue = new HashMap<>();
        for (Order o : orders) {
            if (o == null) continue;
            String name = o.getProductName() == null ? "" : o.getProductName().trim();
            BigDecimal rev = o.getUnitPrice().multiply(BigDecimal.valueOf(o.getQuantity()));
            revenue.merge(name, rev, BigDecimal::add);
        }

        return revenue.entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .toList();
    }


    // get customers who ordered products from at least "minCategories" different categories
    public static List<String> customersWithCategoryDiversity(final List<Order> orders, final int minCategories) {
        if (minCategories <= 0) return List.of();

        Map<String, Set<String>> byCustomer = new HashMap<>();
        for (Order o : orders) {
            byCustomer.computeIfAbsent(o.getCustomerId(), k -> new HashSet<>())
                    .add(o.getCategory() == null ? "" : o.getCategory().trim());
        }

        return byCustomer.entrySet().stream()
                .filter(e -> e.getValue().size() >= minCategories)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();
    }


    // find the first product containing a given substring (case-insensitive)
    //aici am facut filtrarea, din fericire am mai facut ceva asemanator anterior
    public static Optional<Order> findFirstProductContaining(final List<Order> orders, final String product) {
        String word = product.toLowerCase();

        for (Order o : orders) {
            String name = o.getProductName();
            if (name.toLowerCase().contains(word)) {
                return Optional.of(o);
            }
        }
        return Optional.empty();
    }
}
