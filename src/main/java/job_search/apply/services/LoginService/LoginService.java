package job_search.apply.services.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import job_search.apply.models.Users;
import job_search.apply.models.Login.RequestLogin;
import job_search.apply.models.Login.ResponseLogin;
import job_search.apply.repositories.UserRepository;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public ResponseLogin loginStatus(RequestLogin loginData) {
        // Initialize ResponseLogin
        ResponseLogin responseLogin = new ResponseLogin();

        String username = loginData.getUsername();
        String password = loginData.getPassword();

        // Find the user by username
        Users currentUser = userRepository.findByUsername(username);

        // If the user is found
        if (currentUser != null) {
            // Get the stored password (hashed)
            String storedPassword = currentUser.getPassword();

            // Compare the entered password with the stored hashed password
            if (passwordEncoder.matches(password, storedPassword)) {
                // Login successful
                responseLogin.setUsername(username);
                responseLogin.setPassword(password);
                responseLogin.setValidation(true);
                return responseLogin;
            }
        }

        // Login failed
        responseLogin.setValidation(false);
        return responseLogin;
    }
}
