package Email;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MailUtilGmail {

    public static void sendMail(String to, String from, String subject, String body, boolean bodyIsHTML)
            throws MessagingException {

        // 1 - get a mail session
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.port", "465");     //465    587
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtps.starttls.enable", "false");    //mail.smtps.quitwait
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
 

        // 2 - create a message
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        if (bodyIsHTML) {
            message.setContent(body, "text/html");
        } else {
            message.setText(body);
        }
        // 3 - address the message
        Address fromAddress = new InternetAddress(from);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // 4 - send the message
        Transport transport = session.getTransport("smtps");
        transport.connect("smtp.gmail.com", "thanhndt2004@gmail.com", "dyrg maii wdnf uocp");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
      //  Transport.send (message);               afug nlsz uatc bezh//  dyrg maii wdnf uocp
    }                                      
}
