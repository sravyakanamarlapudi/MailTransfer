import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
public class ForwardMail
{
    public static void main(String [] args) throws Exception
    {
        Properties props = new Properties();
        props.put("mail.store.protocol", "pop3");
        props.put("mail.pop3s.host", "pop.gmail.com");
        props.put("mail.pop3s.port", "995");
        props.put("mail.pop3.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");
        Session sess = Session.getDefaultInstance(props);
        try {
            Store store = sess.getStore("pop3s");
            store.connect("pop.gmail.com", "ksravya1411@gmail.com", "Sricharan@2011");
            Folder folder = store.getFolder("inbox");
            folder.open(Folder.READ_ONLY);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in));
            Message[] msgarr = folder.getMessages();
            if (msgarr.length != 0) {
                for (int i = 0, n = msgarr.length; i < n; i++) {
                    Message msgarrs = msgarr[i];
                    String from = InternetAddress.toString(msgarrs.getFrom());
                    if (from != null) {
                        System.out.println("From: " + from);
                    }
                    String msgrply = InternetAddress.toString(msgarrs
                            .getReplyTo());
                    if (msgrply != null) {
                        System.out.println("Reply the mail " + msgrply);
                    }
                    String tomsg = InternetAddress.toString(msgarrs
                            .getRecipients(Message.RecipientType.TO));
                    if (tomsg != null) {
                        System.out.println("To: " + tomsg);
                    }
                    String sub = msgarrs.getSubject();
                    if (sub != null) {
                        System.out.println("Mail Subject is: " + sub);
                    }
                    Date dsnt = msgarrs.getSentDate();
                    if (dsnt != null) {
                        System.out.println("Msg Sent: " + dsnt);
                    }
                    System.out.print("Do you want to reply [y/n] : ");
                    String str = reader.readLine();
                    if ("Y".equals(str) || "y".equals(str)) {
                        Message msgfwd = new MimeMessage(sess);
                        msgfwd.setRecipients(Message.RecipientType.TO,
                                InternetAddress.parse(from));
                        msgfwd.setSubject("Fwd: " + msgarrs.getSubject());
                        msgfwd.setFrom(new InternetAddress(tomsg));
                        MimeBodyPart msgpart = new MimeBodyPart();
                        Multipart mpart = new MimeMultipart();
                        msgpart.setContent(msgarrs, "message/rfc822");
                        mpart.addBodyPart(msgpart);
                        msgfwd.setContent(mpart);
                        msgfwd.saveChanges();
                        Transport ts = sess.getTransport("smtp");
                        try {
                            ts.connect("ramansiva57@gmail.com", "xodbizaoiqijifre");
                            ts.sendMessage(msgfwd, msgfwd.getAllRecipients());
                        } finally {
                            ts.close();
                        }
                        System.out.println("Your message is forwarded successfully");
                        folder.close(false);
                        store.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}