package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.constant.UserGender;
import com.example.demo.constant.UserRole;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid email or password");
        }
        return "login"; 
    }



    
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    
    @PostMapping("/register")
    public String registerUser(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("username") String username,
                               @RequestParam("birthday") LocalDate birthday,
                               @RequestParam("gender") String gender,
                               @RequestParam("address") String address,
                               @RequestParam("phone") String phone,
                               Model model) {

        // 创建一个注册请求对象并设置参数
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setUserName(username);
        registerRequest.setBirthday(birthday);
        registerRequest.setGender(UserGender.valueOf(gender));
        registerRequest.setAddress(address);
        registerRequest.setPhone(phone);
        registerRequest.setRole(UserRole.User);

        // 调用 Service 层进行注册逻辑
        if (!userService.registerUser(registerRequest)) {
            model.addAttribute("error", "Email already in use");
            return "register";
        }

        return "redirect:/user/login";
    }
    
    @GetMapping("/adduser")
    public String showAddUserPage() {
        return "adduser"; // 返回 adduser.html 页面
    }

    @PostMapping("/adduser")
    public String addUser(@RequestParam("email") String email,
                          @RequestParam("password") String password,
                          @RequestParam("username") String username,
                          @RequestParam("birthday") LocalDate birthday,
                          @RequestParam("gender") String gender,
                          @RequestParam("address") String address,
                          @RequestParam("phone") String phone,
                          Model model) {

       
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setUserName(username);
        registerRequest.setBirthday(birthday);
        registerRequest.setGender(UserGender.valueOf(gender));
        registerRequest.setAddress(address);
        registerRequest.setPhone(phone);
        registerRequest.setRole(UserRole.User);

        
        if (!userService.registerUser(registerRequest)) {
            model.addAttribute("error", "Email already in use");
            return "adduser"; // 返回 adduser.html
        }

        return "redirect:/user/management"; // 注測成功後重定向到用户管理
    }
    
    @GetMapping("/management")
    public String showUserManagement(Model model) {
        List<User> users = userService.getAllUsers(); 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                            .anyMatch(role -> role.getAuthority().equals("Admin"));

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("users", users);
        return "management"; 
    }

    
    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user); 
        return "redirect:/user/management"; 
    }
    
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userId") Integer userId) {
        userService.deleteUserById(userId); // 使用正確的方法名稱
        return "redirect:/user/management"; // 刪除後重定向到用戶列表頁面
    }


}
