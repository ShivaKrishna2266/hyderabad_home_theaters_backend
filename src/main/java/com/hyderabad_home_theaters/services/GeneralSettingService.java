package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.GeneralSettingsDTO;

import java.util.List;

public interface GeneralSettingService {

    GeneralSettingsDTO addGeneralSettings(GeneralSettingsDTO generalSettingsDTO);
    List<GeneralSettingsDTO> getAllGeneralSettings();
    GeneralSettingsDTO updateGeneralSettingsById(Long settingId,GeneralSettingsDTO generalSettingsDTO);
}
