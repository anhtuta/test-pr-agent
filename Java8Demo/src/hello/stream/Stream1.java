package hello.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * VD khi xem Youtube: từng phần nhỏ của video sẽ được load chứ ko phải cả video được load 1 lần ->
 * Đấy là stream
 * 
 * Stream vs Collection: 1 Collection là 1 in-memory data structure, Collection giữ toàn bộ giá trị
 * mà CTDL đó đang có (mọi phần tử trong Collection phải được tính toán trước khi nó được add vào
 * Collection).
 * 
 * 1 Stream theo lý thuyết là 1 fixed data structure, mà các elements are computed on demand -> Ý
 * tưởng: user chỉ extract những data mà họ muốn từ stream, và những data này chỉ được tạo ra
 * (invisible với user) khi cần thiết -> This is a form of a producer-consumer relationship
 * 
 * Nhận xét: các method filter, map, reduce của stream trong Java 8 giống hệt với các method
 * filter, map, reduce của Array trong Javascript
 * 
 * @author Anhtu
 */
public class Stream1 {

    static List<String> items;
    static Map<String, String> nameMap;
    static List<Map<Object, Object>> data;
    static String prefix = "Co";
    static List<String> inputSearches = Arrays.asList("a", "b", "c");

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
        // Tạo Stream cho những kiểu primitive
        IntStream.range(1, 4).forEach(System.out::println); // 1 2 3
        LongStream.of(1, 2, 3, 4).forEach(System.out::println);

        Stream<Integer> stream1 = Stream.of(1, 2, 3, 4, 5);
        stream1.forEach(t -> {
            System.out.println(t);
        });

