package codegen;

import org.antlr.v4.runtime.Token;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayDeque;
import java.util.Deque;

public class CodeGenVisitor extends ControlBaseVisitor<String> {
  private final Deque<String> breakLabels = new ArrayDeque<>();
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
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return null;
  }

  // stat -> break
  @Override
  public String visitBreakStat(ControlParser.BreakStatContext ctx) {
    emit("br " + breakLabels.peek());
    return null;
  }

  // stat -> while (bool) { stat }
  @Override
  public String visitWhileStat(ControlParser.WhileStatContext ctx) {
    String beginLabel = getNewLabel("begin");
    emit(beginLabel);

    String bool = visit(ctx.bool());

    String trueLabel = getNewLabel("b.true");
    String falseLabel = getNewLabel("b.false");
    emit("br " + bool + " " + trueLabel + " " + falseLabel);

    emitLabel(trueLabel);
    breakLabels.push(falseLabel);
    visit(ctx.stat());
    breakLabels.pop();
    emit("br " + beginLabel);

    emitLabel(falseLabel);

    return null;
  }

  // stat -> if (bool) { ifStat = stat } else { elseStat = stat}
  @Override
  public String visitIfElseStat(ControlParser.IfElseStatContext ctx) {
    String bool = visit(ctx.bool());

    String trueLabel = getNewLabel("b.true");
    String falseLabel = getNewLabel("b.false");
    String endLabel = getNewLabel("b.end");
    emit("br " + bool + " " + trueLabel + " " + falseLabel);

    emitLabel(trueLabel);
    visit(ctx.ifStat);
    emit("br " + endLabel);

    emitLabel(falseLabel);
    visit(ctx.elseStat);

    emitLabel(endLabel);

    return null;
  }

  // stat -> if (bool) { stat }
  @Override
  public String visitIfStat(ControlParser.IfStatContext ctx) {
    String bool = visit(ctx.bool());

    String trueLabel = getNewLabel("b.true");
    String falseLabel = getNewLabel("b.false");
    emit("br " + bool + " " + trueLabel + " " + falseLabel);

    emitLabel(trueLabel);
    visit(ctx.stat());

    emitLabel(falseLabel);

    return null;
  }

  // stat -> first = stat second = stat
  @Override
  public String visitSeqStat(ControlParser.SeqStatContext ctx) {
    visit(ctx.first);
    visit(ctx.second);
    return null;
  }

  // stat -> ID = expr
  @Override
  public String visitAssignStat(ControlParser.AssignStatContext ctx) {
    String expr = visit(ctx.expr());
    emit(ctx.ID().getText() + " = " + expr);
    return null;
  }

  // bool -> lhs = exp REL rhs = exp
  @Override
  public String visitRelExpr(ControlParser.RelExprContext ctx) {
    String lhs = visit(ctx.lhs);
    String rhs = visit(ctx.rhs);

    Token op = ctx.op;
    String cond = switch (op.getType()) {
      case ControlLexer.GT -> "sgt";  // sgt: signed greater than
      case ControlLexer.GE -> "sge";  // sge: signed greater than or equal
      case ControlLexer.EE -> "eq";
      default -> throw new IllegalArgumentException("No such cond: " + op.getText());
    };

    String temp = getNewTemp();
    emit(temp + " = " + "icmp " + cond + " " + lhs + " " + rhs);
    return temp;
  }

  // bool -> lhs = bool && rhs = bool
  @Override
  public String visitAndExpr(ControlParser.AndExprContext ctx) {
    String lhs = visit(ctx.lhs);

    String trueLabel = getNewLabel("b1.true");
    String falseLabel = getNewLabel("b1.false");
    String endLabel = getNewLabel("b1.end");
    emit("br " + lhs + " " + trueLabel + " " + falseLabel);

    emitLabel(trueLabel);
    String rhs = visit(ctx.rhs);
    String temp = getNewTemp();
    emit(temp + " = " + "AND " + lhs + " " + rhs);
    emit("br " + endLabel);

    emitLabel(falseLabel);
    emit(temp + " = " + "false");

    emitLabel(endLabel);

    return temp;
  }

  // bool -> lhs = bool || rhs = bool
  @Override
  public String visitOrExpr(ControlParser.OrExprContext ctx) {
    String lhs = visit(ctx.lhs);

    String trueLabel = getNewLabel("or.true");
    String falseLabel = getNewLabel("or.false");
    String endLabel = getNewLabel("or.end");
    emit("br " + lhs + " " + trueLabel + " " + falseLabel);

    emitLabel(falseLabel);
    String rhs = visit(ctx.rhs);
    String temp = getNewTemp();
    emit(temp + " = OR " + lhs + " " + rhs);
    emit("br " + endLabel);

    emitLabel(trueLabel);
    emit(temp + " = true");

    emitLabel(endLabel);

    return temp;
  }

  // bool -> ! bool
  @Override
  public String visitNotExpr(ControlParser.NotExprContext ctx) {
    String bool = visit(ctx.bool());
    String temp = getNewTemp();
    emit(temp + " = " + "NOT " + bool);
    return temp;
  }

  @Override
  public String visitFalseExpr(ControlParser.FalseExprContext ctx) {
    String temp = getNewTemp();
    emit(temp + " = " + ctx.getText());
    return temp;
  }

  // B -> true
  @Override
  public String visitTrueExpr(ControlParser.TrueExprContext ctx) {
    String temp = getNewTemp();
    emit(temp + " = " + ctx.getText());
    return temp;
  }

  @Override
  public String visitIdExpr(ControlParser.IdExprContext ctx) {
    String temp = getNewTemp();
    emit(temp + " = " + ctx.getText());
    return temp;
  }

  @Override
  public String visitIntExpr(ControlParser.IntExprContext ctx) {
    String temp = getNewTemp();
    emit(temp + " = " + ctx.getText());
    return temp;
  }

  private void emitLabel(String label) {
    try {
      fileWriter.write(label + ":\n");
    } catch (IOException ioe) {
      System.err.println(ioe.getMessage() + " : " + label);
    }
  }

  private void emit(String code) {
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