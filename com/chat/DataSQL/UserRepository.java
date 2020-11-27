package com.chat.DataSQL;

import com.chat.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    public User findUserByEmailAndPassword(String email, String password) {
        Connection connection = DataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM mailuserpasswordauth WHERE email = ? AND password = ?"
            );
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("ID"),
                        rs.getString("nickname"),
                        rs.getString("email"),
                        rs.getString("passowrd")
                );
            }

            return null;
        } catch (SQLException throwables) {
            throw new RuntimeException("SWW during user fetch", throwables);
        } finally {
            DataSource.close(connection);
        }
    }
}
