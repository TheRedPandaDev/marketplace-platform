package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.dto.model.marketplace.OfferDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void notifyAboutSuccessfulSignUp(String userEmail) {

    }

    @Override
    public void notifyAboutPosting(String userEmail, PostingDto postingDto) {
        Context context = new Context();
        context.setVariable("posting", postingDto);

        String process = templateEngine.process("email/postingEmail", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setSubject("Your new posting is up");
            mimeMessageHelper.setText(process,true);
            mimeMessageHelper.setFrom("noreply@marketplace-platform-by-thomas.herokuapp.com", "Tom's Marketplace");
            mimeMessageHelper.setTo(userEmail);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessage);
    }

    @Override
    public void notifyAboutOffer(String userEmail, OfferDto offerDto) {

    }
}
