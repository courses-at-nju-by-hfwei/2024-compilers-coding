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

    return null;
  }

  private Token NUMBER() {
    return null;
  }

  private Token WS() {

    return Token.WS;
  }

  private Token ID() {

    return null;
  }
}