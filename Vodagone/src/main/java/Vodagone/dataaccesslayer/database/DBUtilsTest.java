package Vodagone.dataaccesslayer.database;

import javax.ws.rs.BadRequestException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class DBUtilsTest {

    public void initTabellen(Connection testDBConnection) throws SQLException {
        try (Connection connection = testDBConnection) {
            createGebruiker(connection);
            createGebruikersAbonnement(connection);
            createStatus(connection);
            createVerdubbeling(connection);
            createDienstVanAanbieder(connection);
            createAanbieder(connection);
            createDienst(connection);
            createAbonnement(connection);
        }
    }

    public void insertDummyData(Connection testDBConnection) throws SQLException {
        try (Connection connection = testDBConnection) {
            connection.prepareStatement("insert into Gebruiker\n" +
                    "values (0,'MathijsB','Mathijs','admin','1234-1234-1234','mathijs.bouwmeister@gmail.com')").execute();
            connection.prepareStatement("insert into Aanbieder\n" +
                    "values ('Vodafone'),\n" +
                    "\t   ('Ziggo')").execute();
            connection.prepareStatement("insert into Dienst\n" +
                    "values ('Mobiele telefonie 100'),\n" +
                    "\t   ('Mobiele telefonie 250'),\n" +
                    "\t   ('Glasvezel-internet (download 500 Mbps)'),\n" +
                    "\t   ('Kabel-internet (download 300 Mbps)'),\n" +
                    "\t   ('Eredivisie Live 1,2,3,4, en 5'),\n" +
                    "\t   ('HBO Plus')").execute();
            connection.prepareStatement("insert into DienstVanAanbieder\n" +
                    "values ('Vodafone','Mobiele telefonie 100'),\n" +
                    "\t   ('Vodafone','Mobiele telefonie 250'),\n" +
                    "\t   ('Vodafone','Glasvezel-internet (download 500 Mbps)'),\n" +
                    "\t   ('Ziggo','Kabel-internet (download 300 Mbps)'),\n" +
                    "\t   ('Ziggo','Eredivisie Live 1,2,3,4, en 5'),\n" +
                    "\t   ('Ziggo','HBO Plus')").execute();
            connection.prepareStatement("insert into Verdubbeling\n" +
                    "values ('standaard'),\n" +
                    "\t   ('verdubbeld'),\n" +
                    "\t   ('niet-beschikbaar')").execute();
            connection.prepareStatement("insert into Abonnement\n" +
                    "values (0,'Vodafone','Mobiele telefonie 100',5,25,45,0,0),\n" +
                    "\t   (1,'Vodafone','Mobiele telefonie 250',10,50,90,0,1),\n" +
                    "\t   (2,'Vodafone','Glasvezel-internet (download 500 Mbps)',40,200,360,0,1),\n" +
                    "\t   (3,'Ziggo','Kabel-internet (download 300 Mbps)',30,150,270,0,0),\n" +
                    "\t   (4,'Ziggo','Eredivisie Live 1,2,3,4, en 5',10,50,90,1,0),\n" +
                    "\t   (5,'Ziggo','HBO Plus',15,75,135,1,0)").execute();
            connection.prepareStatement("insert into Status\n" +
                    "values ('opgezegd'),\n" +
                    "\t   ('actief'),\n" +
                    "\t   ('proef')").execute();
            connection.prepareStatement("insert into GebruikersAbonnement\n" +
                    "values (0,5,5,'2017-01-01','standaard','actief')").execute();
            connection.prepareStatement("insert into gebruiker (id, gebruiker, naam, wachtwoord, email)\n" +
                    "values (1,'MeronB','Meron','1234','Meron.Brouwer@han.nl'),\n" +
                    "\t   (2,'DennisB','Dennis','1234','Dennis.Breuker@han.nl'),\n" +
                    "\t   (3,'MichelP','Michel','1234','Michel.Portier@han.nl')");
        }
    }

    public void clearDB(Connection testDBConnection) throws SQLException {
        try {
            try (Connection connection = testDBConnection) {
                if(connection != null) {
                    connection.prepareStatement("DROP TABLE IF EXISTS Gebruiker").execute();
                    connection.prepareStatement("DROP TABLE IF EXISTS GebruikersAbonnement").execute();
                    connection.prepareStatement("DROP TABLE IF EXISTS Status").execute();
                    connection.prepareStatement("DROP TABLE IF EXISTS Verdubbeling").execute();
                    connection.prepareStatement("DROP TABLE IF EXISTS DienstVanAanbieder").execute();
                    connection.prepareStatement("DROP TABLE IF EXISTS Aanbieder").execute();
                    connection.prepareStatement("DROP TABLE IF EXISTS Dienst").execute();
                    connection.prepareStatement("DROP TABLE IF EXISTS Abonnement").execute();
                }
            }
        } catch (SQLException e) {
            throw new BadRequestException("There was a SQL issue" + e);
        }
    }

    private void createGebruiker(Connection connection) throws SQLException {
        connection.prepareStatement("create table Gebruiker(\n" +
                "\tid int not null,\n" +
                "\tgebruiker varchar(255) not null,\n" +
                "\tnaam varchar(255) not null,\n" +
                "\twachtwoord varchar(255) not null,\n" +
                "\ttoken varchar(255) null,\n" +
                "\temail varchar(255) not null)").execute();
    }

    private void createGebruikersAbonnement(Connection connection) throws SQLException {
        connection.prepareStatement("create table GebruikersAbonnement(\n" +
                "\tidGebruiker int not null,\n" +
                "\tidAbonnement int not null,\n" +
                "\tprijs DOUBLE not null,\n" +
                "\tstartDatum date not null,\n" +
                "\tverdubbeling varchar(255) not null,\n" +
                "\tstatus varchar(255) not null)").execute();
    }

    private void createStatus(Connection connection) throws SQLException {
        connection.prepareStatement("create table Status(\n" +
                "\tstatus varchar(255) not null)").execute();
    }

    private void createVerdubbeling(Connection connection) throws SQLException {
        connection.prepareStatement("create table Verdubbeling(\n" +
                "\tverdubbeling varchar(255) not null)").execute();
    }

    private void createDienstVanAanbieder(Connection connection) throws SQLException {
        connection.prepareStatement("create table DienstVanAanbieder(\n" +
                "\taanbieder varchar(255) not null,\n" +
                "\tdienst varchar(255) not null)").execute();
    }

    private void createAanbieder(Connection connection) throws SQLException {
        connection.prepareStatement("create table Aanbieder(\n" +
                "\taanbieder varchar(255) not null)").execute();
    }

    private void createDienst(Connection connection) throws SQLException {
        connection.prepareStatement("create table Dienst(\n" +
                "\tdienst varchar(255) not null)").execute();
    }

    private void createAbonnement(Connection connection) throws SQLException {
        connection.prepareStatement("create table Abonnement(\n" +
                "\tid int not null,\n" +
                "\taanbieder varchar(255) not null,\n" +
                "\tdienst varchar(255) not null,\n" +
                "\tprijsPerMaand DOUBLE not null,\n" +
                "\tprijsPerHalfJaar DOUBLE not null,\n" +
                "\tprijsPerJaar DOUBLE not null,\n" +
                "\tdeelbaar bit not null,\n" +
                "\tverdubbelingMogelijk bit not null)").execute();
    }

    public boolean checkIfShareSucceeded(int idGebruiker, int idAbonnement, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM GebruikersAbonnement WHERE idGebruiker = ? AND idAbonnement = ?");
        statement.setInt(1, idGebruiker);
        statement.setInt(2, idAbonnement);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }
}
