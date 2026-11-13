package com.evilcorp.imagine;

import java.util.Objects;

public interface Image {

  String name();
  int size();
  double hue();
  
  static Image download(String url) {
	Objects.requireNonNull(url);
	return RealImage.download(url);
  }
  
  static Image downloadLazy(String url) {
	Objects.requireNonNull(url);
	return new LazyImage(url);
  }
}
