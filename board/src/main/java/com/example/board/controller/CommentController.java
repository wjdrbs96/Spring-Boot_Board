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
    public String AllCommentView(Model model, @RequestParam("PID") String PID,
                                             @RequestParam("comment") String comment) throws Exception {

        int ID = Integer.parseInt(PID);
        CommentDAO.CommentInsert(comment, ID);
        List<Comment> list = CommentDAO.findComment(ID);
        model.addAttribute("list", list);
        return "commentView";
    }
}
