package hello.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Lambda Expression là một cách định nghĩa ngắn gọn khi implement một Functional Interface
 *
 * Sử dụng Functional interface: Trước Java 8: chúng ta tạo anonymous inner classes. Từ Java 8: sử
 * dụng biểu thức lambda thay vì các anonymous inner classes
 *
 * Cấu trúc của một lambda như sau: parameter -> { expression body [return] // (không trả về nếu là
 * void) }
 *
 * parameter -> expression body
 *
 *
 * @author Anhtu
 *
 *         Nhận xét: Lambda Expression khá giống Arrow Function trong Javascript
 */
public class LambdaExpressions {

    public static void main(String[] args) {
        List<String> languages = Arrays.asList("Java", "C#", "C++", "PHP", "Javascript");

        /*------------- First example -------------*/
        // No Lambda
        Collections.sort(languages, new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });

        // Using Lambda
        Collections.sort(languages, (str1, str2) -> {
            return str1.compareTo(str2);
        });

        // More simple
        Collections.sort(languages, (str1, str2) -> str1.compareTo(str2));

        /*-------------
         * Using Lambda in FunctionalInterface
         * Số lượng tham số của biểu thức Lambda phụ thuộc vào số lượng tham số của
         * phương thức trừu tượng của Functional Interface.
         * -------------*/
        // biểu thức Lambda không có tham số
        Sayable1 s1 = () -> "I have nothing to say";
        System.out.println(s1.say());

        // biểu thức Lambda có một tham số duy nhất
        Sayable2 s2 = (name) -> "Hello " + name;
        System.out.println(s2.say("Tuzaku"));

        // biểu thức Lambda có nhiều tham số, sử dụng hoặc không sử dụng từ khóa return
        Addable a1 = (int a, int b) -> {
            return a + b;
        };
        System.out.println(a1.add(5, 7));

        Addable a2 = (a, b) -> {
            return a + b;
        };
        System.out.println(a2.add(5, 7));

        Addable a3 = (a, b) -> (a + b);
        System.out.println(a3.add(5, 7));

        /*------------- biểu thức Lambda với vòng lặp Foreach -------------*/
        // No Lambda
        languages.forEach(new Consumer<String>() {
            @Override
            public void accept(String t) {
                System.out.println(t.toUpperCase());
            }
        });

        // Using Lambda
        languages.forEach(t -> {
            System.out.println(t.toUpperCase());
        });

        // More simple
        languages.forEach(t -> System.out.println(t.toUpperCase()));

        /*-------------
         * truy cập các biến phạm vi bên ngoài từ các biểu thức lambda:
         * Việc truy cập các biến phạm vi bên ngoài từ các biểu thức lambda rất
         * giống với các đối tượng ẩn danh (anonymous objects). Bạn có thể
         * truy cập bất kỳ biến final, static hoặc biến chỉ được gán một lần.
         * Biểu thức Lambda throw một lỗi biên dịch, nếu một biến được gán một
         * giá trị lần thứ hai.
         *  -------------*/
        int demo = 10;
        Addable a4 = (a, b) -> (a + b) * demo;
        System.out.println(a4.add(5, 7));
    }
}


@FunctionalInterface
interface Sayable1 {
    String say();
}


@FunctionalInterface
interface Sayable2 {
    String say(String name);
}


@FunctionalInterface
interface Addable {
    int add(int a, int b);
}
