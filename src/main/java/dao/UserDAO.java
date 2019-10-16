package dao;

import bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection connection;

    public UserDAO () {
        connection = SQLite.getConnection();
    }

    public boolean insertUser (User user) {

        String insert = "INSERT INTO " + User.TABLE_NAME + " ( " +
                User.CPF    + "," +
                User.NOME   + "," +
                User.IDADE  + "," +
                User.SEXO   + "," +
                User.ALTURA + "," +
                User.PESO   + "," +
                User.EMAIL  + ")" +
                " VALUES (?, ?, ?, ?, ?, ?, ?); ";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, user.getCpf());
            preparedStatement.setString(2, user.getNome());
            preparedStatement.setString(3, user.getIdade());
            preparedStatement.setString(4, user.getSexo());
            preparedStatement.setString(5, user.getAltura());
            preparedStatement.setString(6, user.getPeso());
            preparedStatement.setString(7, user.getEmail());
            preparedStatement.execute();
            return true;

        } catch (Exception e) {
            System.err.println("Erro ao inserir registro no banco de dados.");
            e.printStackTrace();
            return false;
        }

    }

    public User getUser (String cpf) {

        User user = null;

        String select = "SELECT * FROM " + User.TABLE_NAME + " WHERE " + User.CPF + " = '" + cpf + "'";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                user = new User();

                user.setCpf(resultSet.getString(User.CPF));
                user.setNome(resultSet.getString(User.NOME));
                user.setIdade(resultSet.getString(User.IDADE));
                user.setSexo(resultSet.getString(User.SEXO));
                user.setAltura(resultSet.getString(User.ALTURA));
                user.setPeso(resultSet.getString(User.PESO));
                user.setEmail(resultSet.getString(User.EMAIL));
            }

            return user;

        } catch (Exception e) {
            System.err.println("Erro ao capturar registro no banco de dados.");
            e.printStackTrace();
            return null;
        }

    }

    public List<User> getUsers () {

        User user;
        List<User> users;

        String select = "SELECT * FROM " + User.TABLE_NAME + ";";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();
            users = new ArrayList<>();

            while (resultSet.next()){
                user = new User();

                user.setCpf(resultSet.getString(User.CPF));
                user.setNome(resultSet.getString(User.NOME));
                user.setIdade(resultSet.getString(User.IDADE));
                user.setSexo(resultSet.getString(User.SEXO));
                user.setAltura(resultSet.getString(User.ALTURA));
                user.setPeso(resultSet.getString(User.PESO));
                user.setEmail(resultSet.getString(User.EMAIL));

                users.add(user);
            }

            return users;

        } catch (Exception e) {
            System.err.println("Erro ao capturar registro no banco de dados.");
            e.printStackTrace();
            return null;
        }

    }

    public boolean updateUser (User user) {

        String insert = "UPDATE " + User.TABLE_NAME + " SET " +
                User.NOME   + " = " + " ?," +
                User.IDADE  + " = " + " ?," +
                User.SEXO   + " = " + " ?," +
                User.ALTURA + " = " + " ?," +
                User.PESO   + " = " + " ?," +
                User.EMAIL  + " = " + " ? " +
                "WHERE " + User.CPF + " = " + " ?;";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getIdade());
            preparedStatement.setString(3, user.getSexo());
            preparedStatement.setString(4, user.getAltura());
            preparedStatement.setString(5, user.getPeso());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getCpf());
            int retorno = preparedStatement.executeUpdate();

            return retorno > 0;

        } catch (Exception e) {
            System.err.println("Erro ao atualizar registro no banco de dados.");
            e.printStackTrace();
            return false;
        }

    }

    public boolean deleteUser (String cpf) {

        String delete = "DELETE FROM " + User.TABLE_NAME + " WHERE " + User.CPF + " = '" + cpf + "' ;" ;

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            int retorno = preparedStatement.executeUpdate();

            return retorno > 0;

        } catch (Exception e) {
            System.err.println("Erro ao excluir registro no banco de dados.");
            e.printStackTrace();
            return false;
        }

    }

}
