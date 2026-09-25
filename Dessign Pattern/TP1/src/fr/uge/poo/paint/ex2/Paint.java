package fr.uge.poo.paint.ex2;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public class Paint {

  public static void main(String[] args) throws IOException {
	if (args.length != 1) {
	  System.err.println("Missing file. Usage : java -cp bin fr.uge.poo.paint.ex2.Paint <file>");
	  return;
	}

	var path = Path.of(args[0]);
	var linesArray = new ArrayList<Line>();
	try (var lines = Files.lines(path)) {
	  lines.filter(line -> !line.isBlank()).forEach(line -> linesArray.add(Line.parse(line)));
	}

	SimpleGraphics area = new SimpleGraphics("area", 800, 600);
	area.clear(Color.WHITE);
	area.render(graphics -> {
	  graphics.setColor(Color.black);
	  linesArray.forEach(line -> line.draw(graphics));
	});
  }
}
