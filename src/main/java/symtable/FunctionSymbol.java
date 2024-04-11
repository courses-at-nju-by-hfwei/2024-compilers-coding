package symtable;

public class FunctionSymbol extends BaseScope implements Symbol {
  public FunctionSymbol(String name, Scope enclosingScope) {
    super(name, enclosingScope);
  }
}