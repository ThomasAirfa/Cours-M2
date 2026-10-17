package fr.uge.poo.newsletter;

import java.util.List;

public sealed interface Mailer permits GMailerAdapter, EEMailerAdapter {

  public enum MailerType {
	GMAILER, EEMAILER;
  }

  public void send(List<String> recipients, String subject, String content);

}
