package Vodagone.dataaccesslayer.models;

import java.util.ArrayList;

public class AvailableAbonnementen {
    private ArrayList<Abonnement> abonnementen = new ArrayList<>();

    public void addAbonnement(Abonnement abonnement){
        abonnementen.add(abonnement);
    }

    public ArrayList<Abonnement> getAbonnementen() {
        return abonnementen;
    }
}
