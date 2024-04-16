grammar PostfixExpr;

@header {
package ag.expr;
}

expr : expr '*' expr
     | expr '+' expr
     | '(' expr ')'
     | INT
     ;

INT : [0-9]+ ;
WS : [ \t\r\n]+ -> skip ;