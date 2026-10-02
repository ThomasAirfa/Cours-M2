package fr.uge.poo.paint.ex5;

import java.awt.Color;
import java.util.Objects;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public final class SimpleGraphicsAdapter implements Graphics {
  private final SimpleGraphics area;
  private Color color = Color.BLACK; // default color


  public SimpleGraphicsAdapter(String title, int width, int height) {
	area = new SimpleGraphics(Objects.requireNonNull(title), width, height);
  }

  private Color convertGraphicsColorToJavaColor(GraphicsColor color) {
	return switch (color) {
	case WHITE -> {
	  yield Color.WHITE;
	}
	case BLACK -> {
	  yield Color.BLACK;
	}
	case ORANGE -> {
	  yield Color.ORANGE;
	}
	};
  }

  @Override
  public void clear(GraphicsColor color) {
	Objects.requireNonNull(color);
	area.clear(convertGraphicsColorToJavaColor(color));
  }

  @Override
  public void setColor(GraphicsColor color) {
	this.color = convertGraphicsColorToJavaColor(color);
  }

  @Override
  public void drawLine(int x1, int y1, int x2, int y2) {
	var currentColor = color;
	area.render(graphics -> {
	  graphics.setColor(currentColor);
	  graphics.drawLine(x1, y1, x2, y2);
	});
  }

//  @Override
//  public void drawRect(int x, int y, int width, int height) {
//	area.render(graphics -> {
//	  graphics.drawRect(x, y, width, height);
//	});
//  }

  @Override
  public void drawOval(int x, int y, int width, int height) {
	var currentColor = color;
	area.render(graphics -> {
	  graphics.setColor(currentColor);
	  graphics.drawOval(x, y, width, height);
	});
  }

  @Override
  public void waitForMouseEvents(MouseCallback callback) {
	Objects.requireNonNull(callback);
	area.waitForMouseEvents(callback::mouseClicked);
  }
}
