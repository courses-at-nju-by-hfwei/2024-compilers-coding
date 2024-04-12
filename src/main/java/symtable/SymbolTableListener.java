package symtable;

import cymbol.CymbolBaseListener;
import cymbol.CymbolParser;

public class SymbolTableListener extends CymbolBaseListener {
  private GlobalScope globalScope = null;
  private Scope currentScope = null;

  private final SymbolTableTreeGraph graph = new SymbolTableTreeGraph();

  /**
   * (1) Create and enter a new scope
   */
  @Override
  public void enterProg(CymbolParser.ProgContext ctx) {
    globalScope = new GlobalScope(null);
    this.currentScope = globalScope;
  }

  @Override
  public void enterBlock(CymbolParser.BlockContext ctx) {
    LocalScope localScope = new LocalScope(currentScope);

    graph.addEdge(localScope.getName(), currentScope.getName());

    currentScope = localScope;
  }

  @Override
  public void enterFunctionDecl(CymbolParser.FunctionDeclContext ctx) {
    String funcName = ctx.ID().getText();
    FunctionSymbol scope = new FunctionSymbol(funcName, currentScope);
    graph.addEdge(scope.getName(), currentScope.getName());

    currentScope.define(scope);
    this.currentScope = scope;
  }

  /**
   * (2) define symbols
   */
  @Override
  public void exitVarDecl(CymbolParser.VarDeclContext ctx) {
    String typeName = ctx.type().getText();
    Type type = (Type) this.currentScope.resolve(typeName);

    String varName = ctx.ID().getText();

    VariableSymbol varSymbol = new VariableSymbol(varName, type);
    this.currentScope.define(varSymbol);
  }

  @Override
  public void exitFormalParameter(CymbolParser.FormalParameterContext ctx) {
    String typeName = ctx.type().getText();
    Type type = (Type) this.currentScope.resolve(typeName);

    String varName = ctx.ID().getText();

    VariableSymbol varSymbol = new VariableSymbol(varName, type);
    this.currentScope.define(varSymbol);
  }

   /**
   * (3) use symbols
   */
  @Override
  public void exitId(CymbolParser.IdContext ctx) {
    String varName = ctx.ID().getText();
    this.currentScope.resolve(varName);
  }

  /**
   * (4) Exit the current scope and return to its enclosing scope
   */

  @Override
  public void exitProg(CymbolParser.ProgContext ctx) {
    graph.addNode(SymbolTableTreeGraph.toDot(currentScope));
    currentScope = this.currentScope.getEnclosingScope();
  }

  @Override
  public void exitBlock(CymbolParser.BlockContext ctx) {
    graph.addNode(SymbolTableTreeGraph.toDot(currentScope));
    currentScope = this.currentScope.getEnclosingScope();
  }

  @Override
  public void exitFunctionDecl(CymbolParser.FunctionDeclContext ctx) {
    graph.addNode(SymbolTableTreeGraph.toDot(currentScope));
    currentScope = this.currentScope.getEnclosingScope();
  }

  public SymbolTableTreeGraph getGraph() {
    return graph;
  }
}