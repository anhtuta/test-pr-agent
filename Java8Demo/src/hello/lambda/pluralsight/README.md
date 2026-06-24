# Lambda Expressions in Java

by Jose Paumard

Link course: https://app.pluralsight.com/library/courses/lambda-expressions-java/table-of-contents

Review: khoá này tuy có level là Advanced, nhưng nó qua basic, chả có gì khó cả!

# Functional interface (FI)

- Là interface chỉ có duy nhất 1 abstract method
- Dùng `@FunctionalInterface` để nói với compiler rằng interface này là 1 Functional interface
- Cái annotation này là KHÔNG bắt buộc:
  - Nhưng nếu có, compiler sẽ báo lỗi nếu interface có > 1 abstract method
  - Lý do không bắt buộc: legacy reason: đảm bảo tính backward compatibility: các interface có từ trước Java 8, nếu chúng chỉ có 1 abstract method, chúng vẫn được coi là FI
- Lambda expression là implementation cho FI

# Lambda Expression

## Are Lambda objects?

Are lambda expressions objects in Java?

- That's a real question because apart from primitive types, everything is an object in Java.
- The answer to this question is not that simple.

What is happening when you try to call a method from object on a lambda expression, such as the `toString` method on a supplier:

```java
Supplier<String > supplier = () -> "one";
var s = supplier.get();
System.out.println("s = " + s); // s = one

var toString = supplier.toString();
System.out.println("toString = " + toString); // toString = org.demo.A_WritingFirstLambdas$$Lambda/@x0000019001003448@723279cf
```

- You can call the method from the object class on lambda expression.
- When you write a lambda expression in your code, **it is an implementation of a functional interface**, and you can **use it as an object**. That is, you can call `toString`, `equals`, or `hashCode` on it and that will work.
- **Lambda expressions are serializable**: you can even serialize them to transmit them over a network, for instance.

But the real question is, what is happening inside the Java virtual machine at runtime?

- At runtime, a lambda expression is implemented in such a way that it is actually **just a static method being called in the class** where you defined your lambda expression.
- So in the end, the JVM sees your lambda as just **a piece of code**.
- If this piece of code is small enough, it will be able to **inline** it wherever it is used in your application.
- There is a limit on the size of the code the JVM can inline, so **keeping your lambda small is better**. In this case, you will get the best performance possible.
- If you decide to call `toString` on your lambda, which is accepted by the compiler, then the JVM has no other choice but to **give you a real object** on which you have this `toString` method. And then, all this optimization is not possible anymore. --> Doing that will just kill your performance.

--> You should refrain from doing that: **Don't call any method from object on your lambda expressions** so that you can get the best performance out of them.

Túm lại:

- Lambda expression có thể coi là 1 object, ta có thể gọi các method của object
- Nhưng nếu không gọi các method đó, thì JVM sẽ tối ưu cái lambda expression này bằng cách tạo nó như 1 static method trong cái class mà ta đã define cái lambda đó --> Việc này tối ưu hơn
- Nếu cố gọi method của object, thì JVM sẽ phải tạo 1 object thực sự cho cái lambda đó --> Performance bị giảm

## Capturing lambda expressions

From within your lambda, if you want to reference variables, that is elements declared within your methods, then these variables need to be final or effectively final.

```java
public static void main(String[] args) {
    // This must be final, or effectively final (means: if u don't use final keyword, JVM still converts this to a final variable)
    var strings = new ArrayList<String>();
    var numbers = List.of(0, 1, 2, 3, 4);
    numbers.forEach(
        (Integer i) -> {
            strings.add(Integer.toString(i));
        }
    );
    System.out.println("strings = " + strings);
    // strings = new ArrayList<String>(); -> Error!
}
```

But a lambda can have references to static fields and instance fields. They do not need to be final.

```java
public class Capturing {
    static List<String> strings2 = new ArrayList<String>();

    public static void main(String[] args) {
        strings2 = new ArrayList<String>(); // No need to be final, can modify it
        var numbers = List.of(0, 1, 2, 3, 4);
        numbers.forEach(
            (Integer i) -> {
                strings2.add(Integer.toString(i));
            }
        );
    }
}
```
