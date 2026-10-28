package fr.uge.jee.onlineshop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
  public static void main(String[] args) {
    // var context = new ClassPathXmlApplicationContext("config-online.xml");
    // var context = new ClassPathXmlApplicationContext("config-onlineshopv2.xml");
    var context = new ClassPathXmlApplicationContext("config-onlineshopv3.xml");

    var shop = context.getBean(OnlineShop.class);
    shop.printDescription();
  }
}