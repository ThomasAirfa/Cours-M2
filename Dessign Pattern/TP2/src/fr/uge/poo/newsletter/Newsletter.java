package fr.uge.poo.newsletter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import fr.uge.poo.newsletter.Mailer.MailerType;

public final class Newsletter {

  private final String name;
  private final Map<String, User> subscribers = new HashMap<>();
  private final Predicate<User> condition;
  private final Mailer mailer;

  public Newsletter(String name, Predicate<User> condition) {
	this.name = Objects.requireNonNull(name);
	this.condition = Objects.requireNonNull(condition);
	this.mailer = new EEMailerAdapter();
	super();
  }

  public Newsletter(String name, Predicate<User> condition, MailerType mailer) {
	this.name = Objects.requireNonNull(name);
	this.condition = Objects.requireNonNull(condition);
	this.mailer = switch (mailer) {
	case EEMAILER -> {
	  yield new EEMailerAdapter();
	}
	case GMAILER -> {
	  yield new GMailerAdapter();
	}
	};
	super();
  }

  // True si l'utilisateur a été ajouté, false sinon (email déjà présent)
  public boolean subscribe(User user) {
	Objects.requireNonNull(user);
	return condition.test(user) && subscribers.putIfAbsent(user.email(), user) == null;
  }

  public boolean unsubscribe(User user) {
	Objects.requireNonNull(user);
	return subscribers.remove(user.email()) != null;
  }

  public void sendMessage(String title, String content) {
	Objects.requireNonNull(title);
	Objects.requireNonNull(content);
	var header = "[" + name + "] " + title;
	mailer.send(List.copyOf(subscribers.keySet()), header, content);
  }
}
