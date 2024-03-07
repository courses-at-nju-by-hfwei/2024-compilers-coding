package dragon;

public class DragonLexer extends Lexer {
  // the last match position (beyond one)
  private int lastMatchPos = 0;

  // the longest match: position (beyond one) and token type
  int longestValidPrefixPos = 0;
  TokenType longestValidPrefixType = null;

  private final KeywordTable kwTable = new KeywordTable();

  public DragonLexer(String input) {
    super(input);
  }

  @Override
  public Token nextToken() {
    if (pos == input.length()) {
      return Token.EOF;
    }

    Token token;
    if (Character.isWhitespace(peek)) {
      token = WS();
    } else if (Character.isLetter(peek)) {
      token = ID();
    } else if (Character.isDigit(peek)) {
      token = NUMBER();
    } else if (peek == '=') {
      token = Token.EQ;
      advance();
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
    } else if (peek == '>') {
      advance();
      if (peek == '=') {
        token = Token.GE;
        advance();
      } else {
        token = Token.GT;
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
    advance();
    int state = 13;

    while (true) {
      switch (state) {
        case 13:
          longestValidPrefixPos = pos;
          longestValidPrefixType = TokenType.INT;

          if (Character.isDigit(peek)) {
            advance();
          } else if (peek == '.') {
            advance();
            state = 14;
          } else if (peek == 'E' || peek == 'e') {
            advance();
            state = 16;
          } else { // recognize an INT
            // TODO
            return backToTheLongestMatch();
          }
          break;
        case 14:
          if (Character.isDigit(peek)) {
            advance();
            state = 15;
          } else {
            return backToTheLongestMatch();
          }
          break;
        case 15:
          longestValidPrefixPos = pos;
          longestValidPrefixType = TokenType.REAL;

          if (Character.isDigit(peek)) {
            advance();
          } else if (peek == 'E' || peek == 'e') {
            advance();
            state = 16;
          } else { // recognize a REAL
            // TODO
            return backToTheLongestMatch();
          }
          break;
        case 16:
          if (peek == '+' || peek == '-') {
            advance();
            state = 17;
          } else if (Character.isDigit(peek)) {
            advance();
            state = 18;
          } else {
            return backToTheLongestMatch();
          }
          break;
        case 17:
          if (Character.isDigit(peek)) {
            advance();
            state = 18;
          } else {
            return backToTheLongestMatch();
          }
          break;
        case 18:
          longestValidPrefixPos = pos;
          longestValidPrefixType = TokenType.SCI;

          if (Character.isDigit(peek)) {
            advance();
          } else { // recognize an SCI
            return backToTheLongestMatch();
          }
          break;
        default:
          System.err.println("Unreachable");
      }
    }
  }

  private Token backToTheLongestMatch() {
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
    // add code below
    StringBuilder sb = new StringBuilder();

    do {
      sb.append(peek);
      advance();
    } while (Character.isLetterOrDigit(peek));

    Token token = this.kwTable.getKeyword(sb.toString());
    if (token == null) {
      return new Token(TokenType.ID, sb.toString());
    }

    return token;
  }

  private Token INT() {
    // add code below
    StringBuilder sb = new StringBuilder();

    do {
      sb.append(peek);
      advance();
    } while (Character.isDigit(peek));

    return new Token(TokenType.INT, sb.toString());
  }
}