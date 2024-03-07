package dragon;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class DragonLexerRulesTest {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(Path.of("src/main/antlr/dragon/dragon0.txt").toFile());

    CharStream input = CharStreams.fromStream(is);
    DragonLexerRules lexer = new DragonLexerRules(input);

    lexer.getAllTokens().forEach(System.out::println);
  }
}
