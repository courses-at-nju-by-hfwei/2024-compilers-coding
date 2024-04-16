package ag.type;

import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.HashMap;
import java.util.Map;

import symtable.BasicTypeSymbol;
import symtable.Type;
import symtable.VariableSymbol;

public class TypeCheckingListener extends ArrayBaseListener {
  private final Map<String, VariableSymbol> symbolTable = new HashMap<>();
  private final ParseTreeProperty<Type> arrayTypeProperty = new ParseTreeProperty<>();
  private final ParseTreeProperty<Type> basicTypeProperty = new ParseTreeProperty<>();

  /**
   * Pass the basic type from top to bottom
   */
  @Override
  public void enterArrDecl(ArrayParser.ArrDeclContext ctx) {
    String typeName = ctx.basicType().getText();
    Type basicType = new BasicTypeSymbol(typeName);
    basicTypeProperty.put(ctx, basicType);
  }

  @Override
  public void enterNonEmptyArrayType(ArrayParser.NonEmptyArrayTypeContext ctx) {
    basicTypeProperty.put(ctx, basicTypeProperty.get(ctx.parent));
  }

  @Override
  public void enterEmptyArrayType(ArrayParser.EmptyArrayTypeContext ctx) {
    basicTypeProperty.put(ctx, basicTypeProperty.get(ctx.parent));
  }

  /**
   * Below: construct the array type from bottom to top
   */

  @Override
  public void exitEmptyArrayType(ArrayParser.EmptyArrayTypeContext ctx) {
    arrayTypeProperty.put(ctx, new ArrayType(0, basicTypeProperty.get(ctx.parent)));
  }

  @Override
  public void exitNonEmptyArrayType(ArrayParser.NonEmptyArrayTypeContext ctx) {
    int dimension = Integer.parseInt(ctx.INT().getText());
    Type subArrayType = arrayTypeProperty.get(ctx.arrayType());

    Type arrayType = new ArrayType(dimension, subArrayType);
    this.arrayTypeProperty.put(ctx, arrayType);
  }

  @Override
  public void exitArrDecl(ArrayParser.ArrDeclContext ctx) {
    Type arrayType = arrayTypeProperty.get(ctx.arrayType());
    arrayTypeProperty.put(ctx, arrayType);

    String arrayName = ctx.ID().getText();
    symbolTable.put(arrayName, new VariableSymbol(arrayName, arrayType));
  }

  @Override
  public void exitArrDeclStat(ArrayParser.ArrDeclStatContext ctx) {
    System.out.println(ctx.arrDecl().ID().getText() + " : " + arrayTypeProperty.get(ctx.arrDecl()));
  }

  /**
   * Below: type reference and inference
   */
  @Override
  public void exitIdExpr(ArrayParser.IdExprContext ctx) {
    arrayTypeProperty.put(ctx, symbolTable.get(ctx.ID().getText()).getType());
  }

  @Override
  public void exitIntExpr(ArrayParser.IntExprContext ctx) {
    arrayTypeProperty.put(ctx, new BasicTypeSymbol("int"));
  }

  @Override
  public void exitVarDecl(ArrayParser.VarDeclContext ctx) {
    String varName = ctx.ID().getText();
    String typeName = ctx.basicType().getText();
    Type type = new BasicTypeSymbol(typeName);

    symbolTable.put(varName, new VariableSymbol(varName, type));
  }

  // type inference
  @Override
  public void exitArraySubscriptExpr(ArrayParser.ArraySubscriptExprContext ctx) {
    arrayTypeProperty.put(ctx,
        ((ArrayType) arrayTypeProperty.get(ctx.primary)).subType);
  }

  /**
   * Below: assign
   */
  @Override
  public void exitAssignStat(ArrayParser.AssignStatContext ctx) {
    Type lhs = arrayTypeProperty.get(ctx.lhs);
    Type rhs = arrayTypeProperty.get(ctx.rhs);
    System.out.println(lhs + " <=> " + rhs);
  }
}