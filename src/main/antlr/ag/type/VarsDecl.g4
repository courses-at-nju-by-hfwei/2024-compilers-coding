grammar VarsDecl;

@header {
package ag.type;
}

decl : type vars ;
type : 'int'        # IntType
     | 'float'      # FloatType
     ;
vars : vars ',' ID  # VarsList
     | ID           # VarsID
     ;

ID : [a-z]+ ;
WS : [ \t\r\n]+ -> skip ;