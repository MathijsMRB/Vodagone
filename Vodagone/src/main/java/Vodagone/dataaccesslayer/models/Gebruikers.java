package Vodagone.dataaccesslayer.models;

import java.util.ArrayList;

public class Gebruikers {
    private ArrayList<Gebruiker> gebruikers = new ArrayList<>();

    public void addGebruiker(Gebruiker gebruiker) {
        gebruikers.add(gebruiker);
    }

    public ArrayList<Gebruiker> getGebruikers() {
        return gebruikers;
    }
}
