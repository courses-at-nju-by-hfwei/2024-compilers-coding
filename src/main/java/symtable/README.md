# symtable

## monolithic

- Type
- Symbol
  - VariableSymbol
  - BasicTypeSymbol
    - int
- Scope
  - BaseScope
    - GlobalScope
- DefPhaseListener
  - enterProg
  - ~~exitProg~~
  - exitVarDecl
  - exitID

## nested

- Symbol
  - FunctionSymbol
- Scope
  - BaseScope
    - LocalScope
  - FunctionSymbol
- SymbolTableListener
  - enterFunctionDecl
  - exitFunctionDecl
  - enterBlock
    - counter for uniqueness
  - exitBlock
  - exitFormalParameter
- SymbolTableTreeGraph

## Phases
- enter
- def
- use
- exit

## forward reference

## type checking