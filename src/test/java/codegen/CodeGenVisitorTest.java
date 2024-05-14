package codegen;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.testng.annotations.Test;

public class CodeGenVisitorTest {
  @Test
  public void testSuitI() throws IOException {
    String PATHI = "src/test/antlr/codegen/control-flow-I/in-class/";
    String[] srcFiles =
        new String[] {
          "assign",
          "bool",
          "if",
          "while",
          "break",
          "bool-short-circuit",
          "while-if-II",
          "bool-short-circuit-II",
        };

    for (String srcFile : srcFiles) {
      testCodeGenVisitor(PATHI + srcFile);
    }
  }

  @Test
  public void testSuitII() throws IOException {
    String PATHII = "src/test/antlr/codegen/control-flow-II/final/";
    String[] srcFiles = new String[] {"while-if-II", "bool-short-circuit-II", "if-false-S1"};

    for (String srcFile : srcFiles) {
      testCodeGenVisitor(PATHII + srcFile);
    }
  }

  private void testCodeGenVisitor(String srcFile) throws IOException {
    InputStream is = new FileInputStream(Path.of(srcFile + ".txt").toFile());

    CharStream input = CharStreams.fromStream(is);
    ControlLexer lexer = new ControlLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    ControlParser parser = new ControlParser(tokens);
    ParseTree tree = parser.prog();

    CodeGenVisitor cg = new CodeGenVisitor(srcFile + "-ir.txt");
    cg.visit(tree);
  }
}
