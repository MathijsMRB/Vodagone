package service;

import Vodagone.service.InitDatabaseUtility;
import Vodagone.dataaccesslayer.dao.LoginDao;
import Vodagone.dataaccesslayer.database.DBUtilsTest;
import Vodagone.dataaccesslayer.database.JdbcConnectionFactory;
import Vodagone.dataaccesslayer.models.LoginResponse;
import Vodagone.service.impl.LoginServiceImpl;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.BadRequestException;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginServiceTest {

    private final DBUtilsTest dbUtilsTest = new DBUtilsTest();
    private final String USER = "MathijsB";
    private final String PASSWORD = "admin";
    private final String TOKEN = "1234-1234-1234";
    private final String EMPTY_STRING = "";

    @Mock
    private LoginDao loginDao;
    @InjectMocks
    private LoginServiceImpl loginService;
    @Spy
    private JdbcConnectionFactory jdbcConnectionFactory;

    @Before
    public void initDatabase() throws SQLException {
        InitDatabaseUtility.initDatabase(this, jdbcConnectionFactory, dbUtilsTest);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForNullUser(){
        loginService.loginUser(null, PASSWORD);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForNullPassword(){
        loginService.loginUser(USER, null);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForEmptyUser(){
        loginService.loginUser(EMPTY_STRING, PASSWORD);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForEmptyPassword(){
        loginService.loginUser(USER, EMPTY_STRING);
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForNotExistingUser(){
        loginService.loginUser("testUser", "50");
    }

    @Test (expected = BadRequestException.class)
    public void shouldThrowForIncorrectPassword(){
        loginService.loginUser("MathijsB", "0");
    }

    @Test
    public void shouldLoginUser(){
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUser(USER);
        loginResponse.setToken(TOKEN);

        when(loginDao.checkValidLogin(USER, PASSWORD)).thenReturn(true);
        when(loginDao.getGebruiker(USER)).thenReturn(loginResponse);

        Assert.assertNotNull(loginService.loginUser(USER, PASSWORD));
    }

    @After
    public void cleanDB() throws SQLException {
        dbUtilsTest.clearDB(jdbcConnectionFactory.getDBConnection());
    }
}
