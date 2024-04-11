package symtable;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import cymbol.CymbolLexer;
import cymbol.CymbolParser;

public class SymbolTableListenerTest {
  public static final String DIR = "src/test/antlr/symtable/";

  public void testSymbolTableListener(String testcase) throws IOException {
    String in = DIR + testcase + ".txt";
    InputStream is = new FileInputStream(Path.of(in).toFile());

    CharStream input = CharStreams.fromStream(is);
    CymbolLexer lexer = new CymbolLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    CymbolParser parser = new CymbolParser(tokens);
    ParseTree tree = parser.prog();

    ParseTreeWalker walker = new ParseTreeWalker();
    SymbolTableListener symtableListener = new SymbolTableListener();
    walker.walk(symtableListener, tree);

    String out = DIR + testcase + ".dot";
    Path fileName = Path.of(out);
    Files.writeString(fileName, symtableListener.getGraph().toDot());
  }

  public static void main(String[] args) throws IOException {
    SymbolTableListenerTest test = new SymbolTableListenerTest();

    test.testSymbolTableListener("nested");

    test.testSymbolTableListener("monolithic");
    test.testSymbolTableListener("nested-func");
    test.testSymbolTableListener("nested-func-with-para");
  }
}