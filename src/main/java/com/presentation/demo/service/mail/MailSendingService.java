package com.presentation.demo.service.mail;

import com.presentation.demo.exceptions.MailSendingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class MailSendingService {

    private Logger MAIL_SERVICE_LOGGER = LoggerFactory.getLogger(MailSendingService.class);

    @Qualifier("getJavaMailSender")
    @Autowired
    private JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String sender;

    @Qualifier("emailTemplateEngine")
    @Autowired
    private TemplateEngine htmlTemplateEngine;

    public void sendSimple(String target, String title, String message) throws MailSendingException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(target);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(message);
        simpleMailMessage.setSentDate(new Date());

        try{
            javaMailSender.send(simpleMailMessage);
        }
        catch (MailException mailExc){
            MAIL_SERVICE_LOGGER.info(mailExc.getMessage());
            throw new MailSendingException(target,mailExc.getMessage());
        }
    }

//    public void sendMime(String target, String title, String contentPath, Map<String,Object> modelAttributes) throws MailSendingException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        Context htmlThymeleafContext = new Context();
//        htmlThymeleafContext.setVariables(modelAttributes);
//        try{
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
//            mimeMessageHelper.setFrom(sender);
//            mimeMessageHelper.setTo(target);
//            mimeMessageHelper.setSubject(title);
//            mimeMessageHelper.setSentDate(new Date());
//
//            String postTemplateResolveContent = htmlTemplateEngine.process(contentPath, htmlThymeleafContext);
//            mimeMessageHelper.setText(postTemplateResolveContent,true);
//
//            javaMailSender.send(mimeMessage);
//        }
//        catch (MessagingException messExc){
//            MAIL_SERVICE_LOGGER.info(messExc.getMessage());
//        }
//        catch (MailException mailExc){
//            MAIL_SERVICE_LOGGER.info(mailExc.getMessage());
//            throw new MailSendingException(target,mailExc.getMessage());
//        }
//    }

    public void sendMime(String target, String title, String content) throws MailSendingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        Context htmlThymeleafContext = new Context();
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(target);
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setSentDate(new Date());

            mimeMessage.setContent(content,"text/html");

            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException messExc){
            MAIL_SERVICE_LOGGER.info(messExc.getMessage());
        }
        catch (MailException mailExc){
            MAIL_SERVICE_LOGGER.info(mailExc.getMessage());
            throw new MailSendingException(target,mailExc.getMessage());
        }
    }
}
