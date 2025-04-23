package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.LoginDTO;
import com.hyderabad_home_theaters.DTOs.ProfileDTO;
import com.hyderabad_home_theaters.DTOs.UserDTO;
import com.hyderabad_home_theaters.dao.UserDao;
import com.hyderabad_home_theaters.entity.Address;
import com.hyderabad_home_theaters.entity.OtpVerification;
import com.hyderabad_home_theaters.entity.Role;
import com.hyderabad_home_theaters.entity.User;
import com.hyderabad_home_theaters.entity.UserProfile;
import com.hyderabad_home_theaters.entity.WatiTemplatesResponse;
import com.hyderabad_home_theaters.repository.AddressRepository;
import com.hyderabad_home_theaters.repository.CustomerRepository;
import com.hyderabad_home_theaters.repository.OtpVerificationRepository;
import com.hyderabad_home_theaters.repository.UserProfileRepository;
import com.hyderabad_home_theaters.repository.UserRepository;
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

    public UserDTO registerUser(UserDTO request) {
        // Step 1: Generate OTP and Send via WATI
        WatiTemplatesResponse response = watiService.sendWatiOTP(request.getPhoneNumber());

        // Step 2: Save OTP in OtpVerification table
        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setEmail(request.getEmail());
        otpVerification.setMobileNumber(request.getPhoneNumber());
        otpVerification.setOtpCode(response.getOtp());  // Save the generated OTP
        otpVerification.setOtpStatus("Pending");  // Initial status before verification
        otpVerification.setCreatedBy("System");
        otpVerification.setUpdatedBy("System");
        otpVerification.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        otpVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        otpVerificationRepository.save(otpVerification);

        // Return response to ask user to input OTP
        // (This can be done on the frontend where the user will submit the OTP)
        return request;
    }

    public UserDTO verifyOtpAndRegisterUser(UserDTO request) {
        // Step 1: Verify OTP
        Optional<OtpVerification> latestOtp = otpVerificationRepository.findTopByMobileNumberOrderByCreatedAtDesc(request.getPhoneNumber());

        // Validate OTP
        if (latestOtp.isEmpty() || latestOtp.get().getOtpCode() == null || !latestOtp.get().getOtpCode().equals(request.getOtp())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        // Step 2: Save User entity
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

        // Step 3: Save UserProfile entity
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(request.getUsername());
        userProfile.setEmail(request.getEmail());
        userProfile.setMobileNumber(request.getPhoneNumber());
        userProfile.setPassword(request.getPassword()); // NOTE: Storing raw password here is not recommended
        userProfile.setCreatedBy("System");
        userProfile.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        userProfile.setUpdatedBy("System");
        userProfile.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
        userProfileRepository.save(userProfile);

        // Step 4: Mark OTP as Verified
        latestOtp.get().setOtpStatus("Verified");
        otpVerificationRepository.save(latestOtp.get());

        // Return the registered user
        return request;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not Found: " + username));
    }

    public ProfileDTO getProfileUsername(String username) {
        UserProfile userProfile = userProfileRepository.findByUsername(username);
        if (userProfile == null) {
            throw new RuntimeException("UserProfile not found for username: " + username);
        }

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUsername(userProfile.getUsername());
        profileDTO.setFullName(userProfile.getUsername()); // Assuming no fullName field
        profileDTO.setEmail(userProfile.getEmail());
        profileDTO.setMobileNumber(userProfile.getMobileNumber());

        List<Address> addressList = addressRepository.findByCustomerId(userProfile.getProfileId());
        if (addressList != null && !addressList.isEmpty()) {
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

    public User logInUser(LoginDTO loginDTO) {
        return userRepository.findByUsername(loginDTO.getUsername())
                .orElse(null);
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
