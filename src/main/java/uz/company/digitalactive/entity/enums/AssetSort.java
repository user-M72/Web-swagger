package uz.company.digitalactive.entity.enums;

public enum AssetSort {
  NAME("name"),
  DESCRIPTION("description"),
  OWNER("owner"),
  ISSUER("issuer"),
  ISSUED_DATE("issuedDate"),
  EXPIRATION_DATE("expirationDate"),
  STATUS("status");

  private final String field;

  AssetSort(String field) {
    this.field = field;
  }

  public String getField() {
    return field;
  }
}
