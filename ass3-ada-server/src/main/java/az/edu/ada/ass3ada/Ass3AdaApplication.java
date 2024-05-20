package az.edu.ada.ass3ada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy

public class Ass3AdaApplication {
    public static void main(String[] args) {
        SpringApplication.run(Ass3AdaApplication.class, args);
    }
}