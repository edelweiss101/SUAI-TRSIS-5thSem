package info.stepanoff.trsis.lab1.model;

public class Employee {
    public int ID = -1;
    public String name;
    public String department;
    public String position;

    // Конструктор класса
    public Employee() {
    }

    // Конструктор класса
    public Employee(int ID, String name, String department, String position) {
        this.ID = ID;
        this.name = name;
        this.department = department;
        this.position = position;
    }

    // Перегрузка оператора сравнения по ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return ID == employee.ID;
    }

    // Неявная конвертация в тип Int
    public int intValue() {
        return ID;
    }
}
