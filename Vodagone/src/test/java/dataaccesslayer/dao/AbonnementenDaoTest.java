package dataaccesslayer.dao;

import Vodagone.dataaccesslayer.dao.AbonnementenDao;
import Vodagone.dataaccesslayer.database.DBUtilsTest;
import Vodagone.dataaccesslayer.database.JdbcConnectionFactory;
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

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AbonnementenDaoTest {

    private final DBUtilsTest dbUtilsTest = new DBUtilsTest();
    private final String TOKEN = "1234-1234-1234";

    @Spy
    private JdbcConnectionFactory jdbcConnectionFactory;

    @InjectMocks
    private AbonnementenDao abonnementenDao;

    @Before
    public void initDatabase() throws SQLException {
        InitDatabaseUtility.initDatabase(this, jdbcConnectionFactory, dbUtilsTest);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForNulConnectionGetAbonnementenUser() {
        when(jdbcConnectionFactory.getDBConnection()).thenReturn(null);
        abonnementenDao.getAbonnementenUser(TOKEN);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForNullConnectionAddAbonnementToUser() {
        when(jdbcConnectionFactory.getDBConnection()).thenReturn(null);
        abonnementenDao.addAbonnementToUser(TOKEN, 1, "test", "test");
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForNullConnectionGetAvailableAbonnementen() {
        when(jdbcConnectionFactory.getDBConnection()).thenReturn(null);
        abonnementenDao.addAbonnementToUser(TOKEN, 1, "test", "test");
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForNullConnectionGetSpecificAbonnement() {
        when(jdbcConnectionFactory.getDBConnection()).thenReturn(null);
        abonnementenDao.getSpecificAbonnement(1, TOKEN);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForNullConnectionVerwijderAbonnement() {
        when(jdbcConnectionFactory.getDBConnection()).thenReturn(null);
        abonnementenDao.verwijderAbonnement(1, TOKEN);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForNullConnectionUpgradeAbonnement() {
        when(jdbcConnectionFactory.getDBConnection()).thenReturn(null);
        abonnementenDao.upgradeAbonnement(1, TOKEN, "test");
    }

    @After
    public void cleanDB() throws SQLException {
        dbUtilsTest.clearDB(jdbcConnectionFactory.getDBConnection());
    }
}
