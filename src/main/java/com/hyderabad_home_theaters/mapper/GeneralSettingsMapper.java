package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.GeneralSettingsDTO;
import com.hyderabad_home_theaters.entity.GeneralSettings;
import org.modelmapper.ModelMapper;

public class GeneralSettingsMapper {

    public static final ModelMapper modelMapper = new ModelMapper();

    public static GeneralSettingsDTO convertToDTO(GeneralSettings generalSettings){
        return modelMapper.map(generalSettings,GeneralSettingsDTO.class);
    }
    public static GeneralSettings convertToEntity(GeneralSettingsDTO generalSettingsDTO){
        return modelMapper.map(generalSettingsDTO, GeneralSettings.class);
    }
}
