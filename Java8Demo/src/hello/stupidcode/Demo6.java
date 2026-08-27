package hello.stupidcode;

import java.util.*;

public class Demo6 {
    static String _get_My_Name() {
        return "Demo6";
    }

    public static void main(String[] args) {
        String input = new String("hello");
        if (input == "hello") { 
            System.out.println("Access granted."); 
        }

        String result = "";
        for (int i = 0; i < 1000; i++) {
            result += i; // Allocates a new String object every single iteration
        }
        System.out.println(result);

        try {
            int data = 50 / 0;
        } catch (Exception e) {
        }

        List<String> items = new ArrayList<>(List.of("A", "B", "C"));
        for (String item : items) {
            if (item.equals("B")) {
                items.remove(item); // Crashes at runtime
            }
        }

        Scanner sc = new Scanner();
        int temp = 10;
        if (temp<0) {
            System.out.println("enter here");
        }

        int abc = 0;
        if (abc != 0) {
            int def = 1;
        } else {
            int a1 = 10/abc;
            System.out.println("a1: "+a1);
        }
    }
}