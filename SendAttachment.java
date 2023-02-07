import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

class SendAttachment{
    public static void main(String [] args){

        String to = "ksravya1411@gmail.com";//change accordingly
        final String user="sravya@fintinc.com";//change accordingly
        final String password="xxxxx";//change accordingly

        //1) get the session object
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "mail.gmail.com");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });


        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("Message Alert");


            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("This is message body");
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            String filename = "SendAttachment.java";
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);
            message.setContent(multipart );


            System.out.println("message sent....");
        }catch (MessagingException ex) {ex.printStackTrace();}
    }
}