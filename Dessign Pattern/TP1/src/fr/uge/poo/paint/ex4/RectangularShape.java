package fr.uge.poo.paint.ex4;

import java.awt.Graphics2D;

sealed interface RectangularShape extends Shape permits Rectangle, Ellipse {
  int x();

  int y();

  int width();

  int height();

  public void draw(Graphics2D graphics);

  @Override
  default Point center() {
	return new Point(x() + width() / 2, y() + height() / 2);
  }
}