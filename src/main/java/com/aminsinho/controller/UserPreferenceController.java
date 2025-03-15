package com.aminsinho.controller;

import com.aminsinho.iservice.UserPreferenceServiceInterface;
import com.aminsinho.models.UserPreference;
import com.aminsinho.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/preferences")
public class UserPreferenceController {
    @Autowired
    private UserPreferenceServiceInterface userPreferenceService;

    @PostMapping
    public void saveUserPreference(@RequestBody UserPreference preference) {
        userPreferenceService.saveUserPreference(preference);
    }
}
