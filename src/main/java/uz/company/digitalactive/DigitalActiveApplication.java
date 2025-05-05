package uz.company.digitalactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("uz.company")
public class DigitalActiveApplication {

  public static void main(String[] args) {
    SpringApplication.run(DigitalActiveApplication.class, args);
  }
}
