package fr.uge.poo.newsletter.ex5;

import fr.uge.poo.newsletter.ex5.observer.SalesAlertObserver;
import fr.uge.poo.newsletter.ex5.observer.StudentSpyObserver;
import fr.uge.poo.newsletter.ex5.observer.SupportAlertObserver;
import fr.uge.poo.newsletter.ex5.observer.WelcomeMailObserver;

public final class Main {
  public static void main(String[] args) {
	var mailer = new EEMailerAdapter();
	var newsletter = new Newsletter("IGM news", ConditionToSubscribe.any(), mailer);
	newsletter.addObserver(new WelcomeMailObserver(mailer));
	newsletter.addObserver(new SalesAlertObserver(mailer));
	newsletter.addObserver(new SupportAlertObserver(mailer));
    newsletter.addObserver(new StudentSpyObserver(mailer));

	var arnaud = new User("Arnaud", "arnaud.carayol@univ-eiffel.fr", 47, User.Nationality.FRENCH);
	var arnaud2 = new User("Arnaud2", "arnaud.carayol@etud.univ-eiffel.fr", 22, User.Nationality.SPANISH);
	var youssef = new User("Youssef", "youssefbergeron@outlook.fr", 27, User.Nationality.BRITISH);

	newsletter.subscribe(arnaud);
	newsletter.subscribe(youssef);

	for (var i = 0; i < 99; i++) { // 2 + 99 = 101 inscrits : alerte sales une seule fois
	  newsletter.subscribe(new User("User" + i, "user" + i + "@mail.fr", 20, User.Nationality.FRENCH));
	}
	
	newsletter.subscribe(arnaud);
	newsletter.subscribe(arnaud2);


//	System.out.println(newsletter.subscribe(arnaud)); // True
//	System.out.println(newsletter.subscribe(youssef)); // True

	// test deux user avec le mm mail
//	System.out.println(newsletter.subscribe(arnaud2)); // False
//
//	newsletter.sendMessage("Bienvenue", "Merci de vous être inscrit !");
//	System.out.println(newsletter.unsubscribe(youssef)); // True
//	newsletter.sendMessage("Rappel", "Il ne reste que Monsieur Carayol");
//
//	var potterNewsletter = new Newsletter("Potter 4ever",
//	    ConditionToSubscribe.nationality(User.Nationality.BRITISH).and(ConditionToSubscribe.ageAtLeast(18)));
//	var javaNewsletter = new Newsletter("Java 4ever",
//	    ConditionToSubscribe.nationality(List.of(User.Nationality.FRENCH, User.Nationality.BRITISH))
//	        .and(ConditionToSubscribe.ageAtLeast(21)));
//	var javaNewsletter = new Newsletter("Java 4ever",
//	    ConditionToSubscribe.nationality(List.of(User.Nationality.FRENCH, User.Nationality.BRITISH))
//	        .and(ConditionToSubscribe.ageAtLeast(21)),
//	    new GMailerAdapter());
//	var whyMe = new Newsletter("Why me!",
//	    ConditionToSubscribe.emailEndWith("@univ-eiffel.fr").and(user -> user.age() % 2 == 0));
//
//	System.out.println(potterNewsletter.subscribe(youssef)); // True
//	System.out.println(potterNewsletter.subscribe(arnaud)); // False
//
//	System.out.println(javaNewsletter.subscribe(arnaud)); // True
//	System.out.println(javaNewsletter.subscribe(arnaud2)); // False
//	System.out.println(javaNewsletter.subscribe(youssef)); // True
//
//	potterNewsletter.sendMessage("Welcome", "You are a wizard");
//	javaNewsletter.sendMessage("Bienvenue", "J'ai un truc qui va te réveiller, voici Monsieur Forax.");
  }
}