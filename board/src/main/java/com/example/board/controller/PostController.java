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
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "pageSize", defaultValue = "1") int pageSize) throws Exception {

        int totalPostCount = PostDAO.PostAllcount();               // 전체 게시글 수
        int totalCount = totalPostCount / pageSize + 1;            // 총 페이지 수
        List<Post> postList = PostDAO.findAll(page, pageSize);
        model.addAttribute("posts", postList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalCount);
        return "postMain";
    }

    @RequestMapping(value = "post/list", method = RequestMethod.POST)
    public String findBytitle(Model model, @RequestParam("srchText") String srchText) throws Exception {
        List<Post> posts = PostDAO.findBytitle(srchText, 1, 4);
        model.addAttribute("posts", posts);
        return "postMain";
    }

}
