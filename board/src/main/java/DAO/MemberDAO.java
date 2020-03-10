package DAO;

import com.example.board.config.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO {
    //TODO loginCheck

    public static int loginCheck(String id, String pwd) throws Exception {
        String sql = "select password from member " +
                     "where loginId = ?";

        String dbpw = "";
        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    dbpw = resultSet.getString("password");
                    if (dbpw.equals(pwd)) {
                        return 1;              // 아이디와 비번이 같을 때
                    }
                    else {
                        return 0;              // 아이디와 비번이 다를 때
                    }
                }
                else {
                    return -1;                // 아이디가 존재하지 않을 때
                }
            }
        }

    }




}
