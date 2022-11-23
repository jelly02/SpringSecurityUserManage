package com.manage.foruser.controller;

import com.manage.foruser.service.UserService;
import com.manage.foruser.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor //final 객체 생성자 생성
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String home(){
        return "index";
    }

    //로그인페이지
    @GetMapping("/login")
    public String login(){
        return "login";
    }


    //성공 페이지
    @GetMapping("/main")
    public String main(){
        return "main";
    }

    //로그인체크
    @PostMapping("/loginCheck")
    public String loginCheck(String id, String password, HttpSession session, Model model){

        System.out.println("컨트롤러의 User"+id+"와"+password);
        String role =  userService.loginCheck(id, password);
        
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
        return "sign-up";
    }

    //중복 아이디 처리
    @ResponseBody
    @PostMapping("/id-check")
    public int idChk(@RequestParam("id") String id) throws Exception {
        int result = userService.idChk(id);

        return result;
    }

    //회원가입 처리
    @PostMapping("/register")
    public String register(User user, Model model) throws Exception {

        System.out.println("여기 컨트롤러인데요. 왔나요?");
        System.out.println("컨트롤러의 User"+user);

        //아이디 중복 확인
        String id = user.getId();
        int idCheck = userService.idChk(id);

        if (idCheck == 0) {
            //아이디 중복되지 않음
            int result = userService.insertMember(user);
            System.out.println("여기 컨트롤러인데요 회원가입 됐나요?**************");

            model.addAttribute("message", "[회원가입성공] 로그인 후 서비스 이용하세요");
            return "/loginSuccess";
        } else {
            //아이디 중복됨
            return "/signup";
        }
    }

    //로그아웃
    @GetMapping("/logout")
    public String logoutConfirm(HttpServletRequest request, HttpServletResponse response) {

        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());

        return "redirect:/login";
    }


    //메시지 처리
    @GetMapping("/message")
    public String message(HttpServletRequest request){

        request.setAttribute("msg","로그인이 필요합니다.");
        request.setAttribute("url","/login");

        return "message";
    }

}

