package job_search.apply.services.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MessagingException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Send plain text OTP email
    public boolean sendOtp(String toEmail, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + otp + "\nThis OTP is valid for 5 minutes.");
            mailSender.send(message);
            return true; // Email sent successfully
        } catch (MailException e) {
            // Log the error
            System.err.println("Error sending OTP email: " + e.getMessage());
            return false; // Email sending failed
        }
    }
    

    // Send HTML email for OTP or other notifications
    public void sendHtmlEmail(String toEmail, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true for HTML content
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // Set to true to indicate it's HTML
            mailSender.send(message);
        } catch (MailException e) {
            // Handle errors when sending HTML email
            System.err.println("Error sending HTML email: " + e.getMessage());
            throw new RuntimeException("Error sending HTML email", e);
        }
    }
}
