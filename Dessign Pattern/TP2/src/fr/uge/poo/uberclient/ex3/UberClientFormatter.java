package fr.uge.poo.uberclient.ex3;

import java.util.Arrays;
import java.util.stream.Collectors;

@FunctionalInterface
public interface UberClientFormatter {
  String format(UberClientInfo info);

  private static String maskEmail(String email) {
	return Arrays.stream(email.split("@")).map(part -> part.isEmpty() ? part : part.charAt(0) + "*") // pour éviter un
	                                                                                                 // StringIndexOutOfBoundsException
	                                                                                                 // dans le cas d'un
	                                                                                                 // mail mal formé
	                                                                                                 // comme @test.fr
	    .collect(Collectors.joining("@"));
  }

  static UberClientFormatter html() {
	return info -> String.format("<h2>%s %s (%1.2f*)</h2>", info.firstName(), info.lastName(),
	    info.averageGrade().getAsDouble());
  }

  static UberClientFormatter htmlSimple() {
	return info -> String.format("<h2>%s %s</h2>", info.firstName(), info.lastName());
  }

  static UberClientFormatter htmlWithEmails() {
	return info -> String.format("<h2>%s %s (%1.2f*) : %s</h2>", info.firstName(), info.lastName(),
	    info.averageGrade().getAsDouble(), info.emails().stream().map(UberClientFormatter::maskEmail).toList());
  }
}
