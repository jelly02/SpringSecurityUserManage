package com.manage.foruser.controller;

import com.manage.foruser.service.UserService;
import com.manage.foruser.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserService userService;
    public UserController(UserService _userService){
        this.userService = _userService;
    }

    @GetMapping("/")
    public String home(){
        return "index";
    }

    //로그인 체크
    @GetMapping("/message")
    public String message(HttpServletRequest request){

        request.setAttribute("msg","로그인이 필요합니다.");
        request.setAttribute("url","/login");

        return "message";
    }

    //로그인페이지
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    //로그인체크
    @PostMapping("/loginCheck")
    public String loginCheck(String id, String password, HttpSession session, Model model){

        System.out.println("컨트롤러의 User"+id+"와"+password);
        String role =  this.userService.loginCheck(id, password);
        
        //회원일 때 View 처리
        if(role != null){
            //로그인 성공
            session.setAttribute("id",id);
            session.setAttribute("password",password);
            session.setAttribute("role",role);

        }else{
            model.addAttribute("msg","로그인 실패");
            return "/login";
        }
        //관리자일 때 View 처리
        return "index";
    }


    //회원가입 페이지
    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    //회원가입 처리
    @PostMapping("/register")
    public String register(User User, Model model){

        System.out.println("컨트롤러의 User"+User);

        int result = this.userService.insertMember(User);

        if (result == 1) {
            model.addAttribute("message", "[회원가입성공] 로그인 후 서비스 이용하세요");
            return "/login";
        } else {
            model.addAttribute("message", "[회원가입실패] 가입 정보를 다시 확인하시기 바랍니다.");
            return "/signup";
        }


    }
}
