package ag.type;

import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class VarsTypeListener extends VarsDeclBaseListener {
  private final ParseTreeProperty<String> types = new ParseTreeProperty<>();

  @Override
  public void enterDecl(VarsDeclParser.DeclContext ctx) {
    types.put(ctx, ctx.type().getText());
  }

  @Override
  public void enterVarsList(VarsDeclParser.VarsListContext ctx) {
    types.put(ctx, types.get(ctx.parent));
  }

  @Override
  public void enterVarsID(VarsDeclParser.VarsIDContext ctx) {
    types.put(ctx, types.get(ctx.parent));
  }

  @Override
  public void exitIntType(VarsDeclParser.IntTypeContext ctx) {
    types.put(ctx, ctx.getText());
  }

  @Override
  public void exitFloatType(VarsDeclParser.FloatTypeContext ctx) {
    types.put(ctx, ctx.getText());
  }

  @Override
  public void exitVarsList(VarsDeclParser.VarsListContext ctx) {
    System.out.println(ctx.ID().getText() + " : " + types.get(ctx));
  }

  @Override
  public void exitVarsID(VarsDeclParser.VarsIDContext ctx) {
    System.out.println(ctx.ID().getText() + " : " + types.get(ctx));
  }
}