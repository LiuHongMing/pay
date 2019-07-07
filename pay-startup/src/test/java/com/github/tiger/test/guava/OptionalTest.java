package com.github.tiger.test.guava;

import com.google.common.base.Optional;
import org.junit.Test;

/**
 * @author liuhongming
 */
public class OptionalTest {

    @Test
    public void testOptional() {
        Integer i = null;

        Optional<Integer> nullOptional = Optional.fromNullable(i);

        if (nullOptional.isPresent()) {
            System.out.println("IntegerOptional get " + nullOptional.get());
        } else {
            System.out.println("IntegerOptional get " + nullOptional.orNull());
        }
    }

}
