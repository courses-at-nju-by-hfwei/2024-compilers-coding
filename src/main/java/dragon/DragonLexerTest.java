package dragon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DragonLexerTest {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Path.of("src/main/antlr/dragon/dragon0.txt"));
    DragonLexer lexer = new DragonLexer(input);

    Token token = lexer.nextToken();

    while (token != Token.EOF) {
      if (token != Token.WS) {
        System.out.println(token);
      }
      token = lexer.nextToken();
    }
  }
}