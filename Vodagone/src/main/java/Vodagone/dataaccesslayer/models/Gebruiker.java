package Vodagone.dataaccesslayer.models;

public class Gebruiker {
    private int id;
    private String name;
    private String email;

    public Gebruiker(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
