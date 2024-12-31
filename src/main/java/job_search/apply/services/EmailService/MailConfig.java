package job_search.apply.services.EmailService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Configure SMTP server details (adjust as per your provider)
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);  // Use the appropriate port for your SMTP provider
        mailSender.setUsername("harshalrp25@gmail.com");  // Enter your email
        mailSender.setPassword("yibl pjez asqu dshe");  // Enter your email password or app-specific password

        // Optional: Configure additional properties like TLS/SSL
        mailSender.getJavaMailProperties().put("mail.transport.protocol", "smtp");
        mailSender.getJavaMailProperties().put("mail.smtp.auth", "true");
        mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
        mailSender.getJavaMailProperties().put("mail.debug", "true");

        return mailSender;
    }
}
