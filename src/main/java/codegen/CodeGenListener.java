package codegen;

import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.io.FileWriter;
import java.io.IOException;

public class CodeGenListener extends ControlBaseListener {
  private final ParseTreeProperty<String> trueLabel = new ParseTreeProperty<>();
  private final ParseTreeProperty<String> falseLabel = new ParseTreeProperty<>();
  private final ParseTreeProperty<String> nextLabel = new ParseTreeProperty<>();
  private final ParseTreeProperty<String> beginLabel = new ParseTreeProperty<>();
  private final ParseTreeProperty<String> codeProperty = new ParseTreeProperty<>();

  private FileWriter fileWriter;
  private int labelCounter = 1;

  public CodeGenListener(String file) throws IOException {
    fileWriter = new FileWriter(file);
  }

  @Override
  public void enterProg(ControlParser.ProgContext ctx) {
    nextLabel.put(ctx.stat(), getNewLabel());
  }

  @Override
  public void exitProg(ControlParser.ProgContext ctx) {
    String code = String.format("%s%n%s:",
        codeProperty.get(ctx.stat()),
        nextLabel.get(ctx.stat()));

    codeProperty.put(ctx, code);

    dump(codeProperty.get(ctx));
  }

  @Override
  public void exitAssignStat(ControlParser.AssignStatContext ctx) {
    codeProperty.put(ctx, "ASSIGN");
  }

  @Override
  public void enterWhileStat(ControlParser.WhileStatContext ctx) {
    beginLabel.put(ctx, getNewLabel() + "begin");

    trueLabel.put(ctx.bool(), getNewLabel());
    falseLabel.put(ctx.bool(), nextLabel.get(ctx));
    nextLabel.put(ctx.stat(), beginLabel.get(ctx));
  }

  @Override
  public void exitWhileStat(ControlParser.WhileStatContext ctx) {
    String code = String.format("%s:%n%s%n%s:%n%s%n%s",
        beginLabel.get(ctx),
        codeProperty.get(ctx.bool()),
        trueLabel.get(ctx.bool()),
        codeProperty.get(ctx.stat()),
        "goto " + beginLabel.get(ctx));

    codeProperty.put(ctx, code);
  }

  @Override
  public void enterIfStat(ControlParser.IfStatContext ctx) {
    trueLabel.put(ctx.bool(), getNewLabel());
    falseLabel.put(ctx.bool(), nextLabel.get(ctx));
    nextLabel.put(ctx.stat(), nextLabel.get(ctx));
  }

  @Override
  public void exitIfStat(ControlParser.IfStatContext ctx) {
    String code = String.format("%s%n%s:%n%s",
        codeProperty.get(ctx.bool()),
        trueLabel.get(ctx.bool()),
        codeProperty.get(ctx.stat()));

    codeProperty.put(ctx, code);
  }

  @Override
  public void enterAndExpr(ControlParser.AndExprContext ctx) {
    trueLabel.put(ctx.lhs, getNewLabel());
    falseLabel.put(ctx.lhs, falseLabel.get(ctx));

    trueLabel.put(ctx.rhs, trueLabel.get(ctx));
    falseLabel.put(ctx.rhs, falseLabel.get(ctx));
  }

  @Override
  public void exitAndExpr(ControlParser.AndExprContext ctx) {
    String code = String.format("%s%n%s:%n%s",
        codeProperty.get(ctx.lhs),
        trueLabel.get(ctx.lhs),
        codeProperty.get(ctx.rhs));

    codeProperty.put(ctx, code);
  }

  @Override
  public void exitOrExpr(ControlParser.OrExprContext ctx) {
    String code = String.format("%s%n%s:%n%s",
        codeProperty.get(ctx.lhs),
        falseLabel.get(ctx.lhs),
        codeProperty.get(ctx.rhs));

    codeProperty.put(ctx, code);
  }

  @Override
  public void enterOrExpr(ControlParser.OrExprContext ctx) {
    trueLabel.put(ctx.lhs, trueLabel.get(ctx));
    falseLabel.put(ctx.lhs, getNewLabel());

    trueLabel.put(ctx.rhs, trueLabel.get(ctx));
    falseLabel.put(ctx.rhs, falseLabel.get(ctx));
  }

  @Override
  public void exitRelExpr(ControlParser.RelExprContext ctx) {
    String code = String.format("if %s goto %s%ngoto %s",
        ctx.getText(),
        trueLabel.get(ctx),
        falseLabel.get(ctx));

    codeProperty.put(ctx, code);
  }

  @Override
  public void exitTrueExpr(ControlParser.TrueExprContext ctx) {
    codeProperty.put(ctx, "goto " + trueLabel.get(ctx));
  }

  @Override
  public void exitFalseExpr(ControlParser.FalseExprContext ctx) {
    codeProperty.put(ctx, "goto " + falseLabel.get(ctx));
  }

  private void dump(String code) {
    try {
      fileWriter.write(code);
      fileWriter.close();
    } catch (IOException ioe) {
      System.err.println(ioe.getMessage() + " : " + code);
    }
  }

  private String getNewLabel() {
    return "L" + labelCounter++;
  }
}