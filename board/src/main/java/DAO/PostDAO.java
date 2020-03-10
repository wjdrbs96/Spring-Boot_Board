package DAO;

import com.example.board.config.DB;
import com.example.board.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public static int PostAllcount() throws Exception {

        String sql = "select count(postId) " +
                     "from post ";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             try (ResultSet resultSet = statement.executeQuery()) {
                 if (resultSet.next())
                     return resultSet.getInt(1);
             }
        }

        return 0;
    }

    public static List<Post> PostfindBytitle(String name, int currentPage, int pageSize) throws Exception {
        String sql = "select * " +
                     "from post " +
                     "where title Like ? or title Like ? or title Like ? " +
                     "Limit ?, ?";

        if (name == "") name = null;         // 코드 실행하다 보면 name=""으로 넘어오는 경우가 있어서 만듬
        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name + "%");
            statement.setString(2, "%" + name);
            statement.setString(3, "%" + name + "%");
            statement.setInt(4, (currentPage - 1) * pageSize);
            statement.setInt(5, pageSize);
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

    public static Post findByPostId(int id) throws Exception {
        String sql = "select p.postId, p.title, p.content, p.count, m.name, p.createDateTime " +
                     "from member m join post p on m.memberId = p.memberId " +
                     "where p.postId = ?";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Post post = new Post();
                    post.setPostId(resultSet.getLong("postId"));
                    post.setTitle(resultSet.getString("title"));
                    post.setContent(resultSet.getString("content"));
                    post.setCount(resultSet.getInt("count"));
                    post.setName(resultSet.getString("name"));
                    post.setCreateDateTime(resultSet.getDate("createDateTime"));
                    return post;

                }


            }

        }

        return null;

    }

    public static void PostUpdate(Post post) throws Exception {

        String sql = "Update post " +
                     "set title = ?, content = ? " +
                     "where postId = ?";


        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getContent());
            statement.setLong(3, post.getPostId());
            statement.executeUpdate();
        }


    }
}
