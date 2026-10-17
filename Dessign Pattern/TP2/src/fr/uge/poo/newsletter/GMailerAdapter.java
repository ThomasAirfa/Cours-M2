package fr.uge.poo.newsletter;

import java.util.List;

import com.goodcorp.gmailer.GMailer;
import com.goodcorp.gmailer.GMailer.Mail;

public final class GMailerAdapter implements Mailer {

  private final GMailer mailer = new GMailer();

  @Override
  public void send(List<String> recipients, String subject, String content) {
	mailer.sendBulk(recipients, new Mail(subject, content));
  }
}