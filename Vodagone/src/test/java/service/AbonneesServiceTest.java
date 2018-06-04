package service;

import Vodagone.dataaccesslayer.dao.AbonneesDao;
import Vodagone.dataaccesslayer.database.DBUtilsTest;
import Vodagone.dataaccesslayer.database.JdbcConnectionFactory;
import Vodagone.dataaccesslayer.models.Gebruiker;
import Vodagone.dataaccesslayer.models.Gebruikers;
import Vodagone.service.InitDatabaseUtility;
import Vodagone.service.impl.AbonneesServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.BadRequestException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AbonneesServiceTest {

    private final DBUtilsTest dbUtilsTest = new DBUtilsTest();
    private final String TOKEN = "1234-1234-1234";

    @Mock
    private AbonneesDao abonneesDao;
    @InjectMocks
    private AbonneesServiceImpl abonneesService;
    @Spy
    private JdbcConnectionFactory jdbcConnectionFactory;

    @Before
    public void initDatabase() throws SQLException {
        InitDatabaseUtility.initDatabase(this, jdbcConnectionFactory, dbUtilsTest);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForNullTokenGetAllAbonnees() {
        abonneesService.getAllAbonnees(null);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForEmptyTokenGetAllAbonnees() {
        abonneesService.getAllAbonnees("");
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForIdLowerThan0() {
        abonneesService.shareAbonnement(-1, 0);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForIdAbonnementLowerThan0() {
        abonneesService.shareAbonnement(0, -1);
    }

    @Test
    public void shouldGetAllAbonnees(){
        Gebruikers gebruikers = new Gebruikers();
        gebruikers.addGebruiker(new Gebruiker(1, "Meron", "Meron.Brouwer@han.nl"));
        gebruikers.addGebruiker(new Gebruiker(2, "Dennis", "Dennis.Breuker@han.nl"));
        gebruikers.addGebruiker(new Gebruiker(3, "Michel", "Michel.Portier@han.nl"));

        when(abonneesDao.getAllAbonnees(TOKEN)).thenReturn(gebruikers);

        assertNotNull(abonneesService.getAllAbonnees(TOKEN));
    }

    @After
    public void cleanDB() throws SQLException {
        dbUtilsTest.clearDB(jdbcConnectionFactory.getDBConnection());
    }
}
