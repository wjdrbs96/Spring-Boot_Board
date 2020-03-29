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
            if (MemberDAO.isSameCheckLoginId(loginId) == 0) {
                Member member = new Member(loginId, password1, name, nickname, email);
                MemberDAO.memberRegister(member);
                return "redirect:/login";
            }

            error = "아이디가 중복됩니다";
        }
        model.addAttribute("error", error);
        return "register1";
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
