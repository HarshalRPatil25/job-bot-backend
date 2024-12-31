package job_search.apply.models;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class TemporaryUserData {

    private String username;

    private String email;
    private String otp;
    
}
