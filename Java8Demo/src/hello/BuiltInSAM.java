package hello;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Functional interface còn được gọi là SAM type (Single abstract method) vì đúng theo trên định
 * nghĩa của nó.
 * 
 * Xem thêm file https://github.com/anhtuta/notes/blob/master/What_new_in_Java8.md
 * 
 * @author anhtu
 */
public class BuiltInSAM {

    public static void main(String[] args) {
        /*
         * Java cung cấp 4 functional interface có sẵn: `Consumer`, `Supplier`, `Function` và `Predicate`
         */
        // Nhận vào một param kiểu T, không trả về gì cả
        Consumer<Integer> print = num -> System.out.println(num);
        print.accept(5);

        // Ngược với Consumer: KHÔNG nhận vào param nào, nhưng lại trả ra một giá trị nào đó
        Supplier<Double> generate = () -> Math.random();
        Double d = generate.get(); // Số random nào đó
        System.out.println(d);

        // Nhận một tham số T, trả về một giá trị tương ứng kiểu R
        Function<String, Integer> getLength = str -> str.length();
        Integer len = getLength.apply("John Doe"); // 3
        System.out.println(len);

        // Nhận một tham số T và trả về boolean
        Predicate<Integer> checkAge = age -> age > 18;
        boolean isValidAge = checkAge.test(10); // false
        System.out.println(isValidAge);

        // 4 functional interface trên khá cơ bản, nhưng bạn sẽ thắc mắc vậy nó được dùng ở đâu? Câu trả lời
        // là dùng trong các operation của Stream API. Ex:
        List.of(2, 3, 5, 7).stream() // Lấy ra stream từ List
                .map(num -> num * 2) // Mỗi phần tử sẽ được nhân đôi
                .filter(num -> num < 10) // Chỉ giữ lại các phần tử nhỏ hơn 10
                .forEach(num -> System.out.println(num)); // In ra các phần tử còn lại

        // Ví dụ trên, map(), filter() và forEach() là các stream operation. Mỗi loại sẽ thực hiện chức năng
        // khác nhau, và nó sử dụng các loại functional interface tương ứng:
        // map() để biến đổi phần tử 1-1, nên nó dùng Function<T, R>
        // filter() để lọc ra các phần tử phù hợp, nên nó dùng Predicate<T>
        // forEach() chỉ dùng in ra thôi, không return gì nữa, nên nó dùng Consumer<T>

        /*
         * Java bổ sung thêm một số dạng "nâng cao" hơn nữa:
         */
        // 1. Dạng Bi...<T, U>:
        // BiConsumer<T, U> nhận 2 param T, U và không trả về
        // BiFunction<T, U, R> nhận 2 param T, U và trả về một R
        // BiPredicate<T, U> nhận 2 param T, U và trả về boolean
        //
        // (Chú ý sẽ không có BiSupplier nhé, vì Supplier ko nhận gì và return 1 value, ko thể có BiSupplier
        // để return 2 value được)
        BiConsumer<String, Integer> printInfo = (name, age) -> {
            System.out.println(name + " - " + age);
        };
        printInfo.accept("Doctor Strange", 29); // Doctor Strange - 29

        BiPredicate<String, Integer> isEqual = (a, b) -> a.equals(b.toString());
        System.out.println(isEqual.test("12", 12)); // true

        BiFunction<Integer, String, Boolean> isMidNight = (hour, meridiem) -> {
            if (hour.equals(12) && meridiem.equals("p.m"))
                return true;
            return false;
        };
        System.out.println(isMidNight.apply(12, "p.m")); // true

        // BiFunction có dùng trong reduce operation
        int res = List.of(1, 2, 3, 4).stream()
                .reduce(0, (acc, curr) -> acc + curr);
        System.out.println(res); // 10 = tổng list trên

        // 2. UnaryOperator<T> và BinaryOperator<T>
        // Đây là 2 functional interface đặc biệt, dựa theo Function<T, R>.
        // Cả hai đều là Function nhưng có kiểu nhận vào và kiểu trả về đều giống nhau.

        // UnaryOperator<T> tương đương Function<T, T>:
        // nhận vào 1 tham số kiểu T và cũng return kiểu T
        UnaryOperator<Integer> calSquare = a -> a * a;
        System.out.println(calSquare.apply(5)); // 25

        // BinaryOperator<T> tương đương BiFunction<T, T, T>:
        // nhận vào 2 tham số kiểu T và cũng return kiểu T
        BinaryOperator<Integer> findMax = (a, b) -> a > b ? a : b;
        System.out.println(findMax.apply(12, 27)); // 27

        // 3. Dạng Primitive...<T>
        // nếu bạn muốn functional interface có method "hình dạng" như thế này thì sao?
        // R apply(int value); // int chứ không phải Integer
        // Java cũng có các functional interface hỗ trợ primitive. Cú pháp chung là Primitive...<T>, trong
        // đó Primitive phải là Int, Long, Double, còn phần ... là những loại cơ bản như Consumer,... Ex:
        // IntConsumer, LongConsumer, DoubleConsumer
        // IntPredicate, LongPredicate, DoublePredicate
        // IntSupplier, LongSupplier, DoubleSupplier
        // IntFunction<T>, LongFunction<T>, DoubleFunction<T>

    }

}
