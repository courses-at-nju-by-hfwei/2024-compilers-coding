// a left-recursive version
grammar ExprLR;

@header {
package expr;
}

prog : expr EOF ;

//expr : term ('-' term)* ;
expr : expr '-' term
     | term
     ;

// term: factor ('*' factor)* ;
term : term '*' factor
     | factor
     ;

factor : DIGIT ;

DIGIT : [0-9] ;
WS : [ \t\n\r]+ -> skip ;