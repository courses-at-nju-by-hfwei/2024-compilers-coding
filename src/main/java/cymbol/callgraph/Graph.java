package cymbol.callgraph;

import org.antlr.v4.runtime.misc.MultiMap;
import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.Set;

public class Graph {
  private final Set<String> nodes = new OrderedHashSet<>();
  private final MultiMap<String, String> edges = new MultiMap<>();

  public void addNode(String node) {
    nodes.add(node);
  }

  public void addEdge(String caller, String callee) {
    edges.map(caller, callee);
  }

  public String toDot() {
    StringBuilder buf = new StringBuilder();

    buf.append("digraph G {\n")
        .append("  ranksep = 0.25;\n")
        .append("  edge [arrowsize = 0.5]\n")
        .append("  node [shape = circle, fontname = \"ArialNarrow\", fontsize = 12, fixedsize = true, height = 0.45];\n");

    buf.append("  ");
    nodes.forEach(node -> buf.append(node).append("; "));
    buf.append("\n");

    edges.getPairs().forEach(edge ->
        buf.append(" ")
            .append(edge.a)
            .append(" -> ")
            .append(edge.b)
            .append(";\n"));
    buf.append("}\n");

    return buf.toString();
  }
}