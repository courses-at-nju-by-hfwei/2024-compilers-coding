package simpleexpr;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

public class SimpleExprGrammarTest {
  public static void main(String[] args) throws IOException {
    System.out.println("SimpleExprTest ...");

    InputStream is = System.in;

    String file;
    if (args.length > 0) {
      file = args[0];
      is = new FileInputStream(file);
    }

    CharStream input = CharStreams.fromStream(is);
    SimpleExprGrammarLexer lexer = new SimpleExprGrammarLexer(input);

    lexer.getAllTokens().forEach(System.out::println);
  }
}