package fr.uge.poo.paint.ex6;

import java.util.stream.Stream;

final class ShapeParser {
  private ShapeParser() {
  }

  public static Shape parseLine(String line) {
	var tokens = line.split(" ");
	var command = tokens[0].toLowerCase();
	var arguments = Stream.of(tokens).skip(1).mapToInt(Integer::parseInt).toArray();
	return ShapeFactory.create(command, arguments);
  }
}
