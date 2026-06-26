package hello.lambda.pluralsight.chaining;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        var strings = List.of("one", "", "two", "***", "three", "", "four", "five", "***");
        var string1 = new ArrayList<>(strings);

        ChainingPredicate<String> isEmpty = s -> s.isEmpty();
        ChainingPredicate<String> isCorrupted = s -> s.equals("***");
        ChainingPredicate<String> isEmptyOrIsCorrupted = isEmpty.or(isCorrupted);

        strings.forEach(s -> System.out.println(s + ": " + isEmptyOrIsCorrupted.test(s)));

        System.out.println("----");

        string1.add(null);
        ChainingPredicate<String> isNotEmpty = s -> !s.isEmpty();
        ChainingPredicate<String> isNotNull = s -> s != null;
        ChainingPredicate<String> isNotEmptyAndNotNull = isNotNull.and(isNotEmpty); // MUST call isNotNull first
        string1.forEach(s -> System.out.println(s + ": " + isNotEmptyAndNotNull.test(s)));
    }
}
