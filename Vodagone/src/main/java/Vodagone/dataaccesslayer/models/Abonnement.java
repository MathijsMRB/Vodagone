package Vodagone.dataaccesslayer.models;

public class Abonnement {
    private int id;
    private String aanbieder;
    private String dienst;

    public Abonnement(int id, String aanbieder, String dienst){
        this.id = id;
        this.aanbieder = aanbieder;
        this.dienst = dienst;
    }

    public Abonnement() {}

    public int getId() {
        return id;
    }

    public String getAanbieder() {
        return aanbieder;
    }

    public String getDienst() {
        return dienst;
    }
}
