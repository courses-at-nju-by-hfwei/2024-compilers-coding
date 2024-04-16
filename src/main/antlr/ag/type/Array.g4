grammar Array;

@header {
package ag.type;
}

prog : stat* EOF ;

stat : varDecl                         # VarDeclStat
     | arrDecl                         # ArrDeclStat
     | lhs = expr '=' rhs = expr ';'   # AssignStat
     ;

varDecl : basicType ID ';' ;
basicType : 'int' | 'float' ;

// OR: type ID ('[' INT ']')* ';'
arrDecl : basicType ID arrayType ';' ;
arrayType : '[' INT ']' arrayType      # NonEmptyArrayType
          |                            # EmptyArrayType
          ;

expr: primary = expr '[' subscript = expr ']'   # ArraySubscriptExpr
    | ID                                        # IdExpr
    | INT                                       # IntExpr
    ;

ID : [a-z]+ ;
INT : [0-9]+ ;
WS : [ \t\n\r]+ -> skip ;