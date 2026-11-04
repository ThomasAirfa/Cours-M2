package fr.uge.poo.newsletter.ex5;

import java.util.List;

public sealed interface Mailer permits GMailerAdapter, EEMailerAdapter {
  public void send(List<String> recipients, String subject, String content);
}
