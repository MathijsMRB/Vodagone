package Vodagone.dataaccesslayer.models;


public class SpecificAbonnementResponse {
    private int id;
    private String aanbieder;
    private String dienst;
    private double prijs;
    private String startDatum;
    private String verdubbeling;
    private boolean deelbaar;
    private String status;

    public void setId(int id) {
        this.id = id;
    }

    public void setAanbieder(String aanbieder) {
        this.aanbieder = aanbieder;
    }

    public void setDienst(String dienst) {
        this.dienst = dienst;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public void setStartDatum(String startDatum) {
        this.startDatum = startDatum;
    }

    public void setVerdubbeling(String verdubbeling) {
        this.verdubbeling = verdubbeling;
    }

    public void setDeelbaar(boolean deelbaar) {
        this.deelbaar = deelbaar;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
