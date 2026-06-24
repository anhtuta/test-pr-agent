package hello.creatingobject;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
class Column<T> {
    String title;
    String fieldName;
    Function<T, ?> customExtractor;

    public Column<T> title(String title) {
        this.title = title;
        return this;
    }

    public Column<T> fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public Column<T> customValue(Function<T, ?> customExtractor) {
        this.customExtractor = customExtractor;
        return this;
    }

    public static <T> Column<T> create(Function<Column<T>, Column<T>> builder) {
        return builder.apply(new Column<T>());
    }

    public Column() {}

    public Column(String title, String fieldName, Function<T, ?> customExtractor) {
        this.title = title;
        this.fieldName = fieldName;
        this.customExtractor = customExtractor;
    }
}

/**
 * Ref: https://viblo.asia/p/khoi-tao-object-java-theo-mot-cach-khac-thuong-vyDZOnGkKwj
 * Bài này đọc khó hiểu quá, chưa xong phần 3
 * 
 * @author anhtu
 */
@Getter
@Setter
@Builder
public class Book {
    private String isbn;
    private String title;
    private String author;
    private String category;

    public Book() {}

    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public Book(String isbn, String title, String author, String category) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
    }

    public static Book quickInit(String isbn, String title) {
        Book book = new Book();
        book.isbn = isbn;
        book.title = title;
        return book;
    }

    public static Book fullInit(String isbn, String title, String author, String category) {
        Book book = new Book();
        book.isbn = isbn;
        book.title = title;
        book.author = author;
        book.category = category;
        return book;
    }


    @Override
    public String toString() {
        return String.format("[Book] isbn: %s, title: %s, author: %s, category: %s",
                isbn, title, author, category);
    }

    public static void main(String[] args) {
        // 1. Sử dụng Constructor
        // Khi muốn thay đổi argument, chúng ta có thể thay đổi constructor hiện tại (cách này không
        // an toàn lắm), hoặc tạo thêm constructor mới. Khi đó class của chúng ta sẽ xuất hiện khá
        // nhiều constructor mà không rõ ngữ cảnh sử dụng.
        Book b1 = new Book("1011", "Sapiens: A Brief History of Humankind",
                "Yuval Noah Harari");
        System.out.println(b1);

        // Để giải quyết vấn đề ngữ cảnh, chúng ta có thể chuyển sang static method.
        Book b2 = Book.fullInit("1012", "Conan Detective", "Gosho Aoyama", "Manga");
        System.out.println(b2);

        // 2. Sử dụng annotation Builder
        Book b3 = Book.builder()
                .title("The Devotion of Suspect X")
                .author("Keigo Higashino")
                .category("Mystery, Detective")
                .build();
        System.out.println(b3);

        // Bạn có thể thắc mắc, tại sao phải cầu kì như vậy, có thể dùng constructor rỗng và setter
        // là xong mà? Hãy thử nghĩ đến trường hợp bạn chỉ muốn khai báo trong một dòng duy nhất như
        // dưới đây
        List<Book> nonFictions = Arrays.asList(
                Book.builder().title("Sapiens: A Brief History of Humankind")
                        .author("Yuval Noah Harari").build(),
                Book.builder().title("The Defining Decade").author("Meg Jay").build(),
                Book.builder().title("The State of Affairs").author("Esther Perel").build());
        System.out.println("\nnonFictions:");
        nonFictions.stream().forEach(book -> {
            System.out.println(book);
        });

        // 3. Vấn đề với generic type
        List<Column<Book>> bookTable = Arrays.asList(
                Column.<Book>builder().title("Book ID").fieldName("isbn").build(),
                Column.<Book>builder().title("Name").fieldName("title").build(),
                Column.<Book>builder().title("Category")
                        .customExtractor(book -> book.getCategory()).build());
        System.out.println(bookTable);

        List<Column<Book>> bookTables2 = Arrays.asList(
                Column.create(c -> c.title("Book ID").fieldName("isbn")),
                Column.create(c -> c.title("Name").fieldName("title")),
                Column.create(c -> c.title("Category").customValue(book -> book.getCategory())));
        System.out.println(bookTables2);
    }

}
