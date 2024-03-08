package dragon;

public class DragonLexer extends Lexer {
  private int lastMatchPos = 0;
  private int longestValidPrefixPos = 0;
  private TokenType longestValidPrefixType = null;

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
    if (Character.isWhitespace(peek)) {
      token = WS();
    } else if (Character.isLetter(peek)) {
      token = ID();
    } else if (Character.isDigit(peek)) {
      token = NUMBER();
    } else if (peek == '=') {
      token = Token.EQ;
      advance();
    } else if (peek == '>') {
      advance();
      if (peek == '=') {
        token = Token.GE;
        advance();
      } else {
        token = Token.GT;
      }
    } else if (peek == '<') {
      advance();
      if (peek == '=') {
        token = Token.LE;
        advance();
      } else if (peek == '>') {
        token = Token.NE;
        advance();
      } else {
        token = Token.LT;
      }
    } else if (peek == '.') {
      token = Token.DOT;
      advance();
    } else if (peek == '+') {
      token = Token.POS;
      advance();
    } else if (peek == '-') {
      token = Token.NEG;
      advance();
    } else {
      token = new Token(TokenType.UNKNOWN, Character.toString(peek));
      advance();
    }

    this.lastMatchPos = pos;
    return token;
  }

  private Token NUMBER() {
    int state = 13;
    advance();

    while (true) {
      switch (state) {
        case 13:
          longestValidPrefixPos = pos;
          longestValidPrefixType = TokenType.INT;

          if (Character.isDigit(peek)) {
            advance();
          } else if (peek == '.') {
            state = 14;
            advance();
          } else if (peek == 'E' || peek == 'e') {
            state = 16;
            advance();
          } else {
            return backToLongestMatch();
          }
          break;
        case 14:
          if (Character.isDigit(peek)) {
            state = 15;
            advance();
          } else {
            return backToLongestMatch();
          }
          break;
        case 15:
          longestValidPrefixPos = pos;
          longestValidPrefixType = TokenType.REAL;

          if (Character.isDigit(peek)) {
            advance();
          } else if (peek == 'E' || peek == 'e') {
            state = 16;
            advance();
          } else {
            return backToLongestMatch();
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
            return backToLongestMatch();
          }
          break;
        case 17:
          if (Character.isDigit(peek)) {
            state = 18;
            advance();
          } else {
            return backToLongestMatch();
          }
          break;
        case 18:
          longestValidPrefixPos = pos;
          longestValidPrefixType = TokenType.SCI;

          if (Character.isDigit(peek)) {
            advance();
          } else {
            return backToLongestMatch();
          }
          break;
        default:
          System.err.println("Unreachable");
      }
    }
  }

  private Token backToLongestMatch() {
    Token token = new Token(longestValidPrefixType,
        input.substring(lastMatchPos, longestValidPrefixPos));

    System.out.println(lastMatchPos + ":" + (longestValidPrefixPos - 1));

    if (longestValidPrefixPos < input.length()) {
      this.reset(longestValidPrefixPos);
    }

    return token;
  }

  private Token WS() {
    while (Character.isWhitespace(peek)) {
      advance();
    }

    return Token.WS;
  }

  private Token ID() {
    StringBuilder sb = new StringBuilder();

    do {
      sb.append(peek);
      advance();
    } while (Character.isLetterOrDigit(peek));

    Token token = kwTable.getKeyword(sb.toString());
    if (token == null) {
      return new Token(TokenType.ID, sb.toString());
    }
    return token;
  }

  private Token INT() {

    return null;
  }

  private Token RELOP() {

    return RELOP();
  }
}