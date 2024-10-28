package com.TCC.services;

import com.TCC.domain.preferences.NotificationType;
import com.TCC.domain.preferences.Preference;
import com.TCC.domain.preferences.PreferenceDTO;
import com.TCC.domain.user.User;
import com.TCC.repositories.PreferencesRepoitory;
import com.TCC.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PreferenceService {

    @Autowired
    private PreferencesRepoitory preferencesRepoitory;

    @Autowired
    private UserRepository userRepository;

    public String updatePreference(String userId, PreferenceDTO preferenceDTO){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));

        for (Preference item: user.getPreference()){
            if (item.getNotificationType().name().equalsIgnoreCase(preferenceDTO.notificationType())){
                BeanUtils.copyProperties(preferenceDTO, item);
                preferencesRepoitory.save(item);
                return "Preference added!" ;
            }
        }
        return  "Invalid type!";

    }

    public void newUserPreferences(String userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
        List<Preference> preferences = new ArrayList<>();

        for (NotificationType type: NotificationType.values()){
            Preference preference = new Preference();
            preference.setNotificationType(type);
            preference.setUser(user);
            preferences.add(preference);
            preference.setIsActive(true);

            preferencesRepoitory.save(preference);
        }

        user.setPreference(preferences);
    }
}
