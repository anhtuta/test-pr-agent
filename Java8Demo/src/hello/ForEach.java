package hello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Phương thức forEach() là một tính năng mới của java 8.
 * 
 * Nó là một phương thức mặc định (default method) được định nghĩa trong interface Iterable và
 * Stream. Các lớp Collection extends từ interface Iterable có thể sử dụng vòng lặp forEach() để
 * duyệt các phần tử
 * 
 * @author Anhtu
 */
public class ForEach {

    static List<String> items;
    static Map<String, String> nameMap;
    static List<Map<Object, Object>> data;

    static {
        items = new ArrayList<>();
        items.add("Converse 70s");
        items.add("Conan 97");
        items.add("Sony Xperia XZ2");
        items.add("Laptop");
        items.add("Apple");

        nameMap = new HashMap<String, String>();
        nameMap.put("A", "Alex");
        nameMap.put("B", "Brian");
        nameMap.put("C", "Charles");

        data = new ArrayList<>();
        Map<Object, Object> tmpMap = new HashMap<>();
        tmpMap.put("name", "Anhtu");
        tmpMap.put("dob", 1995);
        tmpMap.put("address", "Ha Noi");
        data.add(tmpMap);

        tmpMap = new HashMap<>();
        tmpMap.put("name", "Junie");
        tmpMap.put("dob", 1994);
        tmpMap.put("address", "Ha Nam");
        data.add(tmpMap);

        tmpMap = new HashMap<>();
        tmpMap.put("name", "Vegeta");
        tmpMap.put("dob", 1900);
        tmpMap.put("address", "Saiyan");
        data.add(tmpMap);
    }

    public static void main(String[] args) {

        /************** With List **************/
        System.out.println("\n=====> C1:");
        Consumer<String> makeUpperCase = new Consumer<String>() {
            @Override
            public void accept(String t) {
                System.out.println(t.toUpperCase());
            }
        };
        items.forEach(makeUpperCase);

        System.out.println("\n=====> C2:");
        items.forEach(new Consumer<String>() {
            @Override
            public void accept(String t) {
                System.out.println(t.toUpperCase());
            }
        });

        System.out.println("\n=====> C3:");
        items.forEach(t -> System.out.println(t.toUpperCase()));

        System.out.println("\n=====> C4:");
        items.forEach(System.out::println);


        /************** With Map **************/
        System.out.println("\n=====> C1:");
        BiConsumer<String, String> bilConsumer = new BiConsumer<String, String>() {
            @Override
            public void accept(String key, String value) {
                System.out.println(key + ": " + value);
            }
        };
        nameMap.forEach(bilConsumer);

        System.out.println("\n=====> C2:");
        nameMap.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String key, String value) {
                System.out.println(key + ": " + value);
            }
        });

        System.out.println("\n=====> C3:");
        nameMap.forEach((key, value) -> System.out.println(key + ": " + value));


        /************** With ListMap **************/
        // Ta sẽ duyệt qua thằng data rồi add nó vào 3 list
        List<String> nameList = new ArrayList<>();
        List<Integer> dobList = new ArrayList<>();
        List<String> addressList = new ArrayList<>();

        System.out.println("\n=====> C1:");
        data.forEach(new Consumer<Map<Object, Object>>() {
            @Override
            public void accept(Map<Object, Object> map) {
                if (map.get("name") != null)
                    nameList.add(map.get("name").toString());
                if (map.get("dob") != null)
                    dobList.add(Integer.valueOf(map.get("dob") + ""));
                if (map.get("address") != null)
                    addressList.add(map.get("address").toString());
            }
        });
        System.out.println(nameList);
        System.out.println(dobList);
        System.out.println(addressList);

        System.out.println("\n=====> C2:");
        data.forEach(map -> {
            if (map.get("name") != null)
                nameList.add(map.get("name").toString());
            if (map.get("dob") != null)
                dobList.add(Integer.valueOf(map.get("dob") + ""));
            if (map.get("address") != null)
                addressList.add(map.get("address").toString());
        });
        System.out.println(nameList);
        System.out.println(dobList);
        System.out.println(addressList);

        List<String> languages = Arrays.asList("Java", "C#", "C++", "PHP", "Javascript");
        // Dùng Method reference trong vd này ko có tác dụng, vì method toUpperCase
        // chỉ update data của từng bản copy của từng element, chứ ko thay đổi gì list languages
        languages.forEach(String::toUpperCase);
        languages.forEach(lang -> {
            System.out.println(lang.toUpperCase());
        });
    }
}
