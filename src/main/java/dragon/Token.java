package dragon;

public class Token {
  public static final Token EOF = new Token(TokenType.EOF, "EOF");
  public static final Token WS = new Token(TokenType.WS, " ");

  public static final Token IF = new Token(TokenType.IF, "if");
  public static final Token ELSE = new Token(TokenType.ELSE, "else");

  public static final Token EQ = new Token(TokenType.EQ, "=");
  public static final Token NE = new Token(TokenType.NE, "<>");
  public static final Token LT = new Token(TokenType.LT, "<");
  public static final Token LE = new Token(TokenType.LE, "<=");
  public static final Token GT = new Token(TokenType.GT, ">");
  public static final Token GE = new Token(TokenType.GE, ">=");
  public static final Token DOT = new Token(TokenType.DOT, ".");
  public static final Token POS = new Token(TokenType.POS, "+");
  public static final Token NEG = new Token(TokenType.NEG, "-");

  private final TokenType type;
  private final String text;

  public Token(TokenType type, String text) {
    this.type = type;
    this.text = text;
  }

  public String getText() {
    return this.text;
  }

  @Override
  public String toString() {
    return String.format("token {type : %s, text : %s}",
        this.type, this.text);
  }
}