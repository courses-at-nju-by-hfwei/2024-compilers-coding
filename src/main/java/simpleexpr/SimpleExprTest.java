package simpleexpr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SimpleExprTest {
  public static void main(String[] args) throws IOException {
    System.out.println("SimpleExprTest ...");

    InputStream is = System.in;

    String file;
    if (args.length > 0) {
      file = args[0];
      is = new FileInputStream(file);
    }

    CharStream input = CharStreams.fromStream(is);
    SimpleExprLexer lexer = new SimpleExprLexer(input);

    lexer.getAllTokens().forEach(System.out::println);
  }
}