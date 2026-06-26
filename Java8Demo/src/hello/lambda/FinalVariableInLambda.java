package hello.lambda;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Biến trong lambda expression phải là final hoặc effectively final, nếu ko sẽ lỗi biên dịch (compile error).
 * Effectively final = the reference isn’t changing at all, meaning it’s effectively final.
 * Effectively final tức là các biến đó ko thay đổi reference trong lambda, tức là ko thay đổi giá trị của biến đó.
 * Ex: int a = 1; a = 2; // a ko phải là effectively final
 * Ex: int a = 1; // a là effectively final, nếu như ko bao giờ thay đổi giá trị của a
 *
 * Nhưng tại sao biến trong lambda phải là final hoặc effectively final? Vì lambda expression có thể được thực thi
 * sau khi phương thức chứa nó đã kết thúc, và nếu biến ko phải là final hoặc effectively final, thì giá trị của biến
 * đó có thể bị thay đổi sau khi phương thức kết thúc, và nếu như lambda expression sử dụng biến đó, thì giá trị của
 * biến đó sẽ ko còn đúng nữa (Copilot suggested on Intellij).
 *
 * But why can't we use non-final variables in lambda expressions? Because lambda expressions can be executed after
 * the method containing them has finished executing, and if the variable is not final or effectively final, then the
 * value of the variable can change after the method finishes, and if the lambda expression uses that variable, then the
 * value of the variable will no longer be correct (Copilot suggested on Intellij).
 *
 * Có một số cách để by pass vấn đề này:
 * 1. Sử dụng kiểu object, vì object ko thay đổi reference, chỉ thay đổi giá trị
 * 2. Sử dụng Atomic... class, vì nó giữ giá trị của biến đó và cho phép thay đổi giá trị của nó
 * 3. Sử dụng mảng, vì mảng cũng giữ reference của biến đó, và cho phép thay đổi giá trị của nó
 * (Copilot suggested on Intellij).
 *
 * Capturing Lambdas:
 * Lambda expressions có thể capture (chụp) biến ở ngoài phạm vi của nó. Thế nên ta gọi là capturing lambdas.
 * They can capture static variables, instance variables, and local variables, but only local variables must
 * be final or effectively final.
 *
 * Local variables are saved in the stack, each thread has its own call stack, and no thread can access another
 * thread stack, where are static and instance variables are stored on the heap which is shared among all threads.
 * Tức là, bên trong lambda có thể thay đổi các biến được lưu trữ trên heap, nhưng ko thể thay đổi các biến được lưu
 * trữ trên stack.
 *
 * [you.com]
 * Local variables are stored on the stack. When a lambda expression captures a local variable, it will create a copy
 * of it. Allowing that copy to be modified could lead to unpredictable behavior, especially in concurrent scenarios.
 * Nhưng unpredictable behavior là gì thì đéo biết!!!
 * Static and instance variables are stored on the heap. When a lambda expression captures an instance or static variable,
 * it does NOT create a new copy of that variable. Instead, it holds a reference to the original variable stored in the heap
 *
 * Ref:
 * - https://www.baeldung.com/java-lambda-effectively-final-local-variables
 * - https://www.codementor.io/@wesome/variable-used-in-lambda-expression-should-be-final-or-effectively-final-1urrrjtmac
 */
public class FinalVariableInLambda {

    /**
     * lambda is capturing the value of start, meaning making a copy of it. Forcing the variable to be final avoids
     * giving the impression that incrementing start inside the lambda could actually modify the start method parameter.
     * Why does it make a copy?
     * The lambda won’t get run until after the start method parameter gets garbage collected. Java has to make a copy
     * of start in order for this lambda to live outside of this method
     */
    Supplier<Integer> incrementer1(int start) {
        // return () -> start++; // Error: Variable used in lambda expression should be final or effectively final
        return () -> start;
    }

    private int start = 0;

    Supplier<Integer> incrementer2() {
        return () -> start++; // still works, because start is an instance variable, java stores it on the heap
    }

    // public int workaroundSingleThread1() {
    //     int holder = 2;
    //     IntStream sums = IntStream
    //             .of(1, 2, 3)
    //             .map(val -> val + holder);
    //
    //     // Error: Variable used in lambda expression should be final or effectively final
    //     holder = 0;
    //
    //     return sums.sum();
    // }
    public int workaroundSingleThread() {
        int[] holder = new int[] {2};
        IntStream sums = IntStream
                .of(1, 2, 3)
                .map(val -> val + holder[0]);

        holder[0] = 0;

        // Stream sẽ cộng các phần tử với 2? Sai, vì holder đã bị thay đổi giá trị = 0,
        // và khi lambda expression được thực thi, nó sẽ lấy giá trị mới nhất của holder là 0.
        // Do đó kết quả sẽ là 1 + 0 + 2 + 0 + 3 + 0 = 6, thay vì = 12
        return sums.sum();
    }

    public void workaroundMultithreading() {
        int[] holder = new int[] {2};
        Runnable runnable = () -> System.out.println(IntStream
                .of(1, 2, 3)
                .map(val -> val + holder[0])
                .sum());

        new Thread(runnable).start();

        // simulating some processing.
        // If it’s short enough to let the execution of the method terminate before the other thread
        // is executed it’ll print 6, otherwise, it’ll print 12.
        // Tức là việc processing này, thực tế có thể nhanh hay chậm. Mô phỏng việc nó thự thi nhanh = cách sleep 0
        try {
            // Print 6, because it's short enough to change the holder value before the other thread (above) is executed
            // Thread.sleep(0);

            // Print 12, because it's long enough to let the other thread is executed before the holder value is changed
            Thread.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        holder[0] = 0;
    }

    public static void main(String[] args) {
        // Ta sẽ đếm số phần tử trong list bằng lambda expression để tái hiện lỗi này
        List<String> languages = List.of("Java", "C#", "C++", "PHP", "Javascript");

        // Ko thể dùng các biến counter này
        int count1 = 0;
        count1++;
        Integer count2 = 0;

        // Có thể dùng kiểu object để by pass vấn đề final, vì object ko thay đổi reference, chỉ thay đổi giá trị
        AtomicInteger count3 = new AtomicInteger(0);
        int[] count4 = {0};

        languages.forEach(l -> {
            // count1++; // Error: Variable used in lambda expression should be final or effectively final
            // count2++; // Error: Variable used in lambda expression should be final or effectively final
            // System.out.println(count1); // since we modified count1, it's not effectively final, so error here
            count3.incrementAndGet();
            count4[0]++;
            // System.out.println(l);
        });

        System.out.println("Total languages: " + count3.get());
        System.out.println("Total languages: " + count4[0]);

        // Avoid workarounds
        FinalVariableInLambda app = new FinalVariableInLambda();
        System.out.println("workaroundSingleThread = " + app.workaroundSingleThread());
        System.out.println("workaroundMultithreading:");
        app.workaroundMultithreading();
    }

}
