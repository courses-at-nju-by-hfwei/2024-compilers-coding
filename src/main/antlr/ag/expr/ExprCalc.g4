grammar ExprCalc;

@header {
package ag.expr;
}

prog : stat+ ;

stat : expr
     | ID '=' expr
     ;

expr : l = expr op = ('*' | '/') r = expr
     | l = expr op = ('+' | '-') r = expr
     | '(' expr ')'
     | ID
     | INT
     ;

ADD : '+' ;
SUB : '-' ;
MUL : '*' ;
DIV : '/' ;

ID : [a-z] ;
INT : [0-9] ;
WS : [ \t\r\n] -> skip;