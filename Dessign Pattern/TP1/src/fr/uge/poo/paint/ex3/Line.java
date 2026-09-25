package fr.uge.poo.paint.ex3;

import java.awt.Graphics2D;
import java.util.Objects;

public record Line(int x1, int y1, int x2, int y2) implements Shape {

  public static Line parse(String text) {
	Objects.requireNonNull(text);
	var tokens = text.split(" ");
	int x1 = Integer.parseInt(tokens[1]);
	int y1 = Integer.parseInt(tokens[2]);
	int x2 = Integer.parseInt(tokens[3]);
	int y2 = Integer.parseInt(tokens[4]);
	return new Line(x1, y1, x2, y2);
  }
  
  @Override
  public void draw(Graphics2D graphics) {
	graphics.drawLine(x1, y1, x2, y2);
  }
}
