package Vodagone.service;

import Vodagone.dataaccesslayer.models.Abonnement;
import Vodagone.dataaccesslayer.models.AbonnementenResponse;
import Vodagone.dataaccesslayer.models.SpecificAbonnementResponse;

import java.util.ArrayList;

public interface AbonnementenService {

    AbonnementenResponse getAbonnementenUser(String token);

    void addAbonnementToUser(String token, int id, String aanbieder, String dienst);

    ArrayList<Abonnement> getAllAvailableAbonnementen(String token, String filter);

    SpecificAbonnementResponse getSpecificAbonnement(int id, String token);

    void verwijderAbonnement(int id, String token);

    void upgradeAbonnement(int id, String token, String verdubbeling);
}
