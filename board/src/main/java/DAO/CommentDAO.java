package DAO;

import com.example.board.config.DB;
import com.example.board.model.Comment;
import com.example.board.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class CommentDAO {

    public static void commentInsert(Comment comment, int postID) throws Exception {
        Post post = PostDAO.findOnePost(postID);

        String sql = "insert into comment(postId, memberId, content, createDateTime) " +
                     "values(?, ?, ?, ?)";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, post.getPostId());
            statement.setLong(2, post.getMemberId());
            statement.setString(3, comment.getContent());
            statement.setString(4, comment.getCreateDateTime());
            statement.executeUpdate();
        }
    }

    public static List<Comment> findAllComment(long postId) throws Exception {
        String sql = "select * " +
                     "from comment " +
                     "where postId = ?";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, postId);
            List<Comment> list = new LinkedList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Comment comment = new Comment();
                    comment.setCommentId(resultSet.getInt("commentid"));
                    comment.setPostId(resultSet.getLong("postId"));
                    comment.setMemberId(resultSet.getLong("memberId"));
                    comment.setContent(resultSet.getString("content"));
                    comment.setCreateDateTime(resultSet.getString("createDateTime"));
                    list.add(comment);
                }
                return list;
            }
        }
    }

    public static void postCommentAllDelete(int postId) throws Exception {
        String sql = "delete from comment " +
                     "where postId = ?";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             statement.setLong(1, postId);
             statement.executeUpdate();
        }
    }

    public static void CommentDelete(int commentId) throws Exception {
        String sql = "delete from comment " +
                     "where commentId = ?";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             statement.setLong(1, commentId);
             statement.executeUpdate();
        }
    }

    public static Comment findOneComment(int commentId) throws Exception {
        String sql = "select * " +
                     "from comment " +
                     "where commentId = ?";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             statement.setInt(1, commentId);
             try (ResultSet resultSet = statement.executeQuery()) {
                 while (resultSet.next()) {
                    Comment comment = new Comment();
                    comment.setCommentId(resultSet.getLong("commentId"));
                    comment.setPostId(resultSet.getLong("postId"));
                    comment.setMemberId(resultSet.getLong("memberId"));
                    comment.setContent(resultSet.getString("content"));
                    return comment;
                }
            }
        }
        return null;
    }

}
