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
          } else if (peek == '.') {
          } else if (peek == 'E' || peek == 'e') {
          } else {
          }
          break;
        case 14:
          if (Character.isDigit(peek)) {
          } else {
          }
          break;
        case 15:
          if (Character.isDigit(peek)) {
          } else if (peek == 'E' || peek == 'e') {
          } else { // recognize a REAL
          }
          break;
        case 16:
          if (peek == '+' || peek == '-') {
          } else if (Character.isDigit(peek)) {
          } else {
          }
          break;
        case 17:
          if (Character.isDigit(peek)) {
          } else {
          }
          break;
        case 18:
          if (Character.isDigit(peek)) {
          } else {
          }
          break;
        default:
          System.err.println("Unreachable!");
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