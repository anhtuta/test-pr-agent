package hello.stream.lazy_evaluation;

import java.util.stream.IntStream;

/*
 * https://www.logicbig.com/tutorials/core-java-tutorial/java-util-stream/lazy-evaluation.html
 */
public class LazyExample {
    public static void main(String[] args) {
        System.out.println("Lazy evaluation in sequential stream");
        IntStream stream = IntStream.range(1, 5)
                .peek(i -> System.out.println("starting: " + i))
                .filter(i -> {
                    System.out.println("filtering: " + i);
                    return i % 2 == 0;
                })
                .peek(i -> System.out.println("post filtering: " + i));
        System.out.println("Invoking terminal method count.");
        System.out.println("The count is: " + stream.count());

        System.out.println("\n\nLazy evaluation in parallel stream");
        IntStream stream2 = IntStream.range(1, 5).parallel()
                .peek(i -> System.out.println("starting: " + i))
                .filter(i -> {
                    System.out.println("filtering: " + i);
                    return i % 2 == 0;
                })
                .peek(i -> System.out.println("post filtering: " + i));
        System.out.println("Invoking terminal method count.");
        System.out.println("The count is: " + stream2.count());
    }
}
