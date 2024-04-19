package ag.expr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class PostfixExprAGTest {
  InputStream is = System.in;

  @BeforeMethod
  public void setUp() throws IOException {
    is = new FileInputStream(Path.of("src/test/antlr/ag/expr/postfix.txt").toFile());
  }

  @Test
  public void testPostfixExprAG() throws IOException {
    CharStream input = CharStreams.fromStream(is);
    PostfixExprAGLexer lexer = new PostfixExprAGLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    PostfixExprAGParser parser = new PostfixExprAGParser(tokens);
    parser.stat();
  }
}