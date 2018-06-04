package dataaccesslayer.dao;

import Vodagone.dataaccesslayer.dao.AbonneesDao;
import Vodagone.dataaccesslayer.database.DBUtilsTest;
import Vodagone.dataaccesslayer.database.JdbcConnectionFactory;
import Vodagone.dataaccesslayer.models.Gebruiker;
import Vodagone.dataaccesslayer.models.Gebruikers;
import Vodagone.service.InitDatabaseUtility;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.BadRequestException;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AbonneesDaoTest {

    private final DBUtilsTest dbUtilsTest = new DBUtilsTest();
    private final int ID_GEBRUIKER = 0;
    private final int ID_ABONNEMENT = 0;
    private final String TOKEN = "1234-1234-1234";

    @Spy
    private JdbcConnectionFactory jdbcConnectionFactory;

    @InjectMocks
    private AbonneesDao abonneesDao;

    @Before
    public void initDatabase() throws SQLException {
        InitDatabaseUtility.initDatabase(this, jdbcConnectionFactory, dbUtilsTest);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForNullConnectionShareAbonnement() {
        when(jdbcConnectionFactory.getDBConnection()).thenReturn(null);
        abonneesDao.shareAbonnement(ID_GEBRUIKER, ID_ABONNEMENT);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForNullConnectionGetAllAbonnees() {
        when(jdbcConnectionFactory.getDBConnection()).thenReturn(null);
        abonneesDao.getAllAbonnees(TOKEN);
    }

    @Test
    public void shouldShareAbonnement() throws SQLException {
        abonneesDao.shareAbonnement(1, 5);
        assertTrue(dbUtilsTest.checkIfShareSucceeded(1, 5, jdbcConnectionFactory.getDBConnection()));
    }

    @After
    public void cleanDB() throws SQLException {
        dbUtilsTest.clearDB(jdbcConnectionFactory.getDBConnection());
    }
}