        Stream<Integer> stream2 = Stream.of(new Integer[] {1, 2, 3, 4, 5});
        stream2.forEach(System.out::println);

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < 5; i++) {
            list.add(i);
        }
        Stream<Integer> stream3 = list.stream();
        stream3.forEach(System.out::println);

        // Stream filter() giúp loại bỏ các phần tử dựa trên các tiêu chí nhất định.
        List<String> filteredList = items.stream() //
                .filter(e -> (!e.startsWith(prefix))) //
                .collect(Collectors.toList());
        System.out.println("filteredList:\n\t" + filteredList);

        // Stream skip(), limit() hoàn toàn tương tự với OFSET và LIMIT trong SQL
        // Stream skip() được sử dụng để loại bỏ các phần tử n đầu tiên của Stream.
        // Nếu Stream này chứa ít hơn n phần tử thì luồng trống sẽ được trả lại.
        // Stream limit() được sử dụng để cắt giảm kích thước của Stream
        List<String> items2 = items.stream().limit(2).skip(1).collect(Collectors.toList());
        System.out.println("items2:\n\t" + items2);
        List<String> items3 = items.stream().skip(1).limit(2).collect(Collectors.toList());
        System.out.println("items3:\n\t" + items3); // items3 khác items2 nhé!

        // Stream map() giúp ánh xạ các phần tử tới các kết quả tương ứng
        List<String> upperItems = items.stream() //
                .map(String::toUpperCase) //
                .collect(Collectors.toList());
        System.out.println("upperItems:\n\t" + upperItems);

        // Ở trên dùng method reference String::toUpperCase, viết hẳn hoi và
        // đầy đủ, dễ hiểu thì như sau:
        List<String> lowerItems = items.stream() //
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String t) {
                        return t.toLowerCase();
                    }
                }).collect(Collectors.toList());
        System.out.println("lowerItems:\n\t" + lowerItems);

        List<Boolean> checkIfPrefix = items.stream() //
                .map(e -> (e.startsWith(prefix))) //
                .collect(Collectors.toList());
        System.out.println("checkIfPrefix:\n\t" + checkIfPrefix);

        List<Boolean> contain7Check = items.stream() //
                // Ánh xạ các phần tử của List String sang 1 List Boolean
                .map(new Function<String, Boolean>() {
                    @Override
                    public Boolean apply(String t) {
                        return t.contains("7");
                    }
                }).collect(Collectors.toList());
        System.out.println("contain7Check:\n\t" + contain7Check);

        // Stream sorted() giúp sắp xếp các phần tử theo một thứ tự xác định
        List<String> sortedItems = items.stream() //
                .sorted() //
                .collect(Collectors.toList());
        System.out.println("sortedItems:\n\t" + sortedItems);

        // Sort ngược lại
        List<String> sortedItems2 = items.stream() //
                .sorted((str1, str2) -> -str1.compareTo(str2)) //
                .collect(Collectors.toList());
        System.out.println("sortedItems2:\n\t" + sortedItems2);

        // Intermediate operations:
        // Có thể sử dụng 0 hoặc nhiều intermediate operations để chuyển đổi Stream ban đầu thành
        // những Stream mới. Mặc dù chúng ta có thể định nghĩa nhiều intermediate operation nhưng
        // chúng không thực thi các thao tác đó ngay lập tức, chỉ khi terminal operation được gọi
        // thì toàn bộ các thao tác đó mới được thực thi
        // Terminal Operations
        // Sử dụng terminal operation lấy về kết quả từ những intermediate operations đã định nghĩa.
        // Chúng ta có thể dễ dàng xác định đâu là intermediate operation, đâu là terminal operation
        // bởi vì terminal operation sẽ trả về void hoặc non-Stream object.
        // Sau khi terminal operation được gọi, Stream sẽ không sử dụng được nữa
        // Các method forEach(), collect() ở trên chính là Terminal Operations
        // Ngoài ra còn có 1 vài Terminal Operations, như:
        // anyMatch(), allMatch(), noneMatch(), count(), min(), max()
        Boolean isContainSony = items.stream().anyMatch(new Predicate<String>() {
            @Override
            public boolean test(String t) {
                return t.contains("Sony");
            }
        });
        System.out.println("isContainSony:\n\t" + isContainSony);

        Boolean isAllContainSony = items.stream() //
                .allMatch(str -> str.contains("Sony"));
        System.out.println("isAllContainSony:\n\t" + isAllContainSony);

        String maxItem = items.stream().max(new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        }).get();
        System.out.println("maxItem:\n\t" + maxItem);

        // Parallel Streams
        // Chúng ta thường sử dụng Parallel Streams trong môi trường multi-thread
        // khi mà chúng ta cần hiệu suất xử lý nhanh
        List<String> dummy1 = createDummyData();

        // Ko dùng List<String> dummy2 = dummy1, vì như vậy 2 thằng này sẽ trỏ tới
        // 1 object, và lúc này nếu dummy1 thay đổi cái gì thì dummy2 cũng thay đổi như vậy
        List<String> dummy2 = new ArrayList<>(dummy1);

        // Sequential Stream
        long startTime1 = System.nanoTime();
        long count1 = dummy1.stream().sorted().count();
        long endTime1 = System.nanoTime();
        long millis1 = TimeUnit.NANOSECONDS.toMillis(endTime1 - startTime1);
        System.out.println(String.format("sequential sort %d elements took: %d ms", //
                count1, millis1)); // 815 ms

        // Parallel Stream
        long startTime2 = System.nanoTime();
        long count2 = dummy2.parallelStream().sorted().count();
        long endTime2 = System.nanoTime();
        long millis2 = TimeUnit.NANOSECONDS.toMillis(endTime2 - startTime2);
        System.out.println(String.format("sequential sort %d elements took: %d ms", //
                count2, millis2)); // 471 ms -> Nhanh hơn tầm 42%

        // Hạn chế: Stream không thể tái sử dụng một khi đã gọi Terminal Operations
        // -> chúng ta phải tạo một Stream mới cho mọi hoạt động của thiết bị đầu cuối
        // mà chúng ta muốn thực thi
        Supplier<Stream<String>> streamSupplier = new Supplier<Stream<String>>() {
            @Override
            public Stream<String> get() {
                return items.stream();
            }
        };
        // viết gọn:
        streamSupplier = () -> items.stream();
        System.out.println(streamSupplier.get().anyMatch(s -> s.contains("Sony")));
        System.out.println(streamSupplier.get().allMatch(s -> s.contains("Sony")));

        // Bản chất của Stream
        List<String> result = Stream
                .of("bạn", "hãy", "like", "Fanpage", "loda", "dể", "cập", "nhật", "nhiều", "hơn")
                .filter(s -> {
                    System.out.println("[filtering] " + s);
                    return s.length() >= 4;
                }).map(s -> {
                    System.out.println("[mapping] " + s);
                    return s.toUpperCase();
                }).limit(3).collect(Collectors.toList());
        System.out.println("\n----------------------");
        System.out.println("Result:");
        result.forEach(System.out::println);

        // Kết quả:
        // [filtering] bạn // không thoả mãn
        // [filtering] hãy // tiếp tục tìm, cũng k thoả mãn
        // [filtering] like // thoả mãn
        // [mapping] like // mapping nó luôn
        // [filtering] Fanpage // lại quay lại filter tìm tiếp, thoả mãn
        // [mapping] Fanpage // mapping
        // [filtering] loda // thoả mãn
        // [mapping] loda // mapping
        // Đủ 3 trường hợp thoả mãn, dừng.
        // Bạn sẽ thấy rằng chương trình chỉ xử lý dữ liệu vừa đủ thoả mãn điều kiện limit(3) mà
        // thôi, còn lại nó sẽ bỏ qua để tối ưu hoá performance
        // Chứng tỏ Stream là Lazy evaluation. Hiểu đơn giản là nó sẽ không xử lý dữ liệu trực tiếp
        // qua từng bước, mà chờ bạn khai báo xong tất cả các thao tác operation như map,
        // filter,v.v.. cho tới khi gặp lệnh .collect() thì nó thực hiện toàn bộ trong một vòng lặp
        // duy nhất

        System.out.println("\n---------------------- flatMap demo");
        List<List<String>> listList = Arrays.asList(
                Arrays.asList("a1", "a2"),
                Arrays.asList("b", "b2", "b3"));
        System.out.println(listList);

        // Convert List<List<String>> to List<String>
        List<String> list2 = listList.stream()
                .flatMap(new Function<List<String>, Stream<String>>() {
                    @Override
                    public Stream<String> apply(List<String> t) {
                        return t.stream();
                    }
                })
                .collect(Collectors.toList());
        System.out.println(list2);

        // Dùng method reference sẽ ngắn hơn
        List<String> list3 = listList.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(list3);

        System.out.println("\n---------------------- flatMap demo 2");
        // Bài toán: Return item mà có các ký tự trong list inputSearch.
        // Khá dễ: filter items stream là được
        items.stream()
                .filter(item -> inputSearches.stream().anyMatch(inputSearch -> item.contains(inputSearch)))
                .forEach(item -> System.out.println(item));

        // Cách khác: start with inputSearches stream:
        inputSearches.stream()
                .flatMap(inputSearch -> items.stream().filter(item -> item.contains(inputSearch)))
                .forEach(item -> System.out.println(item));

    }

    public static List<String> createDummyData() {
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
        return values;
    }
}
