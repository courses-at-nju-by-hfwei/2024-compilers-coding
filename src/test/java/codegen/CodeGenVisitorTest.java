package codegen;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class CodeGenVisitorTest {
  private InputStream is = System.in;
//  private final String PATH = "src/test/antlr/codegen/control-flow-I/in-class/";
  private final String PATH = "src/test/antlr/codegen/control-flow-I/final/";
  private String irFile;

  @Test
  public void testSuit() throws IOException {
    String[] srcFiles = new String[]{
        "bool",
        "if", "while",
        "break",
        "bool-short-circuit"
    };
    // while-if-II.txt, bool-short-circuit-II.txt

    for (String srcFile : srcFiles) {
      is = new FileInputStream(Path.of(PATH + srcFile + ".txt").toFile());
      irFile = srcFile + "-ir";

      testCodeGenVisitor();
    }
  }

  @Test
  public void testCodeGenVisitor() throws IOException {
    CharStream input = CharStreams.fromStream(is);
    ControlLexer lexer = new ControlLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    ControlParser parser = new ControlParser(tokens);
    ParseTree tree = parser.prog();

    CodeGenVisitor cg = new CodeGenVisitor(PATH + irFile + ".txt");
    cg.visit(tree);
  }
}