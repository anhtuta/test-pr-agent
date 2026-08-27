package hello.stupidcode;

import java.util.*;

public class Demo5 {
    static String _get_My_Name() {
        return "Demo5";
    }

    public void Read_File(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        System.out.println(br.readLine());
        br.close(); 
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
    }
}