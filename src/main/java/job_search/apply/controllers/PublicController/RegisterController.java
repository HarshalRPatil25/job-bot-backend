package job_search.apply.controllers.PublicController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import job_search.apply.models.Users;
import job_search.apply.models.TemporaryUserData;
import job_search.apply.services.OtpService;
import job_search.apply.services.UserService;

@RestController
@RequestMapping("/public/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    // Step 1: Register User (Save User Details, Send OTP)
    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody Users user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation failed: " + result.getAllErrors());
        }

        try {
            TemporaryUserData temporaryUserData = userService.saveUser(user);
            if (temporaryUserData != null) {
                otpService.generateAndSendOtp(user.getUsername());
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("User registered successfully. OTP sent to your email.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("User registration failed.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    // Step 2: Verify OTP
    @PostMapping("/verify-otp/{username}/{otp}")
    public ResponseEntity<?> verifyOtp(@PathVariable String username, @PathVariable String otp) {
        try {
            if (otpService.validateOtp(username, otp)) {
                return ResponseEntity.ok("OTP verification successful.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid or expired OTP.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}
