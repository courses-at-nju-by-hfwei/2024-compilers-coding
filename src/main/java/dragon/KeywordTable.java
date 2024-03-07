package dragon;

import java.util.HashMap;
import java.util.Map;

public class KeywordTable {
  private final Map<String, Token> keywords = new HashMap<>();

  public KeywordTable() {
    this.reserve(Token.IF);
    this.reserve(Token.ELSE);
  }

  public Token getKeyword(String text) {
    return this.keywords.get(text);
  }

  private void reserve(Token token) {
    keywords.put(token.getText(), token);
  }
}