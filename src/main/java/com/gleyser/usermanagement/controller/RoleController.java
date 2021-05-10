package com.gleyser.usermanagement.controller;

import com.gleyser.usermanagement.entity.Role;
import com.gleyser.usermanagement.exception.RoleNotFoundException;
import com.gleyser.usermanagement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/roles")
@Controller
public class RoleController {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<Role> roles = this.roleRepository.findAllByOrderByNameAsc();

        model.addAttribute("roles", roles);
        return "roles";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createRole(Role role, Model model){
        this.roleRepository.save(role);
        List<Role> roles = this.roleRepository.findAllByOrderByNameAsc();
        model.addAttribute("roles", roles);
        return "roles";

    }

    @PutMapping("/{id}")
    public String updateById(@PathVariable Long id, Role role) throws RoleNotFoundException {
        verifyIfExists(id);
        role.setId(id);
        this.roleRepository.save(role);
        return "roles";
    }

    private Role verifyIfExists(Long id) throws RoleNotFoundException {
        return this.roleRepository.findById(id)
                .orElseThrow( () -> new RoleNotFoundException(id));
    }


}
