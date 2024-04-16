package ag.type;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class ArrayTypeAGTest {
  InputStream is = System.in;

  @BeforeMethod
  public void setUp() throws IOException {
    is = new FileInputStream(Path.of("src/test/antlr/ag/type/a[2][3].txt").toFile());
  }
  @Test
  public void testArrayTypeAG() throws IOException {
    CharStream input = CharStreams.fromStream(is);
    ArrayTypeAGLexer lexer = new ArrayTypeAGLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    ArrayTypeAGParser parser = new ArrayTypeAGParser(tokens);
    parser.arrDecl();
  }
}