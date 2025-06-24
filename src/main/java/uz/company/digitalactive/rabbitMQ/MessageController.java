package uz.company.digitalactive.rabbitMQ;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MessageController {
  @Autowired private PublisherService publisherService;

  //    @PostMapping("/send")   # через postman дабавить одного польлователя
  //    public ResponseEntity<?> send(@RequestBody String message) {
  //        if (message.isBlank()) {
  //            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
  //        }
  //        publisherService.sendMessage(message);
  //        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
  //    }

  @GetMapping("/Queue")
  public ResponseEntity<String> usersToQueue() {
    publisherService.sendUsersToQueue();
    return ResponseEntity.ok("User published to RabbitMQ");
  }
}
