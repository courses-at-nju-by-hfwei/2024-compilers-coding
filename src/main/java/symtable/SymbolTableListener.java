package symtable;

import cymbol.CymbolBaseListener;
import cymbol.CymbolParser;

public class SymbolTableListener extends CymbolBaseListener {
  private GlobalScope globalScope = null;
  private Scope currentScope = null;

  private final SymbolTableTreeGraph graph = new SymbolTableTreeGraph();

  /**
   * Create and enter a new scope
   */

  /**
   * define symbols
   */

  /**
   * use symbols
   */

  /**
   * Exit the current scope and return to its enclosing scope
   */

  public SymbolTableTreeGraph getGraph() {
    return graph;
  }

  /**
   * function call???
   */
}