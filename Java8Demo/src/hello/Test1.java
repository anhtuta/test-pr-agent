package hello;

public class Test1 implements FunctionalInterfaceDemo, DefaultStaticMethod {

    @Override
    public void firstWork() {
        System.out.println("[Test1] This is firstwork");
    }

    @Override
    public void doSomething() {
        System.out.println("[Test1] This is doSomething");
    }

    // Override 1 default method
    @Override
    public void sayHello() {
        System.out.println("[Test1] Hello!");
    }

    // Cannot Override 1 static method
//    @Override
//    public void sayGoobye() {
//        System.out.println("[Test1] Hello!");
//    }

    public static void main(String[] args) {
        Test1 t1 = new Test1();
        t1.doSomething();
        t1.firstWork();
        t1.sayHello();
        DefaultStaticMethod.sayGoobye();

        FunctionalInterfaceDemo demo = new FunctionalInterfaceDemo() {
            @Override
            public void firstWork() {
                System.out.println("[Demo] This is firstwork");
            }
        };
        demo.firstWork();

        FunctionalInterfaceDemo demo2 = () -> {
            System.out.println("[Demo2] This is firstwork");
        };
        demo2.firstWork();

    }
}
