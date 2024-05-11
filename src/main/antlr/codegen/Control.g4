grammar Control;

@header {
package codegen;
}

prog : stat ;

stat : ID '=' expr ';'                      # AssignStat
     | 'if' '(' bool ')' '{' ifStat = stat '}' 'else' '{' elseStat = stat '}' # IfElseStat
     | 'if' '(' bool ')' '{' stat '}'       # IfStat
     | 'while' '(' bool ')' '{' stat '}'    # WhileStat
     | 'break' ';'                          # BreakStat
     | first = stat second = stat           # SeqStat
     ;

bool : lhs = expr op = ('>' | '>=' | '==') rhs = expr   # RelExpr
     | op = '!' bool                                    # NotExpr
     | lhs = bool op = '&&' rhs = bool                  # AndExpr
     | lhs = bool op = '||' rhs = bool                  # OrExpr
     | 'true'                                           # TrueExpr
     | 'false'                                          # FalseExpr
     ;

/**
 * Section 6.4.1: grammar for expressions
 *   (array decl and references are not included yet)
 */

expr : '-' expr         # NegExpr
     | expr '*' expr    # MultExpr
     | expr '+' expr    # ADDExpr
     | '(' expr ')'     # ParenExpr
     | ID               # IdExpr
     | INT              # IntExpr
     ;

GT : '>' ;
GE : '>=' ;
EE : '==' ;

ID : [a-z] ;
INT : [0-9] ;
WS : [ \t\r\n]+ -> skip;