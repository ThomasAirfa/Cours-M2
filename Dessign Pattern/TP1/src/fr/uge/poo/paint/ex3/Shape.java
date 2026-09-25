package fr.uge.poo.paint.ex3;

import java.awt.Graphics2D;

public sealed interface Shape permits Line, Rectangle, Ellipse {

  public static Shape parse(String line) {
	var tokens = line.split(" ");
	int x1 = Integer.parseInt(tokens[1]);
	int y1 = Integer.parseInt(tokens[2]);
	int x2 = Integer.parseInt(tokens[3]);
	int y2 = Integer.parseInt(tokens[4]);
	switch (tokens[0].toLowerCase()) {
	case "line" -> {
	  return new Line(x1, y1, x2, y2);
	}
	case "rectangle" -> {
	  return new Rectangle(x1, y1, x2, y2);
	}
	case "ellipse" -> {
	  return new Ellipse(x1, y1, x2, y2);
	}
	default -> {
	  throw new IllegalArgumentException("unknown shape : " + tokens[0]);
	}
	}
  }

  public void draw(Graphics2D graphics);
}
