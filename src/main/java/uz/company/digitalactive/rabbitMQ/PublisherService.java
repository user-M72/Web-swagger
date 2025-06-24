package uz.company.digitalactive.rabbitMQ;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uz.company.digitalactive.dto.LoginDto;
import uz.company.digitalactive.dto.response.LoginResponseDto;
import uz.company.digitalactive.dto.response.user.UserResponseDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublisherService {

  public final WebClient webClient;

  @Value("${exchange}")
  private String exchange;

  @Value("${key}")
  private String key;

  @Value("${app.auth.login-url}")
  private String loginUrl;

  @Value("${app.auth.user-url}")
  private String userUrl;

  @Autowired private RabbitTemplate rabbitTemplate;

  private String token;

  public LoginResponseDto getToken(LoginDto loginRequestDto) {
    return webClient
        .post()
        .uri(loginUrl)
        .bodyValue(loginRequestDto)
        .retrieve()
        .bodyToMono(LoginResponseDto.class)
        .block();
  }

  public String getTokenWebsite() {
    return getToken(new LoginDto("admin@gmail.com", "secret")).token();
  }

  public List<UserResponseDto> getUser() {
    String token = getTokenWebsite();

    return webClient
        .get()
        .uri(userUrl)
        .headers(headers -> headers.setBearerAuth(token))
        .retrieve()
        .bodyToFlux(UserResponseDto.class)
        .collectList()
        .block();
  }

  public void sendMessage(UserResponseDto message) {
    rabbitTemplate.convertAndSend(exchange, key, message);
    log.info("Message sent to Rabbit");
  }

  public void sendUsersToQueue() {
    List<UserResponseDto> users = getUser();
    for (UserResponseDto user : users) {
      sendMessage(user);
    }
    log.info("All users sent to RabbitMQ");
  }
}
