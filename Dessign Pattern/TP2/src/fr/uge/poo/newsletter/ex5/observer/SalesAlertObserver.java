package fr.uge.poo.newsletter.ex5.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.uge.poo.newsletter.ex5.Mailer;
import fr.uge.poo.newsletter.ex5.SubscriberInfo;

public class SalesAlertObserver implements NewsletterObserver {
  private static final int THRESHOLD = 100;
  private static final String SALES = "sales@goodcorp.com";

  private final Mailer mailer;
  private final ArrayList<String> subscribersNames = new ArrayList<>();
  private boolean alreadyNotified;

  public SalesAlertObserver(Mailer mailer) {
	this.mailer = Objects.requireNonNull(mailer);
	super();
  }

  @Override
  public void onSubscribed(String newsletterName, SubscriberInfo user) {
	subscribersNames.add(user.name());
	if (!alreadyNotified && subscribersNames.size() > THRESHOLD) {
	  alreadyNotified = true;
	  mailer.send(List.of(SALES), "[" + newsletterName + "] more than " + THRESHOLD + " subscribers",
	      "Newsletter " + newsletterName + " subscribers: " + String.join(", ", subscribersNames));
	}
  }

  @Override
  public void onUnsubscribed(String newsletterName, SubscriberInfo user) {
	subscribersNames.remove(user.email());
  }
}
