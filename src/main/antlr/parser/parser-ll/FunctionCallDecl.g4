grammar FunctionCallDecl;

prog : (func_call | decl)+ EOF;

func_call : ID '(' arg ')' ;
decl : 'int' ID optional_init ';' ;

arg : 'int' ID optional_init ;
optional_init
    : '=' ID    # Init
    |           # NoInit
    ;

ID : [a-zA-Z0-9]+;
WS : [ \t\r\n]+ -> skip;




