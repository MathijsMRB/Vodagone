package Vodagone.dataaccesslayer.dao;

import Vodagone.dataaccesslayer.database.JdbcConnectionFactory;
import Vodagone.dataaccesslayer.models.LoginResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import java.sql.*;

import static Vodagone.dataaccesslayer.database.DBUtils.checkConnection;

@Singleton
public class LoginDao {

    @Inject
    private JdbcConnectionFactory jdbcConnectionFactory;

    public boolean checkValidLogin(String user, String password) {
        boolean isValidLogin;
        try {
            isValidLogin = checkValidLoginDB(user, password);
        } catch(SQLException e) {
            throw new BadRequestException("There was a SQL error at function checkValidLogin: " + e);
        }
        return isValidLogin;
    }

    private boolean checkValidLoginDB(String user, String password) throws SQLException {
        try (Connection connection = jdbcConnectionFactory.getDBConnection()) {
            checkConnection(connection);
            PreparedStatement statement = connection.prepareStatement("SELECT gebruiker FROM Gebruiker WHERE gebruiker = ? AND wachtwoord = ?");
            statement.setString(1, user);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next() && resultSet.getString("gebruiker").equals(user);
        }
    }

    public void addToken(String token, String user) {
        try {
            addTokenDB(token, user);
        } catch(SQLException e) {
            throw new BadRequestException("There was a SQL error at function addToken: " + e);
        }
    }

    private void addTokenDB(String token, String user) throws SQLException {
        try (Connection connection = jdbcConnectionFactory.getDBConnection()){
            checkConnection(connection);
            PreparedStatement statement = connection.prepareStatement("UPDATE gebruiker SET token = ? WHERE gebruiker = ? ");
            statement.setString(1, token);
            statement.setString(2, user);
            statement.executeUpdate();
        }
    }

    public LoginResponse getGebruiker(String user){
        LoginResponse loginResponse;
        try {
            loginResponse = getGebruikerDB(user);
        } catch(SQLException e){
            throw new BadRequestException("There was a SQL error at function getGebruiker: " + e);
        }
        return loginResponse;
    }

    private LoginResponse getGebruikerDB(String user) throws SQLException {
        LoginResponse loginResponse = new LoginResponse();
        try (Connection connection = jdbcConnectionFactory.getDBConnection()){
            checkConnection(connection);
            PreparedStatement statement = connection.prepareStatement("SELECT gebruiker, token FROM Gebruiker WHERE gebruiker = ?");
            statement.setString(1, user);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                loginResponse.setToken(resultSet.getString("token"));
                loginResponse.setUser(resultSet.getString("gebruiker"));
            }
        }
        return loginResponse;
    }
}
