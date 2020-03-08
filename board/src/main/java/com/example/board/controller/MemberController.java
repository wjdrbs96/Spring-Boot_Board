package com.example.board.controller;

import DAO.MemberDAO;
import com.example.board.model.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "loginForm";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpSession session,
                        Model model,
                        @RequestParam("userid") String userid,
                        @RequestParam("password") String password) throws Exception {
        //TODO 로그인 성공시 postMain

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
        return "postMain";
        //TODO 로그인 실패시 loginForm
    }
}
