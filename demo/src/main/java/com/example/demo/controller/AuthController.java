package com.example.demo.controller;
import com.example.demo.model.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        Admin user = new Admin();
        model.addAttribute("user", user);
        return "register";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
