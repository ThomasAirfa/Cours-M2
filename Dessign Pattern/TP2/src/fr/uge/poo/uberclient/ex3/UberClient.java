package fr.uge.poo.uberclient.ex3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.concurrent.ThreadLocalRandom;

public final class UberClient {
  /*
   * Quel principe SOLID n'est plus respecté par la classe UberClient ? Le single
   * responsability principle car la classe doit maintenant exporter des données.
   * 
   * Est-ce que vous voyez un gain avec votre nouvelle architecture par rapport au
   * code initial ? Quel principe SOLID a permis d'implémenter ce changement de
   * façon relativement transparente.
   * 
   * Code plus lisible + les tâches sont mieux réparti dans les différentes
   * classes, code maintenable et extendable plus facilement.
   * 
   * Le principe SOLID Open/Closed, UberClient reste fermé et expose uniquement le
   * nécessaire à UberClientInfo pour un affichage si on a besoin de rajouter un
   * affichage on modifie uniquement la classe chargé de l'affichage
   */

  private final String firstName;
  private final String lastName;
  private final long uid;
  private final List<Integer> grades;
  private final List<String> emails;
  private final List<String> phoneNumbers;

  private UberClient(String firstName, String lastName, long uid, List<Integer> grades, List<String> emails,
      List<String> phoneNumbers) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.uid = uid;
	this.grades = grades;
	this.emails = emails;
	this.phoneNumbers = phoneNumbers;
  }

  private UberClientInfo infos(ScoreComputationStrategy strategy) {
	Objects.requireNonNull(strategy);
	return new UberClientInfo(firstName, lastName, emails, () -> strategy.computeScore(grades));
  }

  public String export(ScoreComputationStrategy strategy, UberClientFormatter formatter) {
	Objects.requireNonNull(strategy);
	Objects.requireNonNull(formatter);
	return formatter.format(infos(strategy));
  }

  public final static class UberClientBuilder {
	private String firstName;
	private String lastName;
	private OptionalLong uid;
	private final List<Integer> grades = new ArrayList<>();
	private final List<String> emails = new ArrayList<>();
	private final List<String> phoneNumbers = new ArrayList<>();

	private UberClientBuilder() { // forcer le passage par with()

	}

	public UberClientBuilder firstName(String firstName) {
	  this.firstName = Objects.requireNonNull(firstName);
	  return this;
	}

	public UberClientBuilder lastName(String lastName) {
	  this.lastName = Objects.requireNonNull(lastName);
	  return this;
	}

	public UberClientBuilder uid(long uid) {
	  this.uid = OptionalLong.of(uid);
	  return this;
	}

	public UberClientBuilder randomUid() {
	  this.uid = OptionalLong.of(ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE));
	  return this;
	}

	public UberClientBuilder grade(int grade) {
	  grades.add(grade);
	  return this;
	}

	public UberClientBuilder grade(List<Integer> grades) {
	  this.grades.addAll(Objects.requireNonNull(grades));
	  return this;
	}

	public UberClientBuilder email(String email) {
	  emails.add(Objects.requireNonNull(email));
	  return this;
	}

	public UberClientBuilder email(List<String> emails) {
	  this.emails.addAll(Objects.requireNonNull(emails));
	  return this;
	}

	public UberClientBuilder phoneNumber(String phoneNumber) {
	  phoneNumbers.add(Objects.requireNonNull(phoneNumber));
	  return this;
	}

	public UberClientBuilder phoneNumber(List<String> phoneNumbers) {
	  this.phoneNumbers.addAll(Objects.requireNonNull(phoneNumbers));
	  return this;
	}

	public static UberClientBuilder with() {
	  return new UberClientBuilder();
	}

	public UberClient build() {
	  this.firstName = Objects.requireNonNull(firstName);
	  this.lastName = Objects.requireNonNull(lastName);
	  if (uid.isEmpty()) {
		throw new IllegalStateException("UID must be set with uid(...) or randomUid()");
	  }
	  if (uid.getAsLong() < 0) {
		throw new IllegalArgumentException("UID must be positive");
	  }

	  if (grades.size() == 0) {
		throw new IllegalArgumentException("A client must have at least one grade");
	  }
	  for (var grade : grades) {
		if (grade < 1 || grade > 5) {
		  throw new IllegalArgumentException("All grades must be between 1 and 5");
		}
	  }
	  if (emails.size() == 0 && phoneNumbers.size() == 0) {
		throw new IllegalArgumentException("A client must have at least an email or a phoneNumber");
	  }
	  return new UberClient(firstName, lastName, uid.getAsLong(), grades, emails, phoneNumbers);
	}
  }
}
