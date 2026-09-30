package fr.uge.poo.paint.ex5;

import java.awt.Color;
import java.util.Objects;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public final class SimpleGraphicsAdapter implements Graphics {
  private final SimpleGraphics area;

  public SimpleGraphicsAdapter(String title, int width, int height) {
	area = new SimpleGraphics(Objects.requireNonNull(title), width, height);
  }

  @Override
  public void clear(Color color) {
	Objects.requireNonNull(color);
	area.clear(color);
  }
  
  @Override
  public void setColor(Color color) {
	area.
	
  }

  @Override
  public void drawLine(int x1, int y1, int x2, int y2) {
	area.render(graphics -> {
	  graphics.drawLine(x1, y1, x2, y2);
	});
  }

  @Override
  public void drawRect(int x, int y, int width, int height) {
	area.render(graphics -> {
	  graphics.drawRect(x, y, width, height);
	});
  }

  @Override
  public void drawOval(int x, int y, int width, int height) {
	area.render(graphics -> {
	  graphics.drawOval(x, y, width, height);
	});
  }

  @Override
  public void waitForMouseEvents(MouseCallback callback) {
	Objects.requireNonNull(callback);
	area.waitForMouseEvents(callback::mouseClicked);
  }
  
}
