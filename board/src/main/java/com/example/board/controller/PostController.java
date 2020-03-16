package com.example.board.controller;

import DAO.CommentDAO;
import DAO.PostDAO;
import com.example.board.model.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostController {

    // 게시글 전부 찾기
    @RequestMapping(value = "post/list", method = RequestMethod.GET)
    public String getAllPost(Model model,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) throws Exception {

        // 페이지네이션 포함
        int totalPostCount = PostDAO.postAllCount();               // 전체 게시글 수
        int totalCount = totalPostCount / pageSize + 1;            // 총 페이지 수
        List<Post> postList = PostDAO.findAll(page, pageSize);

        model.addAttribute("posts", postList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalCount);
        return "postMain";
    }

    // 게시글 검색
    @RequestMapping(value = "post/list", method = RequestMethod.POST)
    public String findByTitle(Model model, @RequestParam("srchText") String srchText) throws Exception {
        List<Post> posts = PostDAO.postFindByTitle(srchText, 1, 4);
        model.addAttribute("posts", posts);
        return "postMain";
    }

    // 게시글 쓰기
    @RequestMapping(value = "post/write", method = RequestMethod.GET)
    public String PostWrite(Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userid");
        Post post = PostDAO.findPostByLoginId(userId);
        model.addAttribute("post", post);
        return "writePost";
    }

    @RequestMapping(value = "post/write", method = RequestMethod.POST)
    public String PostWrite(@RequestParam("title") String title,
                            @RequestParam("content") String content,
                            @RequestParam("number") int memberId) throws Exception {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setMemberId(memberId);
        post.setCount(1);                // 처음 게시글을 썼기 때문에 조회수 1로 지정
        PostDAO.insertPost(post);
        return "redirect:/post/list";
    }

    // 게시글 id 찾기
    @RequestMapping(value = "post/View", method = RequestMethod.GET)
    public String PostView(Model model, @RequestParam("postId") int postId) throws Exception {
        Post post = PostDAO.findByPostId(postId);
        model.addAttribute("posts", post);
        return "postView";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String postUpdate(@RequestParam("postId") int postId,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content) throws Exception {
        Post post = PostDAO.findByPostId(postId);
        post.setTitle(title);
        post.setContent(content);

        PostDAO.postUpdate(post);
        return "redirect:/post/list";
    }

    @RequestMapping(value = "post/delete", method = RequestMethod.GET)
    public String postDelete(@RequestParam("postId") int postId) throws Exception {
        PostDAO.deletePost(postId);
        return "redirect:/post/comment/delete?postId=" + postId;
    }

    @RequestMapping(value = "post/comment/delete", method = RequestMethod.GET)
    public String postCommentDelete(@RequestParam("postId") int postId) throws Exception {
        CommentDAO.postCommentDelete(postId);
        return "redirect:/post/list";
    }


}
