package com.TCC.repositories;

import com.TCC.domain.preferences.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreferencesRepoitory extends JpaRepository<Preference, String> {
}
