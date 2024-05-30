package com.example.demo.service;

import com.example.demo.dto.AdminDto;
import com.example.demo.model.Admin;
import com.example.demo.model.Role;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private AdminRepository adminRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private Logger logger =  Logger.getLogger(this.getClass().getName());
    public AdminService(AdminRepository adminRepositoryRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepositoryRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setName(adminDto.getName());
        admin.setUsername(adminDto.getUsername());

        logger.info("here");
        admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        admin.setRoles(Arrays.asList(role));
        adminRepository.save(admin);
    }

    public Admin findByUserName(String username) {
        return adminRepository.findByUsername(username);
    }


    public List<AdminDto> findAllUsers() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map((admin) -> convertEntityToDto(admin))
                .collect(Collectors.toList());
    }

    private AdminDto convertEntityToDto(Admin admin){
        AdminDto adminDto = new AdminDto();
        adminDto.setName(admin.getName());
        adminDto.setUsername(admin.getUsername());
        return adminDto;
    }


    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
