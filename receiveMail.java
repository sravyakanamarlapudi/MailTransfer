import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import com.sun.mail.pop3.POP3Store;

public class ReceiveMail{

    public static void receiveEmail(String pop3Host, String storeType,String user, String password)
    {
        try {

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message messages [] = emailFolder.getMessages();
            int i = ((messages.length)-1);

            Message message = messages(i);

            System.out.println("Email Number"+(i+1));
            System.out.println("Subject: "+message.getSubject());
            System.out.println("From: "+message.getFrom()[0]);

            }

            //close the store and folder objects
            emailFolder.close(true);
            store.close();

        } catch (NoSuchProviderException e) {
        } catch (MessagingException e) {
        }catch (Exception e){
        }
    }

    public static void main(String[] args) {

        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        String username = "ksravya1411@gmail.com";// change accordingly
        String password = "*****";// change accordingly

        check(host, mailStoreType, username, password);

    }

}
