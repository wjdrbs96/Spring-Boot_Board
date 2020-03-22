package DAO;

import com.example.board.config.DB;
import com.example.board.model.Member;
import com.example.board.model.Post;

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

    public static Member findPostByLoginId(String loginId) throws Exception {
        String sql = "select name, memberId " +
                "from member " +
                "where loginId = ?";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, loginId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Member member = new Member();
                    member.setName(resultSet.getString("name"));
                    member.setMemberId(resultSet.getLong("memberId"));
                    return member;
                }
            }
        }
        return null;
    }

    public static void memberRegister(Member member) throws Exception {
        String sql = "insert member(loginId, password, name, nickname, email) " +
                     "values(?, ?, ?, ?, ?)";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, member.getLoginId());
            statement.setString(2, member.getPassword());
            statement.setString(3, member.getName());

            statement.setString(4, member.getNickName());
            statement.setString(5, member.getEmail());
            statement.executeUpdate();
        }
    }



}
