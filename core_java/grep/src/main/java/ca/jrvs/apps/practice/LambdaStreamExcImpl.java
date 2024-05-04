package ca.jrvs.apps.practice;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.ArrayList;

public class LambdaStreamExcImpl implements LambdaStreamExc {

    @Override
    public Stream<String> createStrStream(String... strings) {
        return Stream.of(strings);
    }

    @Override
    public Stream<String> toUpperCase(String... strings) {
        return createStrStream(strings).map(String::toUpperCase);
    }

    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        return stringStream.filter(string -> !string.contains(pattern));
    }
    @Override
    public IntStream createIntStream(int[] arr) {
        return IntStream.of(arr);
    }

    @Override
    public <E> List<E> toList(Stream<E> stream) {

        List<E> streamList = new ArrayList<>();
        stream.forEach((element) -> streamList.add(element));
        return streamList;
    }

    @Override
    public List<Integer> toList(IntStream intStream) {
        List<Integer> intList = new ArrayList<>();
        intStream.forEach((num) -> intList.add(num));
        return intList;
    }

    @Override
    public IntStream createIntStream(int start, int end) {
        return IntStream.rangeClosed(start, end);
    }

    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        return intStream.mapToDouble(Math::sqrt);
    }

    @Override
    public IntStream getOdd(IntStream intStream) {
        return intStream.filter(num -> num % 2 != 0);
    }

    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        return message -> System.out.println(prefix + message + suffix);
    }

    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {
        Stream.of(messages).forEach(message -> printer.accept(message));
    }

    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {
        intStream.filter(num -> num % 2 != 0).forEach(num -> printer.accept(String.valueOf(num)));
    }

    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        return ints.flatMap(list -> list.stream()).map(num -> num * num);
    }
}