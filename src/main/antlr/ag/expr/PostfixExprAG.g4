grammar PostfixExprAG;

@header {
package ag.expr;
}

stat : expr { System.out.println($expr.postfix); }
     ;

expr returns [String postfix]
  : l = expr op = '*' r = expr { $postfix = $l.postfix + $r.postfix + $op.text; }
  | l = expr op = '+' r = expr { $postfix = $l.postfix + $r.postfix + $op.text; }
  | '(' expr ')'               { $postfix = $expr.postfix; }
  | INT                        { $postfix = $INT.text; }
  ;

INT : [0-9]+ ;
WS : [ \t\r\n]+ -> skip ;