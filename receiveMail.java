import java.util.Scanner;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class ReceiveMail {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Scanner scanner = new Scanner(System.in);

        System.out.println("username for authentication: ");
        final String username = scanner.nextLine();

        System.out.println("password for authentication: ");
        final String password = scanner.nextLine();

        System.out.println("to email: ");
        String toEmailAddress = scanner.nextLine();

        System.out.println("subject: ");
        String subject = scanner.nextLine();

        System.out.println("message: ");
        String textMessage = scanner.nextLine();

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmailAddress));
            message.setSubject(subject);
            message.setText(textMessage);


            System.out.println("\nyour message received successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
