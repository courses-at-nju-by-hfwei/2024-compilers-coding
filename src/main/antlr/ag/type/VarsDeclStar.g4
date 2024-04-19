grammar VarsDeclStar;

@header {
package ag.type;
}

decl : type vars;

type : 'int'
     | 'float'
     ;

vars : ID (',' ID)* ;

ID : [a-z]+ ;
WS : [ \t\r\n]+ -> skip ;