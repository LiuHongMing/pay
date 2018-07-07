package com.github.tiger.test.guava;

import com.google.common.base.Optional;

public class OptionalTest {

    public static void main(String[] args) {
        Integer i = null;

        Optional<Integer> nullOptional = Optional.fromNullable(i);

        if (nullOptional.isPresent()) {
            System.out.println("IntegerOptional get " + nullOptional.get());
        } else {
            System.out.println("IntegerOptional get " + nullOptional.orNull());
        }

    }

}
