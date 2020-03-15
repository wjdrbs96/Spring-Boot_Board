package com.example.board.controller;

import DAO.CommentDAO;
import com.example.board.model.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CommentController {

    @RequestMapping(value = "comment/view", method = RequestMethod.GET)
    public String insertAllCommentView(Model model,
                                 @RequestParam("postId") int postId,
                                 @RequestParam("comment") String comment) throws Exception {

        CommentDAO.commentInsert(comment, postId);
        List<Comment> list = CommentDAO.findAllComment(postId);
        model.addAttribute("list", list);
        return "commentView";
    }

    @RequestMapping(value = "comment/list", method = RequestMethod.GET)
    public String getCommentList(Model model,
                                 @RequestParam("postId") int postId) throws Exception {
        List<Comment> list = CommentDAO.findAllComment(postId);
        model.addAttribute("list", list);
        return "commentView";
    }
}
