grammar Expr;

@header {
package expr;
}

prog : expr EOF ;

expr :
  | expr '*' expr
  | expr '-' expr
  | DIGIT
  ;

DIGIT : [0-9] ;
WS : [ \t\n\r]+ -> skip ;