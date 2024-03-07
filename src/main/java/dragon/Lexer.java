package dragon;

public abstract class Lexer {
  final String input;
  char peek;
  int pos;

  public Lexer(String input) {
    this.input = input;
    this.pos = 0;  // the next position to be scanned
    this.peek = input.charAt(pos);
  }

  public abstract Token nextToken();

  public void advance() {
    pos++;
    if (pos >= input.length()) {
      peek = Character.MIN_VALUE;
    } else {
      peek = input.charAt(pos);
    }
  }

  public void reset(int pos) {
    this.pos = pos;
    this.peek = input.charAt(pos);
  }
}