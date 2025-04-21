package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.LoginDTO;
import com.hyderabad_home_theaters.DTOs.ProfileDTO;
import com.hyderabad_home_theaters.DTOs.UserDTO;
import com.hyderabad_home_theaters.dao.UserDao;
import com.hyderabad_home_theaters.entity.Address;
import com.hyderabad_home_theaters.entity.Customer;
import com.hyderabad_home_theaters.entity.Role;
import com.hyderabad_home_theaters.entity.User;
import com.hyderabad_home_theaters.entity.UserProfile;
import com.hyderabad_home_theaters.repository.AddressRepository;
import com.hyderabad_home_theaters.repository.CustomerRepository;
import com.hyderabad_home_theaters.repository.UserProfileRepository;
import com.hyderabad_home_theaters.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
   @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    public UserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Autowired
    private AddressRepository addressRepository;


    public UserDTO registerUser(UserDTO request){
       User user = new User();
       user.setUsername(request.getUsername());
       user.setEmail(request.getEmail());
       user.setPhoneNumber(request.getPhoneNumber());
       user.setPassword(passwordEncoder.encode(request.getPassword()));
         Role adminRole = roleService.findByRoleName(request.getRole());
       if(adminRole == null){
           throw new RuntimeException("Admin role not found");
       }
       Set<Role> roles = new HashSet<>();
       roles.add(adminRole);
       user.setRoles(roles);
       user.setEnabled(true);

          userRepository.save(user);

        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(request.getUsername());
        userProfile.setEmail(request.getEmail());
        userProfile.setMobileNumber(request.getPhoneNumber());
        userProfile.setPassword(request.getPassword());
        userProfile.setCreatedBy("System");
        userProfile.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        userProfile.setUpdatedBy("System");
        userProfile.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

        userProfileRepository.save(userProfile);
        return request;
   }

   public User findByUsername(String username){
       return userRepository.findByUsername(username)
               .orElseThrow(() -> new RuntimeException("User Not Found"));
   }



    public ProfileDTO getProfileUsername(String username) {
        ProfileDTO profileDTO = new ProfileDTO();
        UserProfile userProfile = userProfileRepository.findByUsername(username);
        if(userProfile!=null) {
            profileDTO.setEmail(userProfile.getEmail());
//            profileDTO.setFirstName(userProfile.getUsername());
//            profileDTO.setSurname(userProfile.get());
            profileDTO.setUsername(userProfile.getUsername());
            profileDTO.setFullName(userProfile.getUsername());
            profileDTO.setMobileNumber(userProfile.getMobileNumber());
            List<Address> addressList = addressRepository.findByCustomerId(userProfile.getProfileId());
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

   public User logInUser(LoginDTO loginDTO){
       Optional<User> user =userRepository.findByUsername(loginDTO.getUsername());
       if (user != null){
            return  user.get();
       }
       return  null;
   }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public User findOne(String username) {
        return userDao.findByUsername(username);
    }


}
