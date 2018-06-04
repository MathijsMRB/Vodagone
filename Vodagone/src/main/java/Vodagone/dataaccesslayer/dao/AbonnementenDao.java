package Vodagone.dataaccesslayer.dao;

import Vodagone.dataaccesslayer.database.JdbcConnectionFactory;
import Vodagone.dataaccesslayer.models.Abonnement;
import Vodagone.dataaccesslayer.models.AbonnementenResponse;
import Vodagone.dataaccesslayer.models.AvailableAbonnementen;
import Vodagone.dataaccesslayer.models.SpecificAbonnementResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Vodagone.dataaccesslayer.database.DBUtils.checkConnection;

@Singleton
public class AbonnementenDao {

    @Inject
    private JdbcConnectionFactory jdbcConnectionFactory;

    private final String SQLAANBIEDER = "SELECT id, aanbieder, dienst " +
            "FROM Abonnement A " +
            "WHERE aanbieder LIKE ? " +
            "AND NOT EXISTS(SELECT 1 " +
            "FROM GebruikersAbonnement GA " +
            "INNER JOIN Gebruiker G ON GA.idGebruiker = G.id " +
            "WHERE GA.idAbonnement = A.id AND token = ?)";
    private final String SQLDIENST = "SELECT id, aanbieder, dienst " +
            "FROM Abonnement A " +
            "WHERE dienst LIKE ? " +
            "AND NOT EXISTS(SELECT 1 " +
            "FROM GebruikersAbonnement GA " +
            "INNER JOIN Gebruiker G ON GA.idGebruiker = G.id " +
            "WHERE GA.idAbonnement = A.id AND token = ?)";
    private final String SQLNOFILTER = "SELECT id, aanbieder, dienst " +
            "FROM Abonnement A " +
            "WHERE NOT EXISTS(SELECT 1 " +
            "FROM GebruikersAbonnement GA " +
            "INNER JOIN Gebruiker G ON GA.idGebruiker = G.id " +
            "WHERE GA.idAbonnement = A.id AND token = ?)";

    public AbonnementenResponse getAbonnementenUser(String token) {
        AbonnementenResponse abonnementenResponse;
        try {
            abonnementenResponse = getAbonnementenUserDB(token);
        } catch (SQLException e) {
            throw new BadRequestException("There was a SQL error at function getAbonnementUser: " + e);
        }
        return abonnementenResponse;
    }

