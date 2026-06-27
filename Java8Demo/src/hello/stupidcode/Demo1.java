package hello.stupidcode;

public class Demo1 {
    private String name;
    private String address;
    static String getMyName() {
        return "John Doe";
    }
    static String saySomethingToMe() {
        return "Hello " + getMyName() + "!" + "This is a stupid code" + "and I am a stupid person" + ", hu hu hu!!!";
    }

    public static void main(String[] args) {
        System.out.println("Hello " + get_my_Name() + "!");
        System.out.println(Say_something_to_me());
    }
}
