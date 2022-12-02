package com.example.swp493_g1_camms.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailSender {
    @Value("${mail.username}")
    //
    private String email;
    @Value("${mail.password}")
    //ioznkydeqqeeicvr
    private String password;
    @Value("${mail.smtp.starttls.enable}")
    private String auth;
    @Value("${mail.smtp.starttls.enable}")
    private String enable;
    @Value("${mail.smtp.host}")
    private String host;
    @Value("${mail.smtp.port}")
    private String port;

    public boolean sendMailToChangePassword(String to, String OTP,String fullName) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", enable);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setContent(message, "text/plain; charset=UTF-8");
            message.setFrom(new InternetAddress(email));
            System.out.println(email);
            System.out.println(password);
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Thiet lap lai mat khau"+"\n");
            message.setText("Xin chào, " + fullName+
                    "Ma OTP cua ban la: " + OTP+"\nCảm ơn.");

            Transport.send(message);

            System.out.println("gui toi mail thanh cong");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean sendOtpIntoEmail(String to, String OTP,String fullName) {
        //đang fixx cứng cần sửa lại
        Properties props = new Properties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", enable);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        try {
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(email, password);
                        }
                    });
            Message message = new MimeMessage(session);
            message.setContent(message, "text/plain; charset=UTF-8");
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Mã OTP ");
            message.setText("Xin chào, " + fullName+"\n****\n"+
                    "Mã OTP của bạn : " + OTP+"\nChú ý: Mã OTP này chỉ có hiệu lực trong 5 phút"+"\nCảm ơn.");

            Transport.send(message);

            System.out.println("Gui otp thanh cong");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return true;
    }
}
