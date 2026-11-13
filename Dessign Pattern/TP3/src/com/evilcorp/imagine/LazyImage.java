package com.evilcorp.imagine;

import java.util.Objects;

public class LazyImage implements Image {
  private final String url;
  private final Object lock = new Object();
  private Image image;

  public LazyImage(String url) {
	this.url = Objects.requireNonNull(url);
  }

  private Image downloadIfNecessary() {
	synchronized (lock) {
	  if (image == null) {
		image = Image.download(url);
	  }
	  return image;
	}
  }

  @Override
  public String name() {
	return downloadIfNecessary().name();
  }

  @Override
  public int size() {
	return downloadIfNecessary().size();
  }

  @Override
  public double hue() {
	return downloadIfNecessary().hue();
  }
}
