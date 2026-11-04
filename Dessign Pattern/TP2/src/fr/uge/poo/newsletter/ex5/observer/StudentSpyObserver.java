package fr.uge.poo.newsletter.ex5.observer;

import java.util.List;
import java.util.Objects;

import fr.uge.poo.newsletter.ex5.Mailer;
import fr.uge.poo.newsletter.ex5.SubscriberInfo;

public class StudentSpyObserver implements NewsletterObserver {
  private static final String SPY = "spy@nsa.org";
  private static final String STUDENT_DOMAIN = "@etud.univ-eiffel.fr";

  private final Mailer mailer;

  public StudentSpyObserver(Mailer mailer) {
	this.mailer = Objects.requireNonNull(mailer);
  }

  @Override
  public void onSubscribed(String newsletterName, SubscriberInfo subscriber) {
	if (subscriber.email().toLowerCase().endsWith(STUDENT_DOMAIN)) {
	  mailer.send(List.of(SPY), "[" + newsletterName + "] student subscribed", "Newsletter " + newsletterName);
	}
  }
}
