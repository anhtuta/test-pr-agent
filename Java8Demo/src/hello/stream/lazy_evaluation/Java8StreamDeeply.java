package hello.stream.lazy_evaluation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Ref:
 * https://gpcoder.com/6060-tim-hieu-co-che-lazy-evaluation-cua-stream-trong-java-8/
 */

@Data
@AllArgsConstructor
class Employee {
    String name;
    String department;
    int salary;
}

class EmployeeRepository {
    private static final Map<Integer, Employee> employees = new HashMap<>();
    static {
        employees.put(1, new Employee("gpcoder 1", "A", 50));
        employees.put(2, new Employee("gpcoder 2", "B", 100));
        employees.put(3, new Employee("gpcoder 3", "A", 150));
        employees.put(4, new Employee("gpcoder 4", "B", 200));
    }

    public Employee findById(Integer id) {
        System.out.println("findById: " + id);
        return employees.get(id);
    }
}

class EmployeeFilter {

    public static Predicate<Employee> filterDepartmentEqualsWith(String department) {
        return e -> {
            System.out.println("filterDepartmentEqualsWith: " + e);
            return e.getDepartment().equals(department);
        };
    }

    public static Predicate<Employee> filterSalaryGreaterThan(int salary) {
        return e -> {
            System.out.println("filterSalaryGreaterThan: " + e);
            return e.getSalary() >= salary;
        };
    }
}

public class Java8StreamDeeply {

    public static void main(String[] args) {
        EmployeeRepository employeeRepository = new EmployeeRepository();

        Integer[] empIds = {1, 2, 3, 4};

        System.out.println("Example 1:");
        Stream.of(empIds)
                .map(employeeRepository::findById)
                .filter(EmployeeFilter.filterSalaryGreaterThan(100))
                .filter(EmployeeFilter.filterDepartmentEqualsWith("A"))
                .collect(Collectors.toList());

        System.out.println("\n\nExample 2: when findFirst found the 3rd element is the result,"
                + " it will ignore 4th element: NOT do any intermediate operation on this element anymore");
        Stream.of(empIds)
                .map(employeeRepository::findById)
                .filter(EmployeeFilter.filterSalaryGreaterThan(100))
                .filter(EmployeeFilter.filterDepartmentEqualsWith("A"))
                .findFirst();
    }
}
