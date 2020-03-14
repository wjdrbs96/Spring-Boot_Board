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

    // 게시글 전부 찾기
    @RequestMapping(value = "post/list", method = RequestMethod.GET)
    public String getAllPost(Model model,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "pageSize", defaultValue = "1") int pageSize) throws Exception {

        int totalPostCount = PostDAO.PostAllcount();               // 전체 게시글 수
        int totalCount = totalPostCount / pageSize + 1;            // 총 페이지 수
        List<Post> postList = PostDAO.findAll(1, 3);
        model.addAttribute("posts", postList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalCount);
        return "postMain";
    }

    // 게시글 검색
    @RequestMapping(value = "post/list", method = RequestMethod.POST)
    public String findBytitle(Model model, @RequestParam("srchText") String srchText) throws Exception {
        List<Post> posts = PostDAO.PostfindBytitle(srchText, 1, 4);
        model.addAttribute("posts", posts);
        return "postMain";
    }

    // 게시글 쓰기
    @RequestMapping(value = "post/write", method = RequestMethod.GET)
    public String PostWrite() {
        return "writePost";
    }

    @RequestMapping(value = "post/write", method = RequestMethod.POST)
    public String PostWrite(@RequestParam("title") String title,
                            @RequestParam("content") String content,
                            @RequestParam("name") String name) throws Exception {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setName(name);
        PostDAO.insertPost(post);
        return "postMain";
    }

    // 게시글 id 찾기
    @RequestMapping(value = "post/View", method = RequestMethod.GET)
    public String PostView(Model model, @RequestParam("id") String ID) throws Exception {
        Post post = PostDAO.findByPostId(Integer.parseInt(ID));
        model.addAttribute("posts", post);
        return "postView";
    }

    @RequestMapping(value = "post/View", method = RequestMethod.POST)
    public String PostView(@RequestParam("id") String id,
                           @RequestParam("title") String title,
                           @RequestParam("content") String content) throws Exception {

        Post post = new Post();
        post.setPostId(Integer.parseInt(id));
        post.setTitle(title);
        post.setContent(content);
        PostDAO.PostUpdate(post);

        return "postMain";

    }

    /*
    @RequestMapping(value = "post/delete", method = RequestMethod.GET)
    public String PostDelete() {

    }
    */

}
