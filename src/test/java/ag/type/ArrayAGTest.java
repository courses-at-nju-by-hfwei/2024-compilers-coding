package ag.type;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class ArrayAGTest {
  InputStream is = System.in;

  @BeforeMethod
  public void setUp() throws IOException {
    is = new FileInputStream(Path.of("src/test/antlr/ag/type/array.txt").toFile());
  }
  @Test
  public void testArrayAG() throws IOException {
    CharStream input = CharStreams.fromStream(is);
    ArrayAGLexer lexer = new ArrayAGLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    ArrayAGParser parser = new ArrayAGParser(tokens);
    parser.prog();
  }
}