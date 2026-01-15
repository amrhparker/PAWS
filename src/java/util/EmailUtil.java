package util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {

    public static void sendAdoptionApprovalEmail(
            String toEmail,
            String adopterName,
            String petName) {

        final String fromEmail = "petadoptionwelfarepaws@gmail.com";
        final String password = "xgsdomkbapewxwkm"; 

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, "PAWS Adoption Team"));
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail)
            );

            message.setSubject("Adoption Approved – PAWS Pet Adoption");

            String emailContent =
                "Dear " + adopterName + ",\n\n" +

                "Congratulations! We are pleased to inform you that your adoption application "
                + "has been APPROVED.\n\n" +

                "Adopted Pet Details\n"
                + "----------------------------------\n"
                + "Pet Name : " + petName + "\n\n" +

                "Pet Pick-Up Information\n"
                + "----------------------------------\n"
                + "PAWS Pet Adoption Centre\n"
                + "Lot 123, Jalan Haiwan Prihatin,\n"
                + "40150 Shah Alam, Selangor\n\n"
                + "Operating Hours:\n"
                + "Monday – Friday, 10:00 AM – 5:00 PM\n\n" +

                "Please bring the following documents:\n"
                + "• Your Identification Card (IC)\n"
                + "• This approval email (hardcopy or softcopy)\n\n" +

                "Kindly collect your pet within 7 days from the date of this email.\n"
                + "If you are unable to attend within this period, please contact us immediately.\n\n" +

                "If you have any questions, feel free to reply to this email.\n\n" +

                "Thank you for choosing adoption and giving a loving home to a pet.\n\n" +

                "Warm regards,\n"
                + "PAWS Adoption Team\n"
                + "petadoptionwelfarepaws@gmail.com";

            message.setText(emailContent);

            System.out.println("Attempting to send email...");
            System.out.println("To: " + toEmail);

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (Exception e) {
            System.out.println("Failed to send email");
            e.printStackTrace();
        }
    }
}
