grammar ArrayType;

@header {
package ag.type;
}

// OR: type ID ('[' INT ']')* ';'
arrDecl : basicType ID arrayType ';' ;
arrayType : '[' INT ']' arrayType      # NonEmptyArrayType
          |                            # EmptyArrayType
          ;

basicType : 'int' | 'float' ;

ID : [a-z]+ ;
INT : [0-9]+ ;
WS : [ \t\n\r]+ -> skip ;