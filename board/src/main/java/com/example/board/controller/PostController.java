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
    public String findByTitle(Model model,
                              @RequestParam("select") String select,
                              @RequestParam("srchText") String srchText) throws Exception {
        model.addAttribute("select", select);

        if (select.equals("title")) {
            List<Post> posts = PostDAO.postFindByTitle(srchText, 1, 4);
            model.addAttribute("posts", posts);
            return "postMainTitle";
        }
        else if (select.equals("nickname")) {
            List<Post> posts = PostDAO.postFindByNickName(srchText, 1, 4);
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

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register() {
        return "register1";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(Model model,
                           @RequestParam("loginId") String loginId,
                           @RequestParam("password1") String password1,
                           @RequestParam("password2") String password2,
                           @RequestParam("name") String name,
                           @RequestParam("nickname") String nickname,
                           @RequestParam("email") String email) throws Exception {
        String error = "";
        model.addAttribute("loginId", loginId);
        model.addAttribute("password1", password1);
        model.addAttribute("password2", password2);
        model.addAttribute("name", name);
        model.addAttribute("nickname", nickname);
        model.addAttribute("email", email);

        if (loginId == null || loginId.length() == 0) {
            error = "사용자 아이디를 입력하세요";
        }
        else if (password1 == null || password1.length() == 0) {
            error = "비밀번호1를 입력하세요";
        }
        else if (password2 == null || password2.length() == 0) {
            error = "비밀번호2를 입력하세요";
        }
        else if (name == null || name.length() == 0) {
            error = "이름을 입력하세요";
        }
        else if (nickname == null || nickname.length() == 0) {
            error = "닉네임을 입력하세요";
        }
        else if (!password1.equals(password2)) {
            error = "비밀번호 불일치";
        }
        else if (email == null || email.length() == 0) {
            error = "이메일 주소를 입력하세요";
        }
        else {
            Member member = new Member(loginId, password1, name, nickname, email);
            MemberDAO.memberRegister(member);
            return "redirect:/login";
        }
        model.addAttribute("error", error);
        return "register1";
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



}
