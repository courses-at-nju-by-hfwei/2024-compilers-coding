grammar IfStatFactor;

@header {
package ifstat.ifstatefactor;
}

stat : 'if' expr 'then' stat stat_prime
     | expr
     ;

stat_prime : 'else' stat
     |
     ;

expr : ID ;

ID : [a-z] ;
WS : [ \t\n\r]+ -> skip ;