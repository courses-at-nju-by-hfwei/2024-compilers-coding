package ag.type;

import symtable.Type;

public class ArrayType implements Type {
  // for basic type, we have "count = 0"
  // for example, "(0, int)" is "int"
  int count;
  Type subType;

  public ArrayType(int count, Type subType) {
    this.count = count;
    this.subType = subType;
  }

  @Override
  public String toString() {
    StringBuilder typeStr = new StringBuilder();
    if (count == 0) {
      return typeStr.append(subType).toString();
    }
    return typeStr.append("(")
        .append(count)
        .append(",")
        .append(subType.toString())
        .append(')')
        .toString();
  }
}