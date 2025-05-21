package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.CategoryDTO;
import com.hyderabad_home_theaters.DTOs.LoginDTO;
import com.hyderabad_home_theaters.DTOs.ProfileDTO;
import com.hyderabad_home_theaters.DTOs.UserDTO;
import com.hyderabad_home_theaters.dao.UserDao;
import com.hyderabad_home_theaters.entity.*;
import com.hyderabad_home_theaters.mapper.CategoryMapper;
import com.hyderabad_home_theaters.mapper.UserMapper;
import com.hyderabad_home_theaters.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserDao userDao;

    @Autowired
    private WatiService watiService;

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    // ✅ Send OTP only
    public String registerUser(UserDTO request) {
        // ✅ Check if username & mobile number already exists
        if (userRepository.existsByUsernameAndPhoneNumber(request.getUsername(), request.getPhoneNumber())) {
            throw new RuntimeException("Username and phoneNumber already registered");
        }

        WatiTemplatesResponse response = watiService.sendWatiOTP(request.getPhoneNumber());

        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setEmail(request.getEmail());
        otpVerification.setMobileNumber(request.getPhoneNumber());
        otpVerification.setOtpCode(response.getOtp());
        otpVerification.setOtpStatus("Pending");
        otpVerification.setCreatedBy("System");
        otpVerification.setUpdatedBy("System");
        otpVerification.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        otpVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        otpVerificationRepository.save(otpVerification);

        return "OTP sent successfully";
    }

    // ✅ Final registration after OTP verification
    public UserDTO verifyOtpAndRegisterUser(UserDTO request) {
        Optional<OtpVerification> latestOtp = otpVerificationRepository.findTopByMobileNumberOrderByCreatedAtDesc(request.getPhoneNumber());

        if (latestOtp.isEmpty() || latestOtp.get().getOtpCode() == null || !latestOtp.get().getOtpCode().equals(request.getOtp())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);

        Role role = roleService.findByRoleName(request.getRole());
        if (role == null) {
            throw new RuntimeException("Role not found: " + request.getRole());
        }

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
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

        latestOtp.get().setOtpStatus("Verified");
        otpVerificationRepository.save(latestOtp.get());

        return request;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not Found: " + username));
    }

    public ProfileDTO getProfileUsername(String username) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByUsername(username);
        UserProfile userProfile = optionalUserProfile.orElseThrow(
                () -> new RuntimeException("UserProfile not found for username: " + username)
        );

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUsername(userProfile.getUsername());
        profileDTO.setFullName(userProfile.getUsername()); // Update with real logic if needed
        profileDTO.setEmail(userProfile.getEmail());
        profileDTO.setMobileNumber(userProfile.getMobileNumber());

        // ✅ Fetch addresses using the mapped relation
        List<Address> addressList = addressRepository.findByUserProfile(userProfile);

        if (addressList != null && !addressList.isEmpty()) {
            Address address = addressList.get(0); // Just using the first address for demo
            profileDTO.setAddressLine1(address.getAddressLine1());
            profileDTO.setAddressLine2(address.getAddressLine2());
            profileDTO.setLandmark(address.getLandmark());
            profileDTO.setArea(address.getArea());
            profileDTO.setCity(address.getCity());
            profileDTO.setPostCode(address.getPinCode());
            profileDTO.setState(address.getState());
            profileDTO.setCountry(address.getCountry());
            profileDTO.setRegion(address.getRegion());
        } else {
            profileDTO.setAddressLine1("N/A");
        }

        return profileDTO;
    }


    public User findOne(String username) {
        return userDao.findByUsername(username);
    }

    public UserDTO getUserById(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            return UserMapper.convertedToDTO(user);
        }else {
            return null;
        }
    }
}
