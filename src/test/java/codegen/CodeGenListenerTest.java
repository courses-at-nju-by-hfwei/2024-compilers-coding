package codegen;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.testng.annotations.Test;

public class CodeGenListenerTest {
  @Test
  public void testSuit() throws IOException {
    String PATH = "src/test/antlr/codegen/control-flow-II/in-class/";

    String[] srcFiles =
        new String[] {
          "bool",
          "bool-short-circuit",
          "bool-short-circuit-II",
          "if-bool",
          "if-else",
          "if-else-assign",
          "if-false-S1",
          "if-if-else-else",
          "if-true-if-false",
          "while",
          "while-if-else",
          "while-if-II",
        };

    for (String srcFile : srcFiles) {
      testCodeGenListener(PATH + srcFile);
    }
  }

  private void testCodeGenListener(String srcFile) throws IOException {
    InputStream is = new FileInputStream(Path.of(srcFile + ".txt").toFile());

    CharStream input = CharStreams.fromStream(is);
    ControlLexer lexer = new ControlLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    ControlParser parser = new ControlParser(tokens);
    ParseTree tree = parser.prog();

    CodeGenListener cg = new CodeGenListener(srcFile + "-ir.txt");
    ParseTreeWalker walker = new ParseTreeWalker();
    walker.walk(cg, tree);
  }
}
