package fr.uge.poo.paint.ex7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import fr.uge.poo.paint.ex7.Graphics.GraphicsColor;

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

  public void draw(Graphics graphics) {
	for (var shape : shapes) {
	  graphics.setColor(shape == selected ? GraphicsColor.ORANGE : GraphicsColor.BLACK);
	  shape.draw(graphics);
	}
  }

  public WindowSize requiredWindowSize() {
	return shapes.stream().map(Shape::requiredWindowSize).reduce(WindowSize.MIN_WINDOW_SIZE, WindowSize::max);
  }
}