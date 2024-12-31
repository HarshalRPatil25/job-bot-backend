package job_search.apply.controllers.PublicController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import job_search.apply.models.Login.RequestLogin;
import job_search.apply.models.Login.ResponseLogin;
import job_search.apply.services.LoginService.LoginService;
import job_search.apply.models.Login.RequestForgetPassword;
import job_search.apply.models.Login.ResponseForgetPassword;

import job_search.apply.services.OtpService;


@RestController
@RequestMapping("/public/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private OtpService otpService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody RequestLogin loginData) {
        try {
            ResponseLogin responseLogin = loginService.loginStatus(loginData);

            if (responseLogin.isValidation()) {
                return ResponseEntity.ok("Login Successful: " + responseLogin);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Login Failed: Invalid username or password.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

   

   
}
