package com.github.tiger.test.java.lang;

import java.util.function.Function;

public class FunctionalTest {

    @FunctionalInterface
    public interface IConvert<F, T> {
        T convert(F from);
    }

    public static class Something {

        public Something() {
        }

        public Something(String something) {
            System.out.println(something);
        }

        static String startWith(String s) {
            return String.valueOf(s.charAt(0));
        }

        String endWith(String s) {
            return String.valueOf(s.charAt(s.length() - 1));
        }

        void endWith() {
        }
    }

    public static <T extends FunctionImpl> T newFunction(
            Function<String, T> factory, String val) {
        return factory.apply(val);
    }

    public static class FunctionImpl {
        public FunctionImpl(String s) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        IConvert<String, String> staticMethod = Something::startWith;
        System.out.println(staticMethod.convert("123"));

        Something something = new Something();
        IConvert<String, String> objectMethod = something::endWith;
        System.out.println(objectMethod.convert("Java"));

        IConvert<String, Something> constructor = Something::new;
        Something something1 = constructor.convert("constructors");
        System.out.println(something1.endWith("456"));

        IConvert<String, String> lambdaMethod = (String str) -> {
            return "lambda: " + str;
        };
        System.out.println(lambdaMethod.convert("use lambda"));

        FunctionImpl functionImpl = newFunction(FunctionImpl::new, "Hello world !!!");
    }

}
