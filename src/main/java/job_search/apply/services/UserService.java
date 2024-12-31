package job_search.apply.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import job_search.apply.models.Users;
import job_search.apply.models.TemporaryUserData;
import job_search.apply.repositories.UserRepository;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpService otpService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    // Save user and generate OTP
    @Transactional
    public TemporaryUserData saveUser(Users user) {
        if (user == null) {
            logger.error("User object is null.");
            return null;
        }

        try {
            logger.info("User registration started for: {}", user.getUsername());

            // Encode password
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Save user to the database
            userRepository.save(user);

            // Generate and send OTP using OtpService
            boolean otpSent = otpService.generateAndSendOtp(user.getUsername());
            if (!otpSent) {
                throw new RuntimeException("Failed to send OTP email.");
            }

            // Prepare and return temporary user data
            TemporaryUserData tempUserData = new TemporaryUserData();
            tempUserData.setUsername(user.getUsername());
            tempUserData.setEmail(user.getEmail());
            tempUserData.setOtp(user.getOtp()); // OTP retrieved after successful generation

            logger.info("User registered successfully. OTP generated and sent.");
            return tempUserData;

        } catch (Exception e) {
            logger.error("Error during user registration: {}", e.getMessage());
            throw e; // Transaction will rollback automatically
        }
    }

    // Update user password
    public Users updatePassword(String username, String password) {
        Users user = userRepository.findByUsername(username);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(password)); // Encode new password
            return userRepository.save(user); // Save updated password
        }
        logger.warn("User not found for username: {}", username);
        return null;
    }

    // Update user profile details
    public Users updateUserProfile(String username, Users updatedUser) {
        Users currentUser = userRepository.findByUsername(username);
        if (currentUser != null) {
            currentUser.setFullName(updatedUser.getFullName());
            userRepository.save(currentUser);
            logger.info("User profile updated for: {}", username);
            return currentUser;
        }
        logger.error("User not found: {}", username);
        throw new UnsupportedOperationException("User not found: " + username);
    }

    // Fetch user data
    public Users getUserData(String username) {
        logger.info("Fetching user data for username: {}", username);
        Users user = userRepository.findByUsername(username);
        if (user != null) {
            logger.info("User found: {}", user.getUsername());
            return user;
        } else {
            logger.warn("User not found for username: {}", username);
            return null;
        }
    }
}
