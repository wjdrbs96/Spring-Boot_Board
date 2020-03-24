package com.example.board.controller;

import DAO.MemberDAO;
import com.example.board.model.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class MemberController {

    // 로그인 페이지 GET
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "loginForm";
    }

    // 로그인 POST 요청
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, Model model,
                        @RequestParam("userid") String userid,
                        @RequestParam("password") String password) throws Exception {

        int num = MemberDAO.loginCheck(userid, password);
        String errorMsg = null;

        if (num == 0) {
            errorMsg = "아이디와 비밀번호를 다시 입력하세요";
            model.addAttribute("errorMsg", errorMsg);
            return "loginForm";
        }
        else if (num == -1) {

            errorMsg = "아이디가 존재하지 않습니다";
            model.addAttribute("errorMsg", errorMsg);
            return "loginForm";
        }

        session.setAttribute("userid", userid);
        model.addAttribute("userid", userid);
        return "redirect:/post/list";
    }

    @RequestMapping(value = "/find/password", method = RequestMethod.GET)
    public String findPassword() {
        return "findPassword";
    }

    @RequestMapping(value = "/find/password", method = RequestMethod.POST)
    public String findPassword(Model model,
                               @RequestParam("loginId") String loginId,
                               @RequestParam("name") String name) throws Exception {

        String password = MemberDAO.passwordFind(loginId, name);

        if (password == null) {
            String errorMsg = "입력하신 정보가 맞지 않습니다";
            model.addAttribute("errorMsg", errorMsg);
            return "errorPage";
        }

        model.addAttribute("password", password);

        return "findPasswordView";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @RequestMapping(value = "/member/update", method = RequestMethod.GET)
    public String memberUpdate(Model model,
                               @RequestParam("loginId") String loginId) throws SQLException {
        Member member = MemberDAO.findOneMember(loginId);
        model.addAttribute("member", member);
        return "memberUpdate";
    }

    @RequestMapping(value = "member/update", method = RequestMethod.POST)
    public String memberUpdate(@RequestParam("memberId") int memberId,
                               @RequestParam("loginId") String loginId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("nickname") String nickName,
                               @RequestParam("email") String email) throws SQLException {

        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(password);
        member.setName(name);
        member.setNickName(nickName);
        member.setEmail(email);

        MemberDAO.memberUpdate(member, memberId);
        return "redirect:/post/list";
    }

}
