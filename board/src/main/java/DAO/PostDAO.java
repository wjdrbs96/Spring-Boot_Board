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
        String sql = "select p.*, m.nickname " +
                     "from post p join member m " +
                     "on p.memberId = m.memberId " +
                     "order by p.postId desc " +
                     "limit ?, ?";

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
                    post.setCreateDateTime(resultSet.getString("createDateTime"));
                    post.setNickName(resultSet.getString("nickname"));
                    list.add(post);
                }
                return list;
            }

        }
    }

    public static int postAllCount() throws Exception {
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

    public static List<Post> postFindByTitle(String title, int currentPage, int pageSize) throws Exception {
        String sql = "select p.*, m.nickname " +
                     "from post p join member m " +
                     "on p.memberId = m.memberId " +
                     "where title like ? or title like ? or title like ? " +
                     "limit ?, ?";

        if (title == "") title = null;         // 코드 실행하다 보면 name=""으로 넘어오는 경우가 있어서 만듬
        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title + "%");
            statement.setString(2, "%" + title);
            statement.setString(3, "%" + title + "%");
            statement.setInt(4, (currentPage - 1) * pageSize);
            statement.setInt(5, pageSize);
            List<Post> list = new ArrayList<Post>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Post post = new Post();
                    post.setPostId(resultSet.getLong("postId"));
                    post.setNickName(resultSet.getString("nickname"));
                    post.setMemberId(resultSet.getLong("memberId"));
                    post.setTitle(resultSet.getString("title"));
                    post.setContent(resultSet.getString("content"));
                    post.setCount(resultSet.getInt("count"));
                    post.setCreateDateTime(resultSet.getString("createDateTime"));

                    list.add(post);
                }
                return list;
            }
        }
    }

    public static List<Post> postFindByNickName(String nickName, int currentPage, int pageSize) throws Exception {
        String sql = "select p.*, m.nickname " +
                     "from post p join member m " +
                     "on p.memberId = m.memberId " +
                     "where m.nickname like ? " +
                     "limit ?, ?";

        if (nickName == "") nickName = null;         // 코드 실행하다 보면 name=""으로 넘어오는 경우가 있어서 만듬
        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + nickName + "%");
            statement.setInt(2, (currentPage - 1) * pageSize);
            statement.setInt(3, pageSize);
            List<Post> list = new ArrayList<Post>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Post post = new Post();
                    post.setPostId(resultSet.getLong("postId"));
                    post.setNickName(resultSet.getString("nickname"));
                    post.setTitle(resultSet.getString("title"));
                    post.setContent(resultSet.getString("content"));
                    post.setCount(resultSet.getInt("count"));
                    post.setCreateDateTime(resultSet.getString("createDateTime"));

                    list.add(post);
                }
                return list;
            }
        }
    }


    public static Post findOnePost(int postId) throws Exception{
        String sql = "select * " +
                     "from post " +
                     "where postId = ?";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, postId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Post post = new Post();
                    post.setPostId(resultSet.getLong("postId"));
                    post.setMemberId(resultSet.getLong("memberId"));
                    post.setTitle(resultSet.getString("title"));
                    post.setContent(resultSet.getString("content"));
                    post.setCount(resultSet.getInt("count"));
                    return post;
                }
            }
        }
        return null;
    }

    public static Post findByPostId(int id) throws Exception {
        String sql = "select p.*, m.name " +
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
                    post.setMemberId(resultSet.getLong("memberId"));
                    post.setContent(resultSet.getString("content"));
                    post.setCount(resultSet.getInt("count"));
                    post.setName(resultSet.getString("name"));
                    post.setCreateDateTime(resultSet.getString("createDateTime"));
                    return post;
                }
            }
        }
        return null;
    }

    // 게시글 수정
    public static void postUpdate(Post post) throws Exception {
        String sql = "update post " +
                     "set title = ?, content = ?, count = ? " +
                     "where postId = ?";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getContent());
            statement.setLong(3, post.getCount());
            statement.setLong(4, post.getPostId());
            statement.executeUpdate();
        }
    }

    // 글쓰기
    public static void insertPost(Post post) throws Exception {
        String sql = "insert post(memberId, title, content, count, createDateTime) " +
                     "values(?, ?, ?, ?, ?)";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, post.getMemberId());
            statement.setString(2, post.getTitle());
            statement.setString(3, post.getContent());
            statement.setInt(4, 1);
            statement.setString(5, post.getCreateDateTime());
            statement.executeUpdate();
        }
    }

    public static void deletePost(long postId) throws SQLException {
        String sql = "delete from post " +
                     "where postId = ?";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, postId);
            statement.executeUpdate();
        }
    }
}
