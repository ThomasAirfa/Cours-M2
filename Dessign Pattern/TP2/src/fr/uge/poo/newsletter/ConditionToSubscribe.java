package fr.uge.poo.newsletter;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public final class ConditionToSubscribe {
  
  private ConditionToSubscribe() {
	
  }

  public static Predicate<User> any() {
	return _ -> true;
  }

  public static Predicate<User> nationality(User.Nationality nationality) {
	Objects.requireNonNull(nationality);
	return user -> user.nationality().equals(nationality);
  }
  
  public static Predicate<User> nationality(List<User.Nationality> nationalities) {
	Objects.requireNonNull(nationalities);
	return user -> nationalities.stream().anyMatch(nationality -> user.nationality().equals(nationality));
  }
  
  public static Predicate<User> ageAtLeast(int age) {
	return user -> user.age() >= age;
  }
  
  public static Predicate<User> emailEndWith(String suffix) {
	Objects.requireNonNull(suffix.toLowerCase());
	return user -> user.email().toLowerCase().endsWith(suffix);
  }
}
