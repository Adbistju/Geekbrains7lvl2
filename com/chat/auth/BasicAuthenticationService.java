package com.chat.auth;

import com.chat.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.chat.DataSQL.DataSource.getConnection;

public class BasicAuthenticationService implements AuthenticationService {
    static int idCu=4;
    private static List<User> users;
    /*=List.of(
            new User(1,"n1", "1", "11"),
            new User(2,"n2", "2", "22"),
            new User(3,"n3", "3", "33")
    );*/


    @Override
    public Optional<User> doAuth(String email, String password) {
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM mailuserpasswordauth WHERE email = ? AND password = ?"
            );
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                System.out.println("SUCSES@!");

                User userB= new User(
                        rs.getInt("id"),
                        rs.getString("nickname"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                //users.add(userB);
                return Optional.of(userB);
            }
            return Optional.empty();
        } catch (SQLException throwables) {
            throw new RuntimeException("SWW during user fetch", throwables);
        }
    }

    @Override
    public int doRegist(String email, String password, String nickName) {
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO mailuserpasswordauth (`nickname`, `email`, `password`) VALUES ('"+email+"', '"+password+"', '"+nickName+"')"
            );

            int val = statement.executeUpdate();
            return val;

        } catch (SQLException throwables) {
            throw new RuntimeException("SWW during user fetch", throwables);
        }
    }
    @Override
    public int doChangeNick(String nickName, String pas) {
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE mailuserpasswordauth SET nickname = '"+nickName+"'WHERE password ="+pas
            );
            int val = statement.executeUpdate();
            return val;
        } catch (SQLException throwables) {
            throw new RuntimeException("SWW during user fetch", throwables);
        }
    }
}
