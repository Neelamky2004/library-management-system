package library;

public class Member extends Person {

    public Member(String id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxBooks() {
        return 3;
    }

    @Override
    public String toString() {
        return String.format("%-6s %-20s Student (max %d books)", getId(), getName(), getMaxBooks());
    }
}
