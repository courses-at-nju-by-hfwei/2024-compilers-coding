grammar SimpleExpr;

@header {
package simpleexpr;
}

prog : stat* EOF ;

stat : expr ';'
     | ID '=' expr ';'
     | 'print' expr ';'
     ;

expr : expr ('*' | '/') expr
     | expr ('+' | '-') expr
     | '(' expr ')'
     | ID
     | INT
     | FLOAT
     ;

SEMI : ';' ;
ASSIGN : '=' ;
PRINT : 'print' ;
MUL : '*' ;
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;
LPAREN : '(' ;
RPAREN : ')' ;

ID : ('_' | LETTER) WORD* ;
INT : '0' | ('+' | '-')? [1-9]NUMBER* ;
FLOAT : INT '.' NUMBER*
      | '.' NUMBER+
      ;

SL_COMMENT : '//' .*? '\n' -> skip ;
SL_COMMENT2 : '//' (~'\n')* '\n' -> skip ;
DOCS_COMMENT : '/**' .*? '*/' -> skip ;
ML_COMMENT : '/*' .*? '*/' -> skip ;

WS : [ \t\r\n]+ -> skip ;

fragment LETTER : [a-zA-Z] ;
fragment NUMBER : [0-9] ;
fragment WORD : '_' | LETTER | NUMBER ;