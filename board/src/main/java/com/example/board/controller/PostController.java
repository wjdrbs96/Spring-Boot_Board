package com.example.board.controller;

import DAO.CommentDAO;
import DAO.MemberDAO;
import DAO.PostDAO;
import com.example.board.model.Member;
import com.example.board.model.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class PostController {

    // 게시글 전부 찾기
    @RequestMapping(value = "post/list", method = RequestMethod.GET)
    public String getAllPost(Model model,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "pageSize", defaultValue = "7") int pageSize) throws Exception {

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
    public String findByTitle(Model model,
                              @RequestParam("select") String select,
                              @RequestParam("srchText") String srchText) throws Exception {
        model.addAttribute("select", select);

        if (select.equals("title")) {
            List<Post> posts = PostDAO.postFindByTitle(srchText, 1, 7);
            model.addAttribute("posts", posts);
            return "postMainTitle";
        }
        else if (select.equals("nickname")) {
            List<Post> posts = PostDAO.postFindByNickName(srchText, 1, 7);
            model.addAttribute("posts", posts);
            return "postMainNickName";
        }

        return "postMain";
    }

    // 게시글 쓰기
    @RequestMapping(value = "post/write", method = RequestMethod.GET)
    public String PostWrite(Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userid");
        Member member = MemberDAO.findPostByLoginId(userId);
        model.addAttribute("member", member);
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("time", sd.format(date));
        return "writePost";
    }

    @RequestMapping(value = "post/write", method = RequestMethod.POST)
    public String PostWrite(@RequestParam("title") String title,
                            @RequestParam("content") String content,
                            @RequestParam("nickname") String nickName,
                            @RequestParam("memberId") int memberId) throws Exception {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setNickName(nickName);
        post.setMemberId(memberId);
        post.setCount(1);                // 처음 게시글을 썼기 때문에 조회수 1로 지정

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        post.setCreateDateTime(sdf.format(new Date()));
        PostDAO.insertPost(post);
        return "redirect:/post/list";
    }

    @RequestMapping(value = "post/update", method = RequestMethod.POST)
    public String postUpdateRedirect(Model model,
                                     @RequestParam("postId") int postId,
                                     @RequestParam("title") String title,
                                     @RequestParam("content") String content) throws Exception {
        Post post = PostDAO.findByPostId(postId);
        post.setTitle(title);
        post.setContent(content);
        post.setCount(post.getCount());
        model.addAttribute("posts", post);
        return "postUpdate";
    }

    // 게시글 id 찾기
    @RequestMapping(value = "post/View", method = RequestMethod.GET)
    public String PostView(Model model,
                           @RequestParam("postId") int postId) throws Exception {
        Post post = PostDAO.findByPostId(postId);

        post.setCount(post.getCount() + 1);
        PostDAO.postUpdate(post);
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
        post.setCount(post.getCount());

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
        CommentDAO.postCommentAllDelete(postId);
        return "redirect:/post/list";
    }

    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    public String notice() {
        return "notice";
    }
}
