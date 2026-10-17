package fr.uge.poo.uberclient.ex3;

import java.util.List;

@FunctionalInterface
public interface ScoreComputationStrategy {
  double computeScore(List<Integer> grades);

  static ScoreComputationStrategy average() {
	// return averageOverNbGrades(Integer.MAX_VALUE);
	return grades -> grades.stream().mapToInt(Integer::valueOf).average().getAsDouble();
  }
  
  static ScoreComputationStrategy averageOverNbGrades(int maxNbGrades) {
	return grades -> grades.stream().limit(maxNbGrades).mapToInt(Integer::valueOf).average().getAsDouble();
  }
}
