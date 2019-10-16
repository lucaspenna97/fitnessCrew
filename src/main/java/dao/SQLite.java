package dao;

import bean.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SQLite {

    public static Connection connection;

    private static String CREATE_TABLE_USER =
            "CREATE TABLE IF NOT EXISTS " + User.TABLE_NAME + " (" +
                    User.CPF    + " PRIMARY KEY," +
                    User.NOME   + "," +
                    User.IDADE  + "," +
                    User.SEXO   + "," +
                    User.ALTURA + "," +
                    User.PESO   + "," +
                    User.EMAIL  + ");";

    public static Connection getConnection () {

        if (connection != null) return connection;

        try{

            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:user.db";
            connection = DriverManager.getConnection(url);

            if (connection != null){
                Statement statement = connection.createStatement();
                statement.execute(CREATE_TABLE_USER);
            }

            return connection;

        } catch (Exception e) {
            System.err.println("Erro ao conectar com banco de dados.");
            e.printStackTrace();
        }

        return null;
    }

}
