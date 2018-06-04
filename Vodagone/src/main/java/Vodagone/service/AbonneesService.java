package Vodagone.service;

import Vodagone.dataaccesslayer.models.Gebruiker;

import java.util.ArrayList;

public interface AbonneesService {
    ArrayList<Gebruiker> getAllAbonnees(String token);

    void shareAbonnement(int id, int idAbonnement);
}
