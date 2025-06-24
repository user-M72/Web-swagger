package uz.company.digitalactive.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.company.digitalactive.dto.request.user.UserRequestDto;
import uz.company.digitalactive.dto.response.user.UserResponseDto;
import uz.company.digitalactive.entity.enums.UserSort;
import uz.company.digitalactive.service.user.UserService;

@RestController
@RequestMapping("/api/users/v1")
public class UserController {

  @Autowired private UserService userService;

  @GetMapping
  public List<UserResponseDto> get() {
    return userService.get();
  }

  @GetMapping("/{userId}")
  public UserResponseDto getById(@PathVariable("userId") UUID id) {
    return userService.getById(id);
  }

  @PostMapping
  public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userRequestDto) {
    UserResponseDto created = userService.create(userRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @PutMapping("/{userId}")
  public UserResponseDto update(
      @PathVariable("userId") UUID id, @RequestBody UserRequestDto userResponseDto) {
    return userService.update(id, userResponseDto);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> delete(@PathVariable("userId") UUID id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/paginated")
  public ResponseEntity<Page<UserResponseDto>> getPaginated(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam UserSort sortBy,
      @RequestParam(defaultValue = "asc") String direction,
      @RequestParam(required = false) String search) {
    Page<UserResponseDto> users = userService.getPaginated(page, size, sortBy, direction, search);
    return ResponseEntity.ok(users);
  }

  @GetMapping("/download/pdf")
  public void exportToPDF(HttpServletResponse response) throws IOException {
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=users.pdf");

    List<UserResponseDto> users = userService.get();

    PdfWriter writer = new PdfWriter(response.getOutputStream());
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);

    for (UserResponseDto user : users) {
      document.add(new Paragraph("Name: " + user.firstname()));
      document.add(new Paragraph("LastName: " + user.lastname()));
      document.add(new Paragraph("Email: " + user.email()));
      document.add(new Paragraph("Phone: " + user.phoneNumber()));
      document.add(new Paragraph("----------"));
    }

    document.close();
//    перед изменением или исползовании docker-compose build всегда запускаем CLEAN BUILD
  }
}
