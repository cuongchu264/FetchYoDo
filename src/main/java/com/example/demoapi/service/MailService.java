package com.example.demoapi.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class MailService {

    @Value("${sendgrid.api.key}")
    private String sendgridApiKey;

    @Value("${sendgrid.sender.email}")
    private String senderEmail;

    @Value("${sendgrid.sender.name}")
    private String senderName;

    public void sendEmail(String to, String subject, String contentText) throws IOException {
        Email from = new Email(senderEmail, senderName);
        Email toEmail = new Email(to);

        String htmlContent = """
                <html>
                  <body style="font-family: Arial, sans-serif; color: #222;">
                    <h3 style="color:#0066cc;">🔔 Product Notification</h3>
                    <p>%s</p>
                    <br/>
                    <hr/>
                    <p style="font-size: 12px; color: #888;">
                      This is an automated notification from <b>FetchYoDo</b>.<br/>
                      Please do not reply to this email.
                    </p>
                  </body>
                </html>
                """.formatted(contentText.replace("\n", "<br/>"));

        Content content = new Content("text/html", htmlContent);
        Mail mail = new Mail(from, subject, toEmail, content);

        mail.personalization.get(0).addHeader("X-Entity-Ref-ID", "FetchYoDo-Product-Notification");

        SendGrid sg = new SendGrid(sendgridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            System.out.println("Status send mail: " + response.getStatusCode());
            if (response.getStatusCode() >= 400) {
                System.err.println("Error Body: " + response.getBody());
            }
        } catch (IOException ex) {
            throw ex;
        }
    }
}
