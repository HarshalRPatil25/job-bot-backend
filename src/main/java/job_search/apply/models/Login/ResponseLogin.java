package job_search.apply.models.Login;

import org.springframework.stereotype.Component;

import com.mongodb.lang.NonNull;

import lombok.Data;

@Component
@Data
public class ResponseLogin {
     
    private String username;
    
    private String password;

    private boolean validation;
}
