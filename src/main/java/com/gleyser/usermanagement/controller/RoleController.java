package com.gleyser.usermanagement.controller;

import com.gleyser.usermanagement.entity.Role;
import com.gleyser.usermanagement.exception.RoleNotFoundException;
import com.gleyser.usermanagement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@RequestMapping("/roles")
@Controller
public class RoleController {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String getRoles(Model model) {
        List<Role> roles = this.roleRepository.findAllByOrderByNameAsc();
        model.addAttribute("roles", roles);
        return "roles";
    }

    @PostMapping


    @ResponseStatus(HttpStatus.CREATED)
    public String createRole(@Valid Role role, Model model){
        this.roleRepository.save(role);
        List<Role> roles = this.roleRepository.findAllByOrderByNameAsc();
        model.addAttribute("roles", roles);
        return "roles";

    }

    @GetMapping("/{id}")
    public String finById(@PathVariable Long id, Model model){
        Optional<Role> role = this.roleRepository.findById(id);
        model.addAttribute("role", role.get());
        return "editrole";

    }

    @PutMapping
    public String updateById(@ModelAttribute @Valid Role role, Model model) throws RoleNotFoundException {
        verifyIfExists(role.getId());
        this.roleRepository.saveAndFlush(role);
        List<Role> roles = this.roleRepository.findAllByOrderByNameAsc();
        model.addAttribute("roles", roles);
        return "roles";
    }

    private Role verifyIfExists(Long id) throws RoleNotFoundException {
        return this.roleRepository.findById(id)
                .orElseThrow( () -> new RoleNotFoundException(id));
    }


}
