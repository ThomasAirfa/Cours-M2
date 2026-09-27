package fr.uge.poo.paint.ex4;

import java.awt.Graphics2D;

public sealed interface Shape permits Line, RectangularShape {
  
  public void draw(Graphics2D graphics);

  public Point center();
}
