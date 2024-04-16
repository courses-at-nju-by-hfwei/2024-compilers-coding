grammar Vars;

vars : vars ',' ID
     | ID
     ;

ID : [a-z]+ ;
WS : [ \t\r\n]+ -> skip ;