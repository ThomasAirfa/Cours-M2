package fr.uge.poo.paint.ex6;

import java.util.Objects;

import com.evilcorp.coolgraphics.CoolGraphics;
import com.evilcorp.coolgraphics.CoolGraphics.ColorPlus;

public final class CoolGraphicsAdapter implements Graphics {
  private final CoolGraphics area;
  private ColorPlus color = ColorPlus.BLACK; // default color
  
  public CoolGraphicsAdapter(String title, int width, int heigth) {
	area = new CoolGraphics(Objects.requireNonNull(title), width, heigth);
  }
  
  private ColorPlus convertGraphicsColorToColorPlus(GraphicsColor color) {
	return switch (color) {
	case WHITE -> {
	  yield ColorPlus.WHITE;
	}
	case BLACK -> {
	  yield ColorPlus.BLACK;
	}
	case ORANGE -> {
	  yield ColorPlus.ORANGE;
	}
	};
  }

  @Override
  public void clear(GraphicsColor color) {
	area.repaint(convertGraphicsColorToColorPlus(color));
  }
  
  @Override
  public void setColor(GraphicsColor color) {
	this.color = convertGraphicsColorToColorPlus(color);
  }

  @Override
  public void drawLine(int x1, int y1, int x2, int y2) {
	area.drawLine(x1, y1, x2, y2, color);
  }

  @Override
  public void drawOval(int x, int y, int width, int height) {
	area.drawEllipse(x, y, width, height, color);
  }

  @Override
  public void waitForMouseEvents(MouseCallback callback) {
	Objects.requireNonNull(callback);
	area.waitForMouseEvents(callback::mouseClicked);
  }
}
