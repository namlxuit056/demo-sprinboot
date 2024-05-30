package com.example.demo.controller;

import com.example.demo.dto.AdminDto;
import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.AdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class AdminController {
    private static final Logger log = LogManager.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    AdminRepository adminRepository;


    @PostMapping(value = "/admins", consumes = {"application/x-www-form-urlencoded;charset=UTF-8"})
    public String  createAdmin(@ModelAttribute AdminDto admin, BindingResult result, Model model) {
            System.out.println("User: " + admin);
            Admin existingAdmin = adminService.findByUserName(admin.getUsername());

            if(existingAdmin != null){
                result.rejectValue("email", null,
                        "There is already an account registered with the same username");
            }

            if(result.hasErrors()){
                model.addAttribute("user", admin);
                return "/register";
            }


            adminService.saveUser(admin);

            return "redirect:/register?success";

    }

    @GetMapping("/admins")
    public String admins(Model model){
        List<AdminDto> admins = adminService.findAllUsers();
        model.addAttribute("admins", admins);
        return "admins";
    }
}
