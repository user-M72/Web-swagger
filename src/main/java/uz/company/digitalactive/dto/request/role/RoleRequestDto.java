package uz.company.digitalactive.dto.request.role;

public record RoleRequestDto(String name, String description) {
    public String getName() {
        return name();
    }
}
