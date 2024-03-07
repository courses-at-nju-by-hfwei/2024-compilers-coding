package dragon;

/**
 * Types of tokens
 * Grouped by hardness of recognition
 */
public enum TokenType {
  // Group 0
  EOF,  // end of file
  UNKNOWN,  // for error

  // Group 1
  // lookhead = 1 (LA(1))
  DOT, POS, NEG,
  IF, ELSE,
  ID,
  INT,
  WS,

  // Group 2
  // =, <>, <, <=, >, >=
  // LA(2)
  EQ, NE, LT, LE, GT, GE,

  // Group 3
  // arbitrary LA
  REAL,
  SCI,
}