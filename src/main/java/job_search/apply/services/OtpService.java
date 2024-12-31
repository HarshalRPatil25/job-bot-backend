package job_search.apply.services;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import job_search.apply.models.Users;
import job_search.apply.repositories.UserRepository;
import job_search.apply.services.EmailService.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OtpService {

    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private final Random random = new Random();

    // Generate and send OTP for registration and password reset
    public boolean generateAndSendOtp(String username) {
        logger.info("Generating OTP for username: {}", username);

        // Retrieve the user from the database using the username
        Users user = userRepository.findByUsername(username);

        if (user != null) {
            // Generate a 6-digit OTP
            String otp = String.format("%06d", random.nextInt(999999));
            logger.info("Generated OTP for {}: {}", username, otp);

            // Set OTP and expiry time (5 minutes)
            user.setOtp(otp);
            user.setOtpExpiry(LocalDateTime.now().plusMinutes(5)); // OTP expires in 5 minutes
            userRepository.save(user); // Save OTP and expiry time to the database

            // Send OTP to the user's email
            boolean otpSent = emailService.sendOtp(user.getEmail(), otp);
            if (!otpSent) {
                logger.error("Failed to send OTP to email for user: {}", username);
                return false; // OTP sending failed
            }

            logger.info("OTP sent successfully to {}'s email: {}", username, user.getEmail());
            return true; // OTP generated and sent successfully
        } else {
            logger.warn("User not found for username: {}", username);
            return false; // User not found in the database
        }
    }

    // Validate OTP during registration or password reset
    public boolean validateOtp(String username, String otp) {
        logger.info("Validating OTP for username: {}", username);
        Users user = userRepository.findByUsername(username);

        if (user != null && user.getOtp() != null && user.getOtp().equals(otp)) {
            if (user.getOtpExpiry().isAfter(LocalDateTime.now())) {
                // Clear OTP after successful validation
                user.setOtp(null);
                userRepository.save(user);
                logger.info("OTP validation successful for user: {}", username);
                return true; // OTP is valid and within the expiry time
            } else {
                logger.warn("OTP expired for username: {}", username);
            }
        }

        logger.warn("Invalid OTP for username: {}", username);
        return false; // Invalid OTP or expired OTP
    }
}
