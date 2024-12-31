package job_search.apply.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
@Document(collection = "User")
public class Users {

    @Id
    private ObjectId userId;

    @Indexed(unique = true)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Full Name is mandatory")
    private String fullName;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @DBRef
    private List<AppliedJobs> list = new ArrayList<>();


    @DBRef
    private List<BookmarkJobs> bookmarkJobsList=new ArrayList<>();

    // New fields for OTP verification
    private String otp;
    private LocalDateTime otpExpiry;
}
