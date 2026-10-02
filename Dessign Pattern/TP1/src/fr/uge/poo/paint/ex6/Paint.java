package fr.uge.poo.paint.ex6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import fr.uge.poo.paint.ex6.Graphics.GraphicsColor;

public class Paint {

  public static void main(String[] args) throws IOException {
	if (args.length < 1 || args.length > 2) {
	  System.err.println("Usage : java -cp bin fr.uge.poo.paint.ex5.Paint <file> [-legacy]");
	  return;
	}

	boolean legacy = args.length == 2 && args[1].equals("-legacy");
	Graphics area;
	if (legacy) {
	  area = new SimpleGraphicsAdapter("area", 800, 600);
	} else {
	  area = new CoolGraphicsAdapter("area", 800, 600);
	}

	var path = Path.of(args[0]);
	var drawing = new Drawing();
	try (var lines = Files.lines(path)) {
	  lines.filter(line -> !line.isBlank()).map(ShapeParser::parseLine).forEach(drawing::add);
	}

	area.clear(GraphicsColor.WHITE);
	drawing.draw(area);
	area.render();
	area.waitForMouseEvents((x, y) -> {
	  drawing.selectNearest(x, y);
	  area.clear(GraphicsColor.WHITE);
	  drawing.draw(area);
	  area.render();
	});
  }
}
