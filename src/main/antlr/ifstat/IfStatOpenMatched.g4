grammar IfStatOpenMatched;

@header {
package ifstat.ifstatopenmatched;
}

prog : stat EOF ;

stat : matched_stat | open_stat ;

matched_stat : 'if' expr 'then' matched_stat 'else' matched_stat
             | expr
             ;

open_stat: 'if' expr 'then' stat
         | 'if' expr 'then' matched_stat 'else' open_stat
         ;

expr : ID ;

ID : [a-z] ;
WS : [ \t\n\r]+ -> skip ;