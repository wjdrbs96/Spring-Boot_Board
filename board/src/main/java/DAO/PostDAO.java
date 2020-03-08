package DAO;

import com.example.board.config.DB;
import com.example.board.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public static List<Post> findAll(int page, int pageSize) throws Exception {
        String sql = "select * from post " +
                     "Limit ?, ?";


        // 코드 실행하다 보면 name=""으로 넘어오는 경우가 있어서 만듬
        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (page - 1) * pageSize);
            statement.setInt(2, pageSize);
            List<Post> list = new ArrayList<Post>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Post post = new Post();
                    post.setPostId(resultSet.getLong("postId"));
                    post.setMemberId(resultSet.getLong("memberId"));
                    post.setTitle(resultSet.getString("title"));
                    post.setContent(resultSet.getString("content"));
                    post.setCount(resultSet.getInt("count"));
                    post.setCreateDateTime(resultSet.getDate("createDateTime"));

                    list.add(post);
                }

                return list;
            }

        }
    }
}
