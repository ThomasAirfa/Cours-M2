package fr.uge.poo.newsletter.ex5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import fr.uge.poo.newsletter.ex5.observer.NewsletterObserver;

public final class Newsletter {
  private final String name;
  private final Map<String, User> subscribers = new HashMap<>();
  private final Predicate<User> condition;
  private final Mailer mailer;
  private final ArrayList<NewsletterObserver> observers = new ArrayList<>();

  public Newsletter(String name, Predicate<User> condition) {
	Objects.requireNonNull(name);
	Objects.requireNonNull(condition);
	this(name, condition, new EEMailerAdapter());
  }

  public Newsletter(String name, Predicate<User> condition, Mailer mailer) {
	this.name = Objects.requireNonNull(name);
	this.condition = Objects.requireNonNull(condition);
	this.mailer = Objects.requireNonNull(mailer);
	super();
  }

  // True si l'utilisateur a été ajouté, false sinon (email déjà présent)
  public boolean subscribe(User user) {
	Objects.requireNonNull(user);
	if (condition.test(user) && subscribers.putIfAbsent(user.email(), user) == null) {
	  observers.forEach(observer -> observer.onSubscribed(name, new SubscriberInfo(user.name(), user.email())));
	  return true;
	}
	observers.forEach(l -> l.onSubscriptionFailed(name, new SubscriberInfo(user.name(), user.email())));  // email déjà inscrit
	return false;
  }

  public boolean unsubscribe(User user) {
	Objects.requireNonNull(user);
	return subscribers.remove(user.email()) != null;
  }

  public void sendMessage(String title, String content) {
	Objects.requireNonNull(title);
	Objects.requireNonNull(content);
	var header = buildHeader(title);
	mailer.send(List.copyOf(subscribers.keySet()), header, content);
  }
  
  public boolean addObserver(NewsletterObserver observer) {
	return observers.add(Objects.requireNonNull(observer));
  }
  
  private String buildHeader(String title) {
	return "[" + name + "] " + title;
  }
}
