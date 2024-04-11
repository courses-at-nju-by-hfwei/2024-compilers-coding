package symtable;

import com.google.common.base.MoreObjects;

import java.util.LinkedHashMap;
import java.util.Map;

public class BaseScope implements Scope {
  private String name;
  private final Map<String, Symbol> symbols = new LinkedHashMap<>();
  private final Scope enclosingScope;

  public BaseScope(String name, Scope enclosingScope) {
    this.name = name;
    this.enclosingScope = enclosingScope;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Scope getEnclosingScope() {
    return this.enclosingScope;
  }

  public Map<String, Symbol> getSymbols() {
    return this.symbols;
  }

  @Override
  public void define(Symbol symbol) {
    if (symbols.containsKey(symbol.getName())) {
      System.out.println("++" + symbol);
    } else {
      symbols.put(symbol.getName(), symbol);
      System.out.println("+" + symbol);
    }
  }

  @Override
  public Symbol resolve(String name) {
    Symbol symbol = symbols.get(name);
    if (symbol != null) {
      System.out.println("*" + symbol);
      return symbol;
    }

    if (enclosingScope != null) {
      return enclosingScope.resolve(name);
    }

    System.err.println("?" + name);
    return null;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", name)
        .add("symbols", symbols.values().toString())
        .toString();
  }
}