package ag.expr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExprCalcAGInteractiveTest {
  public static void main(String[] args) throws IOException {
    InputStream is = System.in;
    BufferedReader br = new BufferedReader(new InputStreamReader(is));

    String expr = br.readLine();
    int line = 1;

    ExprCalcAGParser parser = new ExprCalcAGParser(null);
    parser.setBuildParseTree(false);

    while (expr != null) {
      CharStream input = CharStreams.fromString(expr + "\n");
      ExprCalcAGLexer lexer = new ExprCalcAGLexer(input);
      lexer.setLine(line);
      lexer.setCharPositionInLine(0);

      CommonTokenStream tokens = new CommonTokenStream(lexer);
      parser.setInputStream(tokens);
      parser.stat();

      expr = br.readLine();
      line++;
    }
  }
}
