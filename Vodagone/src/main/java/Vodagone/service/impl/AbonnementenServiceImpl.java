package Vodagone.service.impl;

import Vodagone.dataaccesslayer.dao.AbonnementenDao;
import Vodagone.dataaccesslayer.models.Abonnement;
import Vodagone.dataaccesslayer.models.AbonnementenResponse;
import Vodagone.dataaccesslayer.models.SpecificAbonnementResponse;
import Vodagone.service.AbonnementenService;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.util.ArrayList;

public class AbonnementenServiceImpl implements AbonnementenService {

    @Inject
    private AbonnementenDao abonnementenDao;

    @Override
    public AbonnementenResponse getAbonnementenUser(String token) {
        if(token == null || "".equals(token))
            throw new BadRequestException("Invalid request");

        return abonnementenDao.getAbonnementenUser(token);
    }

    @Override
    public void addAbonnementToUser(String token, int id, String aanbieder, String dienst) {
        if((token == null || "".equals(token)) || (id < 0) || (aanbieder == null || "".equals(aanbieder)) || (dienst == null || "".equals(dienst)))
            throw new BadRequestException("Invalid request");

        abonnementenDao.addAbonnementToUser(token, id, aanbieder, dienst);
    }

    @Override
    public ArrayList<Abonnement> getAllAvailableAbonnementen(String token, String filter) {
        if(token == null || "".equals(token))
            throw new BadRequestException("Invalid request");

        filter = filter.toLowerCase();
        String filterField = "";
        if("vodafone".equals(filter) || "ziggo".equals(filter)) {
            filterField = "aanbieder";
        }
        else if(!("vodafone".equals(filter)) && !("ziggo".equals(filter)) && !("".equals(filter))){
            filterField = "dienst";
        }

        if(filter.length() >= 1)
            filter = filter.substring(0,1).toUpperCase() + filter.substring(1).toLowerCase();
        System.out.println(filter);
        System.out.println(filterField);
        return abonnementenDao.getAvailableAbonnementen(token, filter, filterField).getAbonnementen();
    }

    @Override
    public SpecificAbonnementResponse getSpecificAbonnement(int id, String token) {
        if((id < 0) || (token == null || "".equals(token)))
            throw new BadRequestException("Invalid request");

        return abonnementenDao.getSpecificAbonnement(id, token);
    }

    @Override
    public void verwijderAbonnement(int id, String token) {
        if((id < 0) || (token == null || "".equals(token)))
            throw new BadRequestException("Invalid request");

        abonnementenDao.verwijderAbonnement(id, token);
    }

    @Override
    public void upgradeAbonnement(int id, String token, String verdubbeling) {
        if((id < 0) || (token == null || "".equals(token)) || (verdubbeling == null || "".equals(verdubbeling)))
            throw new BadRequestException("Invaled request");

        abonnementenDao.upgradeAbonnement(id, token, verdubbeling);
    }
}
