package com.github.tiger.test.java.util;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    @Data
    @AllArgsConstructor
    public static class Item {

        private String name;
        private int qty;
        private BigDecimal price;

        //constructors, getter/setters
    }

    public static void main(String[] args) {

        List<Item> items = Arrays.asList(
                new Item("apple", 10, new BigDecimal("9.99")),
                new Item("banana", 20, new BigDecimal("19.99")),
                new Item("orange", 10, new BigDecimal("29.99")),
                new Item("watermelon", 10, new BigDecimal("29.99")),
                new Item("papaya", 20, new BigDecimal("9.99")),
                new Item("apple", 10, new BigDecimal("9.99")),
                new Item("banana", 10, new BigDecimal("19.99")),
                new Item("apple", 20, new BigDecimal("9.99"))
        );

        Map<String, Long> counting = items.stream().collect(
                Collectors.groupingBy(Item::getName, Collectors.counting()));

        System.out.println(counting);

        Map<String, Integer> sum = items.stream().collect(
                Collectors.groupingBy(Item::getName, Collectors.summingInt(Item::getQty)));

        System.out.println(sum);

        //group by price
        Map<BigDecimal, List<Item>> groupByPriceMap =
                items.stream().collect(Collectors.groupingBy(Item::getPrice));

        System.out.println(groupByPriceMap);

        // group by price, uses 'mapping' to convert List<Item> to Set<String>
        Map<BigDecimal, Set<String>> result =
                items.stream().collect(
                        Collectors.groupingBy(Item::getPrice,
                                Collectors.mapping(Item::getName, Collectors.toSet())
                        )
                );

        System.out.println(result);

        //---------- map、reduce

        Map<String, Integer> accResult = Stream.of(
                "dog", "dog", "cat", "fish", "dog", "fish").map(item -> {
            Map<String, Integer> element = Maps.newHashMap();
            element.put(item, 1);
            return element;
        }).reduce(Maps.newHashMap(), (acc, item) -> {
            Object[] keys = item.keySet().toArray();
            String primary = (String) keys[0];
            if (acc.containsKey(primary)) {
                // map
                // Optional<Integer> total = Stream.of(acc.get(primary), item.get(primary))
                // .reduce((sum_, value) -> sum_ + value);

                // flatMap
                Optional<Integer> total = Stream.of(
                        Arrays.asList(acc.get(primary)), Arrays.asList(item.get(primary)))
                        .flatMap(values -> values.stream())
                        .reduce((sum_, value) -> sum_ + value);
                acc.put(primary, total.get());
            } else {
                acc.put(primary, 1);
            }
            return acc;
        });
        System.out.println(accResult);

    }

}
