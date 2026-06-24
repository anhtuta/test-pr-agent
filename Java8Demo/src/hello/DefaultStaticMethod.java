package hello;

/**
 * default method: method mặc định của 1 interface, các class khác implement ko cần override lại
 * method này nữa (có thể override nhưng ko bắt buộc)
 * 
 * static method: cũng giống như phương thức default, ngoại trừ việc nó KHÔNG thể được override
 * trong class được implements
 */
public interface DefaultStaticMethod {

    void doSomething();

    default void sayHello() {
        System.out.println("[DefaultMethod] Hello Java8!");
    }

    static void sayGoobye() {
        System.out.println("[DefaultMethod] Goodbye Java8!");
    }
}
