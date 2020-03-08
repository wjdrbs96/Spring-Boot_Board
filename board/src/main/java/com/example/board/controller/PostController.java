package com.example.board.controller;

import DAO.PostDAO;
import com.example.board.model.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {

    @RequestMapping(value = "post/list", method = RequestMethod.GET)
    public String getAllPost(Model model,
                             @RequestParam("page") int page,
                             @RequestParam("pageSize") int pageSize) throws Exception {
        List<Post> postList = PostDAO.findAll(page, pageSize);
        model.addAttribute("posts", postList);
        return "postMain";
    }
}
