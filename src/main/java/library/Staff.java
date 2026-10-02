package library;

public class Staff extends Person {

    public Staff(String id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxBooks() {
        return 5;
    }

    @Override
    public String toString() {
        return String.format("%-6s %-20s Staff (max %d books)", getId(), getName(), getMaxBooks());
    }
}
