package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Student {

    private int id;
    private String name;

    public Student(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "{id:" + id + ",name:'" + name + "'}";
    }

    static List<Student> generateStList() {
        List<Student> stList = new ArrayList<>();

        Student st1 = new Student(101, "Anhtu");
        Student st2 = new Student(102, "Huy ga");
        Student st3 = new Student(103, "Toan");
        Student st4 = new Student(104, "Nguyen Bka");
        Student st5 = new Student(105, "Diep");
        Student st6 = new Student(106, "Huyen Ta");
        Student st7 = new Student(107, "Junie Ngao");
        Student st8 = new Student(108, "Vegeta Prince");
        Student st9 = new Student(109, "Songoku");
        Student st10 = new Student(1010, "Hehe Haha");

        stList.add(st1);
        stList.add(st2);
        stList.add(st3);
        stList.add(st4);
        stList.add(st5);
        stList.add(st6);
        stList.add(st7);
        stList.add(st8);
        stList.add(st9);
        stList.add(st10);

        return stList;
    }

    public static void main(String[] args) {
        List<Student> stList = generateStList();
        Map<Integer, Student> stMap = new HashMap<>();

        stList.forEach(new Consumer<Student>() {
            @Override
            public void accept(Student st) {
                stMap.put(st.getId(), st);
            }
        });

        System.out.println(stMap);
    }
}


