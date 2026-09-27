package fr.uge.poo.paint.ex4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class Drawing {
  private final List<Shape> shapes = new ArrayList<>();
  private Shape selected;

  public void add(Shape shape) {
	Objects.requireNonNull(shape);
	shapes.add(shape);
  }

  public void selectNearest(int x, int y) {
	selected = shapes.stream().min(Comparator.comparingLong(shape -> squaredDistance(shape.center(), x, y)))
	    .orElse(null);
  }

  private static long squaredDistance(Point p, int x, int y) {
	long xDistance = p.x() - x;
	long yDistance = p.y() - y;
	return xDistance * xDistance + yDistance * yDistance;
  }

  public void draw(Graphics2D graphics) {
	for (var shape : shapes) {
	  graphics.setColor(shape == selected ? Color.ORANGE : Color.BLACK);
	  shape.draw(graphics);
	}
  }
}
