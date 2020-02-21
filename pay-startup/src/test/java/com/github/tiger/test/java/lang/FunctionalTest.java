package com.github.tiger.test.java.lang;

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

    public static void main(String[] args) {
        IConvert<String, String> staticMethod = Something::startWith;
        System.out.println(staticMethod.convert("123"));

        Something something = new Something();
        IConvert<String, String> objectMethod = something::endWith;
        System.out.println(objectMethod.convert("Java"));

        IConvert<String, Something> constructor = Something::new;
        Something something1 = constructor.convert("constructors");
    }

}
