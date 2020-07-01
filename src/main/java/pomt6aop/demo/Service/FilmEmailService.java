package pomt6aop.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pomt6aop.demo.Model.FilmApi;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class FilmEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public FilmEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailWithAttachment(FilmApi filmApi) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("***************************************************");

        helper.setSubject("Add Film: " + filmApi.getTitle());

        String messageText = "<h1> Film: " + filmApi.getTitle() + " added to Your film list</h>";
        helper.setText(messageText, true);

        javaMailSender.send(msg);

    }
}
//https://myaccount.google.com/lesssecureapps
