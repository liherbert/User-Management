package com.gleyser.usermanagement.controller;

import com.gleyser.usermanagement.entity.Role;
import com.gleyser.usermanagement.entity.UserProfile;
import com.gleyser.usermanagement.exception.RoleNotFoundException;
import com.gleyser.usermanagement.exception.UserProfileNotFoundException;
import com.gleyser.usermanagement.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public String finById(@PathVariable Long id, Model model){
        Optional<UserProfile> profile = this.userProfileRepository.findById(id);
        model.addAttribute("profile", profile.get());
        return "editprofile";
    }

    @PutMapping
    public String editProfile(@ModelAttribute @Valid UserProfile userProfile, Model model) throws UserProfileNotFoundException {
        verifyIfProfileExists(userProfile.getId());
        this.userProfileRepository.saveAndFlush(userProfile);
        List<UserProfile> profiles = this.userProfileRepository.findAllByOrderByNameAsc();
        model.addAttribute("profiles", profiles);
        return "profiles";
    }

    private UserProfile verifyIfProfileExists(Long id) throws UserProfileNotFoundException {
        return this.userProfileRepository.findById(id)
                .orElseThrow( () -> new UserProfileNotFoundException(id));
    }




}
