package fr.uge.jee.anotations.onlineshop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Set;

@Configuration
@PropertySource("classpath:onlineshop.ressources")
public class AppConfig {

    @Bean
    StandardDelivery standardDelivery(@Value("${onlineshop.standarddelivery.delay}") int delay) {
        return new StandardDelivery(delay);
    }

    @Bean
    ReturnInsurance returnInsurance(@Value("${onlineshop.returninsurance.membersonly}")boolean bool) {
        return new ReturnInsurance(bool);
    }

    @Bean
    ThiefInsurance thiefInsurance() {
        return new ThiefInsurance();
    }

    @Bean
    OnlineShop onlineShop(@Value("${onlineshop.name}") String name, Set<Delivery> deliveryOptions, Set<Insurance> insurances) {
    return new OnlineShop(name, deliveryOptions, insurances);
    }
}
