package ag.type;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class VarsTypeListenerTest {
  InputStream is = System.in;

  @BeforeMethod
  public void setUp() throws IOException {
    is = new FileInputStream(Path.of("src/test/antlr/ag/type/int-d-f-g.txt").toFile());
  }

  @Test
  public void testVarsTypeListener() throws IOException {
    CharStream input = CharStreams.fromStream(is);
    VarsDeclLexer lexer = new VarsDeclLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    VarsDeclParser parser = new VarsDeclParser(tokens);
    ParseTree tree = parser.decl();

    ParseTreeWalker walker = new ParseTreeWalker();
    VarsTypeListener varsTypeListener = new VarsTypeListener();
    walker.walk(varsTypeListener, tree);
  }
}