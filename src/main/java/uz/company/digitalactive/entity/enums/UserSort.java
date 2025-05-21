package uz.company.digitalactive.entity.enums;

public enum UserSort {
  NAME("firstname"),
  LAST_NAME("lastname"),
  EMAIL("email"),
  PHONE_NUMBER("phoneNumber");

  private final String field;

  UserSort(String field) {
    this.field = field;
  }

  public String getField() {
    return field;
  }
}
