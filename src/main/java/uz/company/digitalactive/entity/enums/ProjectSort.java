package uz.company.digitalactive.entity.enums;

public enum ProjectSort {
  NAME("name"),
  SHORT_NAME("shortName"),
  DESCRIPTION("description");

  private final String field;

  ProjectSort(String field) {
    this.field = field;
  }

  public String getField() {
    return field;
  }
}
