package fr.uge.poo.newsletter.ex5.observer;

import java.util.List;
import java.util.Objects;

import fr.uge.poo.newsletter.ex5.Mailer;
import fr.uge.poo.newsletter.ex5.SubscriberInfo;

public class WelcomeMailObserver implements NewsletterObserver {

  private final Mailer mailer;

  public WelcomeMailObserver(Mailer mailer) {
	this.mailer = Objects.requireNonNull(mailer);
	super();
  }

  @Override
  public void onSubscribed(String newsletterName, SubscriberInfo subscriberInfo) {
	mailer.send(List.of(subscriberInfo.email()), "Welcome to " + newsletterName,
	    "Hello " + subscriberInfo.name() + ", welcome to " + newsletterName + " !");
  }
}
