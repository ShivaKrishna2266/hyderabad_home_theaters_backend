package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.ProfileDTO;
import com.hyderabad_home_theaters.entity.Address;
import com.hyderabad_home_theaters.entity.Customer;
import com.hyderabad_home_theaters.entity.UserProfile;
import com.hyderabad_home_theaters.repository.AddressRepository;
import com.hyderabad_home_theaters.repository.CustomerRepository;
import com.hyderabad_home_theaters.repository.UserProfileRepository;
import com.hyderabad_home_theaters.repository.UserRepository;
import com.hyderabad_home_theaters.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;


    @Override
    public ProfileDTO getByFirstName(String firstName) {
        ProfileDTO profileDTO = new ProfileDTO();
        Customer customer = customerRepository.findByFirstName(firstName);
        if(customer!=null) {
            profileDTO.setEmail(customer.getEmail());
            profileDTO.setFirstName(customer.getFirstName());
            profileDTO.setSurname(customer.getSurname());
            profileDTO.setFullName(customer.getFullName());
            profileDTO.setMobileNumber(customer.getMobileNumber());
            List<Address> addressList = addressRepository.findByCustomerId(customer.getCustomerId());
            if(addressList!=null && addressList.size()>0) {
                Address address = addressList.get(0);
                profileDTO.setAddressLine1(address.getAddressLine1());
                profileDTO.setAddressLine2(address.getAddressLine2());
                profileDTO.setLandmark(address.getLandmark());
                profileDTO.setArea(address.getArea());
                profileDTO.setCity(address.getCity());
                profileDTO.setState(address.getState());
                profileDTO.setCountry(address.getCountry());
                profileDTO.setRegion(address.getRegion());
                profileDTO.setPostCode(address.getPinCode());
            }
            return profileDTO;
        }
        return null;
    }


    @Override
    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        if (profileDTO != null) {
            // Step 1: Save Customer
            Customer customer = new Customer();
            customer.setFirstName(profileDTO.getFirstName());
            customer.setSurname(profileDTO.getSurname());
            customer.setFullName(profileDTO.getFirstName() + " " + profileDTO.getSurname());
            customer.setEmail(profileDTO.getEmail());
            customer.setMobileNumber(profileDTO.getMobileNumber());
            customer.setCreatedBy("SYSTEM");
            customer.setCreatedDate(new Timestamp(new Date().getTime()));
            customer = customerRepository.save(customer); // ensure ID is generated

            // Step 2: Create and Save UserProfile (You must have this entity defined!)
            UserProfile userProfile = new UserProfile();
            userProfile.setCustomer(customer); // assuming there's a relation
            userProfile.setUsername(profileDTO.getUsername());
            userProfile.setMobileNumber(profileDTO.getMobileNumber());
            userProfile.setEmail(profileDTO.getEmail());
            userProfile.setCreatedBy("SYSTEM");
            userProfile.setCreatedDate(new Timestamp(new Date().getTime()));
            userProfile = userProfileRepository.save(userProfile);

            // Step 3: Save Address with userProfile
            Address address = new Address();
            address.setCustomerId(customer.getCustomerId());
            address.setUserProfile(userProfile); // 🔥 REQUIRED
            address.setAddressLine1(profileDTO.getAddressLine1());
            address.setAddressLine2(profileDTO.getAddressLine2());
            address.setLandmark(profileDTO.getLandmark());
            address.setArea(profileDTO.getArea());
            address.setCity(profileDTO.getCity());
            address.setState(profileDTO.getState());
            address.setCountry(profileDTO.getCountry());
            address.setRegion(profileDTO.getRegion());
            address.setPinCode(profileDTO.getPostCode());
            address.setCreatedBy("SYSTEM");
            address.setCreatedDate(new Timestamp(new Date().getTime()));
            addressRepository.save(address);
        }

        return profileDTO;
    }

    @Override
    public ProfileDTO updateProfile(String email, ProfileDTO profileDTO) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {

            return null;
        }

        customer.setFirstName(profileDTO.getFirstName());
        customer.setSurname(profileDTO.getSurname());
        customer.setFullName(profileDTO.getFirstName()+ " "+profileDTO.getSurname());
        customer.setEmail(profileDTO.getEmail());
        customer.setMobileNumber(profileDTO.getMobileNumber());
        customer.setModifiedBy("SYSTEM");
        customer.setModifiedDate(new Timestamp(new Date().getTime()));
        customer = customerRepository.save(customer);
        List<Address> addresses = addressRepository.findByCustomerId(customer.getCustomerId());
        if (!addresses.isEmpty()) {
            Address address = addresses.get(0);
            address.setCustomerId(customer.getCustomerId());
            address.setAddressLine1(profileDTO.getAddressLine1());
            address.setAddressLine2(profileDTO.getAddressLine2());
            address.setLandmark(profileDTO.getLandmark());
            address.setArea(profileDTO.getArea());
            address.setCity(profileDTO.getCity());
            address.setState(profileDTO.getState());
            address.setCountry(profileDTO.getCountry());
            address.setRegion(profileDTO.getRegion());
            address.setPinCode(profileDTO.getPostCode());
            address.setModifiedBy("SYSTEM");
            address.setModifiedDate(new Timestamp(new Date().getTime()));
            addressRepository.save(address);
        } else {
            return null;
        }

        return profileDTO;
    }
}
