package com.ncatania.userservice.infraestructure.adapters.out.smtp;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JavaMailAdapter {

    private final JavaMailSender mailSender;

    public void sendWelcomeEmail(String to, String name) {
        log.info("Sending welcome HTML email to {}", to);
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String htmlContent = """
                <div style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #1a1a1a; color: #ffffff; padding: 40px; text-align: center; border-radius: 10px;">
                    <div style="background-color: #2d2d2d; padding: 30px; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.5);">
                        <h1 style="color: #4CAF50; margin-bottom: 20px;">Welcome, %s! 🚀</h1>
                        <p style="font-size: 18px; line-height: 1.6; color: #cccccc;">
                            Your account has been successfully created in our URL Shortener service. 
                            We are thrilled to have you on board.
                        </p>
                        <hr style="border: 0; border-top: 1px solid #444; margin: 30px 0;">
                        <p style="font-size: 14px; color: #888;">
                            If you did not create this account, please ignore this email.
                        </p>
                        <div style="margin-top: 30px;">
                            <a href="#" style="background-color: #4CAF50; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold;">
                                Go to Dashboard
                            </a>
                        </div>
                    </div>
                    <p style="margin-top: 20px; font-size: 12px; color: #555;">&copy; 2026 NCatania Microservices</p>
                </div>
                """.formatted(name);

            helper.setText(htmlContent, true);
            helper.setTo(to);
            helper.setSubject("Welcome to our service! ✨");
            helper.setFrom("nicolas20032401@gmail.com");

            mailSender.send(mimeMessage);
            log.info("✅ HTML Email sent successfully to {}", to);

        } catch (MessagingException e) {
            log.error("❌ Failed to send HTML email to {}", to, e);
            throw new RuntimeException("Error sending email", e);
        }
    }
}