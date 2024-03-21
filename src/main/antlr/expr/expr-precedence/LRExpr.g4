grammar LRExpr;

@header {
package expr.precedence;
}

stat : expr ';' EOF;

expr : expr '*' expr
     | expr '+' expr
     | INT
     | ID
     ;

ID : LETTER (LETTER | [0-9])* ;
fragment LETTER : [a-zA-Z] ;

INT : '0' | ([1-9] [0-9]*) ;

WS  : [ \t\n\r]+ -> skip ;