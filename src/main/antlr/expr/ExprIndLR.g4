// an indirectly left-recursive grammar
grammar ExprIndLR;

@header {
package expr.exprindlr;
}

expr : ID ;
//expr : expo | ID ;
//expo : expr '+' expr;
//
ID : [a-z] ;