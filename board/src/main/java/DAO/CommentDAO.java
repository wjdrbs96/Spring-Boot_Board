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

    public static void CommentInsert(String comment, int postID) throws Exception {
        Post post = PostDAO.findByPostId(postID);

        String sql = "insert into comment(postId, memberId, content) " +
                     "values(?,?,?)";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, post.getPostId());
            statement.setLong(2, post.getMemberId());
            statement.setString(3, comment);

            statement.executeUpdate();
        }


    }

    public static List<Comment> findComment(int ID) throws Exception {
        String sql = "select c.commentid, c.content " +
                     "from comment c " +
                     "where commentid = ?";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ID);
            List<Comment> list = new LinkedList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Comment comment = new Comment();
                    comment.setCommentId(resultSet.getInt("commentid"));
                    comment.setContent(resultSet.getString("content"));
                    list.add(comment);
                    return list;
                }


            }

        }

        return null;
    }

}
