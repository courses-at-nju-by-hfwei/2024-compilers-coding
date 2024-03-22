package cymbol.callgraph;

import cymbol.CymbolBaseListener;
import cymbol.CymbolParser;

public class FunctionCallGraphListener extends CymbolBaseListener {
  private Graph graph = new Graph();
  public Graph getGraph() {
    return graph;
  }

  private String currentFunction;

  @Override
  public void enterFunctionDecl(CymbolParser.FunctionDeclContext ctx) {
    currentFunction = ctx.ID().getText();
    graph.addNode(currentFunction);
  }

  @Override
  public void enterCall(CymbolParser.CallContext ctx) {
    String callee = ctx.ID().getText();
    graph.addEdge(currentFunction, callee);
  }
}
