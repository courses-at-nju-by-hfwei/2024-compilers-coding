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

public class SignedBinaryNumberAGTest {
  InputStream is = System.in;

  @BeforeMethod
  public void setUp() throws IOException {
    is = new FileInputStream(Path.of("src/test/antlr/ag/expr/binary.txt").toFile());
  }

  @Test
  public void testPostfixExprAG() throws IOException {
    CharStream input = CharStreams.fromStream(is);
    SignedBinaryNumberAGLexer lexer = new SignedBinaryNumberAGLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    SignedBinaryNumberAGParser parser = new SignedBinaryNumberAGParser(tokens);
    parser.number();
  }
}