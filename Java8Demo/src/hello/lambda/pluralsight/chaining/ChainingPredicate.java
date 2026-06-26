package hello.lambda.pluralsight.chaining;


import java.util.Objects;

/**
 * This one is similar to the built-in Predicate interface
 */
@FunctionalInterface
public interface ChainingPredicate<T> {

    boolean test(T t);

    default ChainingPredicate<T> or(ChainingPredicate<T> other) {
        Objects.requireNonNull(other);
        return (T t) -> this.test(t) || other.test(t);
    }

    default ChainingPredicate<T> and(ChainingPredicate<T> other) {
        Objects.requireNonNull(other);
        return (T t) -> this.test(t) && other.test(t);
    }

    default ChainingPredicate<T> negate() {
        return (T t) -> !this.test(t);
    }
}
