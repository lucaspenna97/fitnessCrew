package util;

import bean.User;
import gui.ProcessResults;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {

    public MailSender () { }

    public boolean makeEmail (User user) {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("lucaspenna97@gmail.com","zonk02031997");
            }
        });
        session.setDebug(false);

        try {

            Message message = new MimeMessage(session);

            //Remetente
            message.setFrom(new InternetAddress("lucaspenna97@gmail.com"));

            //Destinatarios
            Address[] destinatarios = InternetAddress.parse(user.getEmail());
            message.setRecipients(Message.RecipientType.TO, destinatarios);

            //Assunto
            message.setSubject("Fitness Crew - Resumo Resultados");

            //Conteudo
            message.setText("Olá " + user.getNome() + "!\n" +
                    "Aqui esta um resumo dos resultados obtidos atraves do aplicativo desktop: \n" +
                    "Índice de Massa Corporal: " + ProcessResults.calculateImc(user.getPeso(), user.getAltura()) + "\n" +
                    "Grau de Acordo com o IMC: " + ProcessResults.calculateGrauImc(Integer.parseInt(ProcessResults.calculateImc(user.getPeso(), user.getAltura()))) + "\n" +
                    "Resumo: " + ProcessResults.resumoDescricao(Integer.parseInt(ProcessResults.calculateImc(user.getPeso(), user.getAltura()))) + "\n" +
                    "Taxa Metabólica Basal:" + ProcessResults.calculateTmb(Integer.parseInt(user.getPeso()), Integer.parseInt(user.getAltura()), Integer.parseInt(user.getIdade()), user.getSexo()));

            Transport.send(message);

            return true;

        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
            return false;
        }

    }

}
