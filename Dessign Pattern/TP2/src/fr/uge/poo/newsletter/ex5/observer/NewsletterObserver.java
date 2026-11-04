package fr.uge.poo.newsletter.ex5.observer;

import fr.uge.poo.newsletter.ex5.SubscriberInfo;

public interface NewsletterObserver {
  default void onSubscribed(String newsletterName, SubscriberInfo user) {
  }

  default void onSubscriptionFailed(String newsletterName, SubscriberInfo user) {
  }

  default void onUnsubscribed(String newsletterName, SubscriberInfo user) {
  }
}
