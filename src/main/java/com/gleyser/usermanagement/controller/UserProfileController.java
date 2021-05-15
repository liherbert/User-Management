package com.gleyser.usermanagement.controller;

import com.gleyser.usermanagement.entity.UserProfile;
import com.gleyser.usermanagement.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/profiles")
@Controller
public class UserProfileController {

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileController(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;

    }

    @GetMapping
    public String getProfiles(Model model) {
        List<UserProfile> profiles = this.userProfileRepository.findAllByOrderByNameAsc();
        model.addAttribute("profiles", profiles);
        return "profiles";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createProfile(Model model, @Valid UserProfile userProfile){
       this.userProfileRepository.save(userProfile);
        List<UserProfile> profiles = this.userProfileRepository.findAllByOrderByNameAsc();
        model.addAttribute("profiles", profiles);
        return "profiles";

    }



}
