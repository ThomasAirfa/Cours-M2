package fr.uge.jee.anotations.onlineshop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        var onlineShop = applicationContext.getBean(OnlineShop.class);
        onlineShop.printDescription();
    }
}