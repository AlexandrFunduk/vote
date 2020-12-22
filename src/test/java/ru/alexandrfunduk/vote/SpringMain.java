package ru.alexandrfunduk.vote;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.alexandrfunduk.vote.model.Restaurant;
import ru.alexandrfunduk.vote.model.Role;
import ru.alexandrfunduk.vote.model.User;
import ru.alexandrfunduk.vote.repository.RestaurantRepository;
import ru.alexandrfunduk.vote.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-db.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            UserRepository repository = appCtx.getBean(UserRepository.class);
            System.out.println(">>>>>>>>>>>000");
            repository.save(new User(100, "aaa", "aaa@email.ru","12345", Role.ADMIN));
            System.out.println(">>>>>>>>>>>111");
            System.out.println(repository.get(100000));
            System.out.println(repository.get(100));

            RestaurantRepository restaurantRepository = appCtx.getBean(RestaurantRepository.class);
            System.out.println("++" + restaurantRepository.save(new Restaurant(null, "KFC")));

            System.out.println(">>" + restaurantRepository.getAll());
        }
        System.out.println(">>>>>>>>>>>222");
        LocalDateTime dateTime = LocalDateTime.of(2020,12,22,11,0,0);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!" + dateTime.toLocalTime().isBefore(LocalTime.of(11, 0, 0)));

    }
}
