package library;

public abstract class Person {
    private final String id;
    private final String name;

    protected Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract int getMaxBooks();
}
