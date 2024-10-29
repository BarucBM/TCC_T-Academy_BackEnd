package com.TCC.controllers;

import com.TCC.domain.preferences.PreferenceDTO;
import com.TCC.services.PreferenceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preference")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceservice;

    @PutMapping("/{id}")
    public ResponseEntity changePreference (@PathVariable("id")String userId, @RequestBody @Valid PreferenceDTO preferenceDTO){
        ResponseEntity.ok(preferenceservice.updatePreference(userId, preferenceDTO));

        return ResponseEntity.ok().build();
    }
}