    private AbonnementenResponse getAbonnementenUserDB(String token) throws SQLException {
        AbonnementenResponse abonnementenResponse = new AbonnementenResponse();
        Double prijs = .0;
        try (Connection connection = jdbcConnectionFactory.getDBConnection()){
            checkConnection(connection);
            PreparedStatement statement = connection.prepareStatement("SELECT A.id, aanbieder, dienst, prijs " +
                    "FROM GebruikersAbonnement GA " +
                    "INNER JOIN Gebruiker G ON GA.idGebruiker = G.id " +
                    "INNER JOIN Abonnement A ON GA.idAbonnement = A.id " +
                    "WHERE token = ? " +
                    "AND status <> 'opgezegd'");
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                abonnementenResponse.addAbonnement(new Abonnement(resultSet.getInt("id"), resultSet.getString("aanbieder"), resultSet.getString("dienst")));
                prijs += resultSet.getDouble("prijs");
            }
            abonnementenResponse.setTotalPrice(prijs);
        }
        return abonnementenResponse;
    }

    public void addAbonnementToUser(String token, int id, String aanbieder, String dienst){
        try {
            addAbonnementToUserDB(token, id, aanbieder, dienst);
        } catch (SQLException e) {
            throw new BadRequestException("There was a SQL error at function addAbonnementToUser: " + e);
        }
    }

    private void addAbonnementToUserDB(String token, int id, String aanbieder, String dienst) throws SQLException {
        try (Connection connection = jdbcConnectionFactory.getDBConnection()) {
            checkConnection(connection);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO GebruikersAbonnement (idGebruiker, idAbonnement, prijs, startDatum, verdubbeling, status) " +
                    "SELECT (select id from gebruiker where token = ?), " +
                    "id, prijsPerMaand, format(getdate(), 'yyy-MM-dd'), " +
                    "case when verdubbelingMogelijk = 0 then 'niet-beschikbaar' else 'standaard' end, 'proef' " +
                    "FROM Abonnement " +
                    "WHERE id = ? AND aanbieder = ? AND dienst = ?");
            statement.setString(1, token);
            statement.setInt(2, id);
            statement.setString(3, aanbieder);
            statement.setString(4, dienst);
            statement.executeUpdate();
        }
    }

    public AvailableAbonnementen getAvailableAbonnementen(String token, String filter, String filterField){
        AvailableAbonnementen availableAbonnementen;
        try {
            availableAbonnementen = getAvailableAbonnementenDB(token, filter, filterField);
        } catch (SQLException e) {
            throw new BadRequestException("There was a SQL error at function getAvailableAbonnementen: " + e);
        }
        return availableAbonnementen;
    }

    private AvailableAbonnementen getAvailableAbonnementenDB(String token, String filter, String filterField) throws SQLException {
        AvailableAbonnementen availableAbonnementen = new AvailableAbonnementen();
        String sql = "";
        if("aanbieder".equals(filterField)){
            sql = SQLAANBIEDER;
        }
        else if("dienst".equals(filterField)){
            sql = SQLDIENST;
        }
        else{
            sql = SQLNOFILTER;
        }
        try (Connection connection = jdbcConnectionFactory.getDBConnection()) {
            checkConnection(connection);
            PreparedStatement statement = connection.prepareStatement(sql);
            if("aanbieder".equals(filterField) || "dienst".equals(filterField)){
                statement.setString(1, filter + "%");
                statement.setString(2, token);
            }
            else{
                statement.setString(1, token);
            }

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                availableAbonnementen.addAbonnement(new Abonnement(resultSet.getInt("id"), resultSet.getString("aanbieder"), resultSet.getString("dienst")));
            }
        }
        return availableAbonnementen;
    }

    public SpecificAbonnementResponse getSpecificAbonnement(int id, String token){
        SpecificAbonnementResponse specificAbonnementResponse;
        try {
            specificAbonnementResponse = getSpecificAbonnementDB(id, token);
        } catch (SQLException e) {
            throw new BadRequestException("There was a SQL error at function getSpecificAbonnement: " + e);
        }
        return specificAbonnementResponse;
    }

    private SpecificAbonnementResponse getSpecificAbonnementDB(int id, String token) throws SQLException {
        SpecificAbonnementResponse specificAbonnementResponse = new SpecificAbonnementResponse();
        try (Connection connection = jdbcConnectionFactory.getDBConnection()) {
            checkConnection(connection);
            PreparedStatement statement = connection.prepareStatement("SELECT idAbonnement, aanbieder, dienst, prijs, startDatum, verdubbeling, deelbaar, status " +
                    "FROM GebruikersAbonnement GA " +
                    "INNER JOIN Abonnement A ON ga.idAbonnement = A.id " +
                    "INNER JOIN Gebruiker G ON GA.idGebruiker = G.id " +
                    "WHERE idAbonnement = ? AND token = ?");
            statement.setInt(1, id);
            statement.setString(2, token);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                specificAbonnementResponse.setId(resultSet.getInt("idAbonnement"));
                specificAbonnementResponse.setAanbieder(resultSet.getString("aanbieder"));
                specificAbonnementResponse.setDienst(resultSet.getString("dienst"));
                specificAbonnementResponse.setPrijs(resultSet.getDouble("prijs"));
                specificAbonnementResponse.setStartDatum(resultSet.getString("startDatum"));
                specificAbonnementResponse.setVerdubbeling(resultSet.getString("verdubbeling"));
                specificAbonnementResponse.setDeelbaar(resultSet.getBoolean("deelbaar"));
                specificAbonnementResponse.setStatus(resultSet.getString("status"));
            }
        }
        return specificAbonnementResponse;
    }

    public void verwijderAbonnement(int id, String token){
        try {
            verwijderAbonnementDB(id, token);
        } catch (SQLException e) {
            throw new BadRequestException("There was a SQL error at function verwijderAbonnement: " + e);
        }
    }

    private void verwijderAbonnementDB(int id, String token) throws SQLException {
        try (Connection connection = jdbcConnectionFactory.getDBConnection()) {
            checkConnection(connection);
            PreparedStatement statement = connection.prepareStatement("UPDATE GebruikersAbonnement " +
                    "SET status = 'opgezegd' " +
                    "WHERE idAbonnement = ? AND idGebruiker = (SELECT id FROM Gebruiker WHERE token = ?)");
            statement.setInt(1, id);
            statement.setString(2, token);
            statement.executeUpdate();
        }
    }

    public void upgradeAbonnement(int id, String token, String verdubbeling){
        try {
            upgradeAbonnementDB(id, token, verdubbeling);
        } catch (SQLException e) {
            throw new BadRequestException("There was a SQL error at function upgradeAbonnement: " + e);
        }
    }

    private void upgradeAbonnementDB(int id, String token, String verdubbeling) throws SQLException {
        try (Connection connection = jdbcConnectionFactory.getDBConnection()) {
            checkConnection(connection);
            PreparedStatement statement = connection.prepareStatement("UPDATE GebruikersAbonnement " +
                    "SET verdubbeling = ?, prijs = prijs*1.5 " +
                    "WHERE idAbonnement = ? AND verdubbeling = 'standaard' " +
                    "AND idGebruiker = (SELECT id FROM Gebruiker WHERE token = ?)");
            statement.setString(1, verdubbeling);
            statement.setInt(2, id);
            statement.setString(3, token);
            statement.executeUpdate();
        }
    }
}

