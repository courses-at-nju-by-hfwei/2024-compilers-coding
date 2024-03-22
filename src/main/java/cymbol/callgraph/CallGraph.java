package cymbol.callgraph;

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

public class CallGraph {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(Path.of("src/main/antlr/cymbol/cymbol-functioncall.txt").toFile());
    CharStream cs = CharStreams.fromStream(is);
    CymbolLexer lexer = new CymbolLexer(cs);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    CymbolParser parser = new CymbolParser(tokens);
    ParseTree tree = parser.prog();

    ParseTreeWalker walker = new ParseTreeWalker();
    FunctionCallGraphListener fc = new FunctionCallGraphListener();
    walker.walk(fc, tree);

    Path fileName = Path.of("src/main/antlr/cymbol/functioncall.dot");
    Files.writeString(fileName, fc.getGraph().toDot());
  }
}
