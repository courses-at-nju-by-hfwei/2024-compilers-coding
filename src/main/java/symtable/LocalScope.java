package symtable;

public class LocalScope extends BaseScope {
  private static int localScopeCounter = 0;

  public LocalScope(Scope enclosingScope) {
    super("LocalScope", enclosingScope);

    String localScopeName = getName() + localScopeCounter;
    setName(localScopeName);
    localScopeCounter++;
  }
}
