package fr.uge.poo.newsletter;

import java.util.List;

import com.evilcorp.eemailer.EEMailer;
import com.evilcorp.eemailer.EEMailer.Mail;

public final class EEMailerAdapter implements Mailer {

  private final EEMailer mailer = new EEMailer();

  @Override
  public void send(List<String> recipients, String subject, String content) {
	recipients.stream().forEach(recipient -> mailer.send(new Mail(recipient, subject, content)));
  }
}