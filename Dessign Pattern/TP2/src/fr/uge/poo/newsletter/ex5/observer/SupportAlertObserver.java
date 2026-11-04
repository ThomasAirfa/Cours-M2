package fr.uge.poo.newsletter.ex5.observer;

import java.util.List;
import java.util.Objects;

import fr.uge.poo.newsletter.ex5.Mailer;
import fr.uge.poo.newsletter.ex5.SubscriberInfo;

public class SupportAlertObserver implements NewsletterObserver {
  private static final String SUPPORT = "support@goodcorp.com";
  private final Mailer mailer;

  public SupportAlertObserver(Mailer mailer) {
	this.mailer = Objects.requireNonNull(mailer);
	super();
  }

  @Override
  public void onSubscriptionFailed(String newsletterName, SubscriberInfo user) {
	mailer.send(List.of(SUPPORT), "[" + newsletterName + "] failed subscription",
	    "Newsletter " + newsletterName + ", user " + user.name() + " is already subscribed");
  }
}
