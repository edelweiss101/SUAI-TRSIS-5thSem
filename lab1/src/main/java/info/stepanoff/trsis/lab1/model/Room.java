package info.stepanoff.trsis.lab1.model;

public class Room {
    public int ID = -1;
    public String name;
    public String department;
    public int capacity = -1;

    // Конструктор класса
    public Room() {
    }
    
    // Конструктор класса
    public Room(int ID, String name, String department, int capacity) {
        this.ID = ID;
        this.name = name;
        this.department = department;
        this.capacity = capacity;
    }

    // Перегрузка оператора сравнения по ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return ID == room.ID;
    }

    // Неявная конвертация в тип Int
    public int intValue() {
        return ID;
    }
}
