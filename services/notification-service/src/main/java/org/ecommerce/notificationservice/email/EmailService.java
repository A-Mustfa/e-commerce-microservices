package org.ecommerce.notificationservice.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_RELATED;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendOrderEmail(
            String destinationEmail,
            String CustomerName,
            BigDecimal amount,
            Long OrderId
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());
        helper.setFrom("ahmedazab122@gmail.com");
        final String templateName = EmailTemplate.ORDER_CONFIRMATION.getTemplate();
        Map<String,Object> model = new HashMap<>();
        model.put("CustomerName", CustomerName);
        model.put("amount", amount);
        model.put("OrderId", OrderId);

        Context context = new Context();
        context.setVariables(model);
        helper.setSubject(EmailTemplate.ORDER_CONFIRMATION.getSubject());

        try{
            String htmlText = templateEngine.process(templateName, context);
            helper.setText(htmlText, true);
            helper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("Email Sent Successfully to %s", destinationEmail));
        }catch(MessagingException e){
            log.warn(String.format("Error sending email to %s", destinationEmail));
        }

    }
}
