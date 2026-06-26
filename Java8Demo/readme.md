# Java 8 có gì mới
- Default method : Cung cấp phương thức mặc định cho Interface -> các class khác
  khi implement interface này ko cần Override các default method này nữa
- Static method: cũng giống như phương thức default, ngoại trừ việc nó KHÔNG thể
  được override trong class được implements
```java
public interface DefaultMethodDemo {
    void doSomething();

    // this method can be overrided
    default void sayHello() {
        System.out.println("[DefaultMethodDemo] Hello Java8!");
    }

    // this method CANNOT be overrided
    static void sayGoobye() {
        System.out.println("[DefaultMethodDemo] Goodbye Java8!");
    }
}
```
- Functional Interface (Single Abstract Method (SAM)): Interface có duy nhất một
  abstract method. Lợi ích chính của functional interface là chúng ta có thể
  sử dụng Lambda Expression để tạo ra thể hiện (instance) cho interface đó.
```java
@FunctionalInterface
public interface MyInterface {
    // Chỉ được khai báo duy nhất 1 abstract method ở interface này
    void function();
}
```
- Method References: Nó cung cấp các cú pháp (syntax) hữu ích để truy cập trực tiếp
  tới các constructor hoặc method đã tồn tại của các lớp hoặc đối tượng trong Java
  mà không cần thực thi chúng. Method References là cú pháp viết tắt của biểu thức
  Lambda để gọi phương thức. Java 8 cho phép truyền một tham chiếu của một method
  hoặc constructor thông qua việc sử dụng từ khóa ```::```
```java
// VD 1 biểu thức Lamda:
str -> System.out.println(str);
// Viết lại theo cách của Method references:
System.out::println
```
- Lambda expression: Thêm khả năng xử lý function cho Java. Cái này khá giống
  arrow function trong Javascript
- Stream API : bao gồm các class, interface và enum để cho phép các hoạt động
  kiểu function trên các element (phần tử) của một Collection, Array. Nó thực
  hiện chỉ khi nó yêu cầu (lazy).
- Date Time API : cung cấp một số lớp mới trong gói java.time cùng với định dạng
  thời gian Joda.
- Optional : là một lớp được sử dụng để hạn chế với lỗi NullPointerException
  trong ứng dụng Java.
- New tools : Các công cụ và tiện ích mới cho trình biên dịch được thêm vào như jdeps.
- Nashorn, JavaScript Engine : là javascript Engine cho phép chạy javascript
  trên JVM. Nó tương tự như engine V8 cung cấp bởi chrome dùng để chạy Node.js.
  Nó tương thích với các ứng dụng Node.js trong khi đồng thời hỗ trợ các thư viện
  Java được gọi thông qua mã javascript chạy trên máy chủ.
-...

# Ref
- https://howtodoinjava.com/java8/java-streams-by-examples/
- https://gpcoder.com/3839-gioi-thieu-java-8/
