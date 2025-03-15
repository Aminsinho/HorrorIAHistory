package com.aminsinho.service;

import com.aminsinho.iservice.UserPreferenceServiceInterface;
import com.aminsinho.models.UserPreference;
import com.aminsinho.repository.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPreferenceService implements UserPreferenceServiceInterface {
    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    public void saveUserPreference(UserPreference preference) {
        userPreferenceRepository.save(preference);
    }
}
