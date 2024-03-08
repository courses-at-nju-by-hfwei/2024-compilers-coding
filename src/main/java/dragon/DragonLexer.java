package dragon;

public class DragonLexer extends Lexer {
  private final KeywordTable kwTable = new KeywordTable();

  public DragonLexer(String input) {
    super(input);
  }

  @Override
  public Token nextToken() {
    if (pos == input.length()) {
      return Token.EOF;
    }

    Token token = null;

    return token;
  }

  private Token NUMBER() {
    int state = 13;
    advance();

    while (true) {
      switch (state) {
        case 13:
          if (Character.isDigit(peek)) {
            advance();
          } else if (peek == '.') {
            state = 14;
            advance();
          } else if (peek == 'E' || peek == 'e') {
            state = 16;
            advance();
          } else {
          }
          break;
        case 14:
          if (Character.isDigit(peek)) {
            state = 15;
            advance();
          } else {
          }
          break;
        case 15:
          if (Character.isDigit(peek)) {
            advance();
          } else if (peek == 'E' || peek == 'e') {
            state = 16;
            advance();
          } else {
          }
          break;
        case 16:
          if (peek == '+' || peek == '-') {
            state = 17;
            advance();
          } else if (Character.isDigit(peek)) {
            state = 18;
            advance();
          } else {
          }
          break;
        case 17:
          if (Character.isDigit(peek)) {
            state = 18;
            advance();
          } else {
          }
          break;
        case 18:
          if (Character.isDigit(peek)) {
            advance();
          } else {
          }
          break;
        default:
          System.err.println("Unreachable");
      }
    }
  }

  private Token WS() {

    return Token.WS;
  }

  private Token ID() {

    return null;
  }

  private Token RELOP() {

    return RELOP();
  }
}