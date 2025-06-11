package uz.company.digitalactive.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.company.digitalactive.dto.request.type.TypeRequestDto;
import uz.company.digitalactive.dto.response.type.TypeResponseDto;
import uz.company.digitalactive.service.type.TypeService;

@RestController
@RequestMapping("/api/types/v1")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping
    public List<TypeResponseDto> get() {
        return typeService.get();
    }

    @GetMapping("/{typeId}")
    public TypeResponseDto getById(@PathVariable("typeId") UUID id) {
        return typeService.getById(id);
    }

    @PostMapping
    public ResponseEntity<TypeResponseDto> create(@RequestBody TypeRequestDto typeRequestDto) {
        TypeResponseDto created = typeService.create(typeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{typeId}")
    public TypeResponseDto update(
            @PathVariable("typeId") UUID id, @RequestBody TypeRequestDto typeRequestDto) {
        return typeService.update(id, typeRequestDto);
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<Void> delete(@PathVariable("typeId") UUID id) {
        typeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
