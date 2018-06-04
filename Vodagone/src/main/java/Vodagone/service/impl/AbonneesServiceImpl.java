package Vodagone.service.impl;

import Vodagone.dataaccesslayer.dao.AbonneesDao;
import Vodagone.dataaccesslayer.models.Gebruiker;
import Vodagone.service.AbonneesService;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.util.ArrayList;

public class AbonneesServiceImpl implements AbonneesService {

    @Inject
    private AbonneesDao abonneesDao;

    @Override
    public ArrayList<Gebruiker> getAllAbonnees(String token) {
        if(token == null || "".equals(token))
            throw new BadRequestException("Invalig request");

        return abonneesDao.getAllAbonnees(token).getGebruikers();
    }

    @Override
    public void shareAbonnement(int id, int idAbonnement) {
        if(id < 0 || idAbonnement < 0)
            throw new BadRequestException("Invalid request");

        abonneesDao.shareAbonnement(id, idAbonnement);
    }
}
