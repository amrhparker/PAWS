package util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {

    public static void sendAdoptionApprovalEmail(
            String toEmail,
            String adopterName,
            String petName) {

        final String fromEmail = "petadoptionwelfarepaws@gmail.com";   // EMAIL KAU
        final String password = "xgsdomkbapewxwkm";      // APP PASSWORD

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail)
            );

            message.setSubject("Adoption Approved – PAWS");

            message.setText(
                "Dear " + adopterName + ",\n\n" +
                "Congratulations! Your adoption application has been APPROVED.\n\n" +
                "Pet Name: " + petName + "\n\n" +
                "Please come to PAWS shelter within 7 days to complete the adoption.\n" +
                "Bring your IC and this email for verification.\n\n" +
                "Thank you,\nPAWS Adoption Team"
            );

            Transport.send(message);

            System.out.println("Email sent successfully");
            System.out.println("Attempting to send email...");
            System.out.println("To: " + toEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
