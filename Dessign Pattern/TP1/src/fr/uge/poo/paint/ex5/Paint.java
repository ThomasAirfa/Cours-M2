package fr.uge.poo.paint.ex5;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public class Paint {

  public static void main(String[] args) throws IOException {
	if (args.length != 1) {
	  System.err.println("Missing file. Usage : java -cp bin fr.uge.poo.paint.ex4.Paint <file>");
	  return;
	}

	var path = Path.of(args[0]);
	var drawing = new Drawing();
	try (var lines = Files.lines(path)) {
	  lines.filter(line -> !line.isBlank()).map(ShapeParser::parseLine).forEach(drawing::add);
	}

	SimpleGraphics area = new SimpleGraphics("area", 800, 600);
	area.clear(Color.WHITE);
	area.render(drawing::draw);
	area.waitForMouseEvents((x, y) -> {
	  drawing.selectNearest(x, y);
	  area.clear(Color.WHITE);
	  area.render(drawing::draw);
	});
  }
}
