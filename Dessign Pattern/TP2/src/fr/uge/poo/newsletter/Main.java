package fr.uge.poo.newsletter;

import java.util.List;

import fr.uge.poo.newsletter.Mailer.MailerType;

public final class Main {
  public static void main(String[] args) {
	var newsletter = new Newsletter("IGM news", ConditionToSubscribe.any());
	var arnaud = new User("Arnaud", "arnaud.carayol@univ-eiffel.fr", 47, User.Nationality.FRENCH);
	var arnaud2 = new User("Arnaud2", "arnaud.carayol@univ-eiffel.fr", 22, User.Nationality.SPANISH);
	var youssef = new User("Youssef", "youssefbergeron@outlook.fr", 27, User.Nationality.BRITISH);

	System.out.println(newsletter.subscribe(arnaud)); // True
	System.out.println(newsletter.subscribe(youssef)); // True

	// test deux user avec le mm mail
	System.out.println(newsletter.subscribe(arnaud2)); // False

	newsletter.sendMessage("Bienvenue", "Merci de vous être inscrit !");
	System.out.println(newsletter.unsubscribe(youssef)); // True
	newsletter.sendMessage("Rappel", "Il ne reste que Monsieur Carayol");

	var potterNewsletter = new Newsletter("Potter 4ever",
	    ConditionToSubscribe.nationality(User.Nationality.BRITISH).and(ConditionToSubscribe.ageAtLeast(18)));
//	var javaNewsletter = new Newsletter("Java 4ever",
//	    ConditionToSubscribe.nationality(List.of(User.Nationality.FRENCH, User.Nationality.BRITISH))
//	        .and(ConditionToSubscribe.ageAtLeast(21)));
	var javaNewsletter = new Newsletter("Java 4ever",
	    ConditionToSubscribe.nationality(List.of(User.Nationality.FRENCH, User.Nationality.BRITISH))
	        .and(ConditionToSubscribe.ageAtLeast(21)), MailerType.GMAILER);
	var whyMe = new Newsletter("Why me!",
	    ConditionToSubscribe.emailEndWith("@univ-eiffel.fr").and(user -> user.age() % 2 == 0));
	
	System.out.println(potterNewsletter.subscribe(youssef)); // True
	System.out.println(potterNewsletter.subscribe(arnaud)); // False
	
	System.out.println(javaNewsletter.subscribe(arnaud)); // True
	System.out.println(javaNewsletter.subscribe(arnaud2)); // False
	System.out.println(javaNewsletter.subscribe(youssef)); // True
	
	potterNewsletter.sendMessage("Welcome", "You are a wizard");
	javaNewsletter.sendMessage("Bienvenue", "J'ai un truc qui va te réveiller, voici Monsieur Forax.");
  }
}