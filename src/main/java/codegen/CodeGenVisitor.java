package codegen;

import java.io.FileWriter;
import java.io.IOException;

public class CodeGenVisitor extends ControlBaseVisitor<String> {
  private final FileWriter fileWriter;
  private int tempCounter = 1;
  private int labelCounter = 1;

  public CodeGenVisitor(String file) throws IOException {
    fileWriter = new FileWriter(file);
  }

  @Override
  public String visitProg(ControlParser.ProgContext ctx) {
    visit(ctx.stat());

    try {
      fileWriter.close();
    } catch (IOException ioe) {
      System.err.println(ioe.getMessage());
    }

    return null;
  }

  // expr -> ID
  @Override
  public String visitIdExpr(ControlParser.IdExprContext ctx) {
    return super.visitIdExpr(ctx);
  }

  // stat -> ID = expr
  @Override
  public String visitAssignStat(ControlParser.AssignStatContext ctx) {
    return super.visitAssignStat(ctx);
  }

  // bool -> true
  @Override
  public String visitTrueExpr(ControlParser.TrueExprContext ctx) {
    return super.visitTrueExpr(ctx);
  }

  // bool -> false
  @Override
  public String visitFalseExpr(ControlParser.FalseExprContext ctx) {
    return super.visitFalseExpr(ctx);
  }

  // bool -> ! bool
  @Override
  public String visitNotExpr(ControlParser.NotExprContext ctx) {
    return super.visitNotExpr(ctx);
  }

  // bool -> lhs = bool && rhs = bool
  @Override
  public String visitAndExpr(ControlParser.AndExprContext ctx) {
    return super.visitAndExpr(ctx);
  }

  // bool -> lhs = bool || rhs = bool
  @Override
  public String visitOrExpr(ControlParser.OrExprContext ctx) {
    return super.visitOrExpr(ctx);
  }

  // bool -> lhs = exp REL rhs = exp
  @Override
  public String visitRelExpr(ControlParser.RelExprContext ctx) {
    return super.visitRelExpr(ctx);
  }

  // stat -> first = stat second = stat
  @Override
  public String visitSeqStat(ControlParser.SeqStatContext ctx) {
    return super.visitSeqStat(ctx);
  }

  // stat -> if (bool) { stat }
  @Override
  public String visitIfStat(ControlParser.IfStatContext ctx) {
    return super.visitIfStat(ctx);
  }

  // stat -> if (bool) { ifStat = stat } else { elseStat = stat}
  @Override
  public String visitIfElseStat(ControlParser.IfElseStatContext ctx) {
    return super.visitIfElseStat(ctx);
  }

  // stat -> while (bool) { stat }
  @Override
  public String visitWhileStat(ControlParser.WhileStatContext ctx) {
    return super.visitWhileStat(ctx);
  }

  // stat -> break
  @Override
  public String visitBreakStat(ControlParser.BreakStatContext ctx) {
    return super.visitBreakStat(ctx);
  }

  private void emitLabel(String label) {
    try {
      fileWriter.write(label + ":\n");
    } catch (IOException ioe) {
      System.err.println(ioe.getMessage() + " : " + label);
    }
  }

  private void emitCode(String code) {
    try {
      fileWriter.write(code + '\n');
    } catch (IOException ioe) {
      System.err.println(ioe.getMessage() + " : " + code);
    }
  }

  private String getNewTemp() {
    return "t" + tempCounter++;
  }

  private String getNewLabel(String label) {
    return label + labelCounter++;
  }
}
