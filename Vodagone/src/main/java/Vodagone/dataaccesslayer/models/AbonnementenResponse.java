package Vodagone.dataaccesslayer.models;

import java.util.ArrayList;

public class AbonnementenResponse {
    private ArrayList<Abonnement> abonnementen = new ArrayList<>();
    private Double totalPrice;

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addAbonnement(Abonnement abonnement){
        abonnementen.add(abonnement);
    }
}
