package dataaccesslayer.dao;

import Vodagone.dataaccesslayer.models.LoginResponse;
import Vodagone.service.InitDatabaseUtility;
import Vodagone.dataaccesslayer.dao.LoginDao;
import Vodagone.dataaccesslayer.database.DBUtilsTest;
import Vodagone.dataaccesslayer.database.JdbcConnectionFactory;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginDaoTest {

    private final DBUtilsTest dbUtilsTest = new DBUtilsTest();
    private final String USER = "MathijsB";
    private final String RIGHTPASSWORD = "admin";
    private final String WRONGPASSWORD = "1";
    private final String TOKEN = "1234-1234-1234";

    @Spy
    private JdbcConnectionFactory jdbcConnectionFactory;

    @InjectMocks
    private LoginDao loginDao;

    @Before
    public void initDatabase() throws SQLException {
        InitDatabaseUtility.initDatabase(this, jdbcConnectionFactory, dbUtilsTest);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForNullConnectionCheckValidLogin() {
        when(jdbcConnectionFactory.getDBConnection()).thenReturn(null);
        loginDao.checkValidLogin(USER, RIGHTPASSWORD);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForNullConnectionAddToken() {
        when(jdbcConnectionFactory.getDBConnection()).thenReturn(null);
        loginDao.addToken(TOKEN, USER);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForNullConnectionGetGebruiker() {
        when(jdbcConnectionFactory.getDBConnection()).thenReturn(null);
        loginDao.getGebruiker(USER);
    }

    @Test
    public void shouldReturnTrueForValidLogin() {
        boolean isValid = loginDao.checkValidLogin(USER, RIGHTPASSWORD);
        assertTrue(isValid);
    }

    @Test
    public void shouldReturnFalseForNotValidLogin() {
        boolean isValid = loginDao.checkValidLogin(USER, WRONGPASSWORD);
        assertFalse(isValid);
    }

    @Test
    public void shouldAddToken(){
        loginDao.addToken(TOKEN, USER);
        LoginResponse loginResponse = loginDao.getGebruiker(USER);
        assertEquals(TOKEN, loginResponse.getToken());
    }

    @Test
    public void shouldGetUser(){
        LoginResponse loginExpected = new LoginResponse(USER, TOKEN);
        loginDao.addToken(TOKEN, USER);
        LoginResponse loginResult = loginDao.getGebruiker(USER);
        assertEquals(loginExpected.getToken(), loginResult.getToken());
        assertEquals(loginExpected.getUser(), loginResult.getUser());
    }

    @After
    public void cleanDB() throws SQLException {
        dbUtilsTest.clearDB(jdbcConnectionFactory.getDBConnection());
    }
}
