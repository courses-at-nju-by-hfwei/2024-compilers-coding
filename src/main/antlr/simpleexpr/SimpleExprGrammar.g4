grammar SimpleExprGrammar;
import SimpleExprLexerRules;

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