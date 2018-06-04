package Vodagone.dataaccesslayer.dao;


import Vodagone.dataaccesslayer.database.JdbcConnectionFactory;
import Vodagone.dataaccesslayer.models.Gebruiker;
import Vodagone.dataaccesslayer.models.Gebruikers;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Vodagone.dataaccesslayer.database.DBUtils.checkConnection;

@Singleton
public class AbonneesDao {

    @Inject
    private JdbcConnectionFactory jdbcConnectionFactory;

    public void shareAbonnement(int idGebruiker, int idAbonnement){
        try {
            shareAbonnementDB(idGebruiker, idAbonnement);
        } catch (SQLException e) {
            throw new BadRequestException("There was a SQL error at function shareAbonnement: " + e);
        }
    }

    private void shareAbonnementDB(int idGebruiker, int idAbonnement) throws SQLException {
        try (Connection connection = jdbcConnectionFactory.getDBConnection()) {
            checkConnection(connection);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO GebruikersAbonnement (idGebruiker, idAbonnement, prijs, startDatum, verdubbeling, status) " +
                    "SELECT ?, id, prijsPerMaand, getdate(), case when verdubbelingMogelijk = 0 then 'niet-beschikbaar' else 'standaard' end, 'actief' " +
                    "FROM Abonnement " +
                    "WHERE id = ?");
            statement.setInt(1, idGebruiker);
            statement.setInt(2, idAbonnement);
            statement.executeUpdate();
        }
    }

    public Gebruikers getAllAbonnees(String token){
        Gebruikers gebruikers;
        try {
            gebruikers = getAbonneesFromDatabase(token);
        } catch (SQLException e) {
            throw new BadRequestException("There was a SQL error at function getAllAbonnees: " + e);
        }
        return gebruikers;
    }

    private Gebruikers getAbonneesFromDatabase(String token) throws SQLException {
        Gebruikers gebruikers = new Gebruikers();
        try (Connection connection = jdbcConnectionFactory.getDBConnection()) {
            checkConnection(connection);
            PreparedStatement statement = connection.prepareStatement("SELECT id, naam, email " +
                    "FROM Gebruiker " +
                    "WHERE token <> ? OR token is null");
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                gebruikers.addGebruiker(new Gebruiker(resultSet.getInt("id"), resultSet.getString("naam"), resultSet.getString("email")));
            }
        }
        return gebruikers;
    }
}