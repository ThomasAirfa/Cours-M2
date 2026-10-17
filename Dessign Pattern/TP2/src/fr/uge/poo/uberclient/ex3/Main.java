package fr.uge.poo.uberclient.ex3;

import java.util.List;

import fr.uge.poo.uberclient.ex3.UberClient.UberClientBuilder;

public final class Main {

  public static void main(String[] args) {
//	var arnaud = new UberClient("Arnaud", "Carayol", 1, List.of(1, 2, 5, 2, 5, 1, 1, 1),
//	    List.of("arnaud.carayol@univ-eiffel.fr", "arnaud.carayol@u-pem.fr"), List.of("07070707070707"));
//	var youssef = new UberClient("Youssef", "Bergeron", List.of(5), List.of("youssefbergeron@outlook.fr"), List.of());

	var arnaud = UberClientBuilder.with().firstName("Arnaud").lastName("Carayol").uid(1)
	    .grade(List.of(1, 2, 5, 2, 5, 1, 1, 1))
	    .email(List.of("arnaud.carayol@univ-eiffel.fr", "arnaud.carayol@u-pem.fr"))
	    .phoneNumber(List.of("07070707070707")).build();
	
	var youssef = UberClientBuilder.with().firstName("Youssef").lastName("Bergeron").grade(List.of(5))
	    .email(List.of("youssefbergeron@outlook.fr")).randomUid().build();
	
	System.out.println(arnaud.export(ScoreComputationStrategy.average(), UberClientFormatter.html()));
	System.out.println(arnaud.export(ScoreComputationStrategy.average(), UberClientFormatter.htmlSimple()));
	System.out.println(arnaud.export(ScoreComputationStrategy.averageOverNbGrades(7), UberClientFormatter.html()));
	System.out.println(arnaud.export(ScoreComputationStrategy.average(), UberClientFormatter.htmlWithEmails()));
	System.out.println(arnaud.export(ScoreComputationStrategy.averageOverNbGrades(5), UberClientFormatter.htmlWithEmails()));
  }
}
