package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.GeneralSettingsDTO;
import com.hyderabad_home_theaters.entity.GeneralSettings;
import com.hyderabad_home_theaters.exception.ApplicationBusinessException;
import com.hyderabad_home_theaters.mapper.GeneralSettingsMapper;
import com.hyderabad_home_theaters.repository.GeneralSettingsRepository;
import com.hyderabad_home_theaters.services.GeneralSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneralSettingsImpl implements GeneralSettingService {


    @Autowired
    private GeneralSettingsRepository generalSettingsRepository;

    @Override
    public GeneralSettingsDTO addGeneralSettings(GeneralSettingsDTO generalSettingsDTO) throws ApplicationBusinessException {
      try {
          List<GeneralSettings> generalSettingsList =generalSettingsRepository.findAll();
          if (generalSettingsList.size() == 0){
              GeneralSettings convertToEntity = GeneralSettingsMapper.convertToEntity(generalSettingsDTO);
              convertToEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
              convertToEntity.setCreatedBy("System");
              convertToEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
              convertToEntity.setUpdatedBy("System");
              generalSettingsRepository.save(convertToEntity);
          }else {
              GeneralSettings generalSettings = generalSettingsList.get(0);
              generalSettings.setLogoDark(generalSettingsDTO.getLogoDark());
              generalSettings.setFavIcon(generalSettingsDTO.getFavIcon());
              generalSettings.setFooter(generalSettingsDTO.getFooter());
              generalSettings.setAddress(generalSettingsDTO.getAddress());
              generalSettings.setPhoneNumber(generalSettingsDTO.getPhoneNumber());
              generalSettings.setEmail(generalSettingsDTO.getEmail());
              generalSettings.setCompanyName(generalSettingsDTO.getCompanyName());
              generalSettings.setColorCode(generalSettingsDTO.getColorCode());
              generalSettings.setGst(generalSettingsDTO.getGst());
              generalSettingsRepository.save(generalSettings);
          }
          return generalSettingsDTO;
      } catch (Exception e) {
          throw new ApplicationBusinessException("Error while adding general settings");
      }
    }

    @Override
    public List<GeneralSettingsDTO> getAllGeneralSettings() {
        List<GeneralSettings> findGeneralSettings = generalSettingsRepository.findAll();
        return  findGeneralSettings
                .stream()
                .map(GeneralSettingsMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GeneralSettingsDTO updateGeneralSettingsById(Long settingId, GeneralSettingsDTO generalSettingsDTO) throws ApplicationBusinessException {
            GeneralSettings generalSettings = generalSettingsRepository.findById(settingId)
                    .orElseThrow(() -> new ApplicationBusinessException("General Settings not found"));
            generalSettings.setLogoDark(generalSettingsDTO.getLogoDark());
            generalSettings.setEmail(generalSettingsDTO.getEmail());
            generalSettings.setFooter(generalSettingsDTO.getFooter());
            generalSettings.setCompanyName(generalSettingsDTO.getCompanyName());
            generalSettings.setAddress(generalSettingsDTO.getAddress());
            generalSettings.setColorCode(generalSettingsDTO.getColorCode());
            generalSettings.setFavIcon(generalSettingsDTO.getFavIcon());
            generalSettings.setGst(generalSettingsDTO.getGst());
            generalSettings.setFavIconImage(generalSettingsDTO.getFavIconImage());
            GeneralSettings generalSettings1 = generalSettingsRepository.save(generalSettings);

            return GeneralSettingsMapper.convertToDTO(generalSettings1);
        }

    }

