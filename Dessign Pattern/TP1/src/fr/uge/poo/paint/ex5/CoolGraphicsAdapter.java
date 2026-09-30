package fr.uge.poo.paint.ex5;

import java.awt.Color;
import java.util.Objects;

import com.evilcorp.coolgraphics.CoolGraphics;

public final class CoolGraphicsAdapter implements Graphics {
  private final CoolGraphics area;
  
  public CoolGraphicsAdapter(String title, int width, int heigth) {
	area = new CoolGraphics(Objects.requireNonNull(title), width, heigth);
  }

  @Override
  public void clear(Color color) {
	//
  }
  
  @Override
  public void setColor(Color color) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void drawLine(int x1, int y1, int x2, int y2) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void drawRect(int x, int y, int width, int height) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void drawOval(int x, int y, int width, int height) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void waitForMouseEvents(MouseCallback callback) {
	Objects.requireNonNull(callback);
	area.waitForMouseEvents(callback::mouseClicked);
  }
}
