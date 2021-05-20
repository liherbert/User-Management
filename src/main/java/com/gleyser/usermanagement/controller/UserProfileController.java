package com.gleyser.usermanagement.controller;

import com.gleyser.usermanagement.entity.Role;
import com.gleyser.usermanagement.entity.UserProfile;
import com.gleyser.usermanagement.exception.DuplicateNameException;
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
    public String createProfile(Model model, @Valid UserProfile userProfile) throws DuplicateNameException, UserProfileNotFoundException {
        saveProfile(userProfile);
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
    public String editProfile(@ModelAttribute @Valid UserProfile userProfile, Model model) throws UserProfileNotFoundException, DuplicateNameException {
        saveProfile(userProfile);
        List<UserProfile> profiles = this.userProfileRepository.findAllByOrderByNameAsc();
        model.addAttribute("profiles", profiles);
        return "profiles";
    }

    @DeleteMapping("/{id}")
    public String deleteRole(@PathVariable Long id, Model model) throws UserProfileNotFoundException {
        this.userProfileRepository.delete(verifyIfProfileExists(id));
        List<UserProfile> profiles = this.userProfileRepository.findAllByOrderByNameAsc();
        model.addAttribute("profiles", profiles);
        return "profiles";
    }

    private UserProfile verifyIfProfileExists(Long id) throws UserProfileNotFoundException {
        return this.userProfileRepository.findById(id)
                .orElseThrow( () -> new UserProfileNotFoundException(id));
    }

    private UserProfile verifyIfNameProfileisUnique(String name) throws DuplicateNameException {
        if (!this.userProfileRepository.findByName(name).isEmpty()){
            throw new DuplicateNameException();
        }
        return this.userProfileRepository.findByName(name).get(0);

    }

    private void saveProfile(UserProfile userProfile) throws UserProfileNotFoundException, DuplicateNameException {
        verifyIfProfileExists(userProfile.getId());
        verifyIfNameProfileisUnique(userProfile.getName());
        this.userProfileRepository.saveAndFlush(userProfile);

    }

}
