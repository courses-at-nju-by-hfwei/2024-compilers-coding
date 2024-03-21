grammar ExprAssoc;

expr: '!' expr
    | <assoc = right> expr '^' expr
    | DIGIT
    ;

DIGIT : [0-9] ;
WS : [ \t\n\r]+ -> skip ;