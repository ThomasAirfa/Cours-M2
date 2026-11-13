package com.evilcorp.imagine;

import java.util.Objects;

public class RealImage implements Image {
  private final String name;
  private final int size;
  private final double hue;

  private RealImage(String name, int size, double hue) {
	Objects.requireNonNull(name);
	if (size <= 0) {
	  throw new IllegalStateException();
	}
	this.name = name;
	this.size = size;
	this.hue = hue;
  }

  @Override
  public String name() {
	return name;
  }

  @Override
  public int size() {
	return size;
  }

  @Override
  public double hue() {
	return hue;
  }

  public static RealImage download(String url) {
	var parts = url.split("/");
	var name = parts[parts.length - 1];
	var size = Math.abs(name.hashCode()) % 1_000_000;
	var hue = Math.abs(name.hashCode() % 255) / 255.0;
	System.out.println("Downloading image from " + url + " will take " + size % 10 + " seconds");
	try {
	  Thread.sleep((size % 10) * 1000);
	} catch (InterruptedException e) {
	  // not a good idea but it makes things easier
	  throw new RuntimeException(e);
	}
	return new RealImage(name, size, hue);
  }

  @Override
  public boolean equals(Object o) {
	if (this == o)
	  return true;
	if (!(o instanceof RealImage))
	  return false;

	RealImage image = (RealImage) o;

	if (size != image.size)
	  return false;
	if (Double.compare(image.hue, hue) != 0)
	  return false;
	return name.equals(image.name);
  }

  @Override
  public int hashCode() {
	int result;
	long temp;
	result = name.hashCode();
	result = 31 * result + size;
	temp = Double.doubleToLongBits(hue);
	result = 31 * result + (int) (temp ^ (temp >>> 32));
	return result;
  }
}
