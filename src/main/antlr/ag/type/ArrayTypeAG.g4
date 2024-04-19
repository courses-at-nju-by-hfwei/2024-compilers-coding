grammar ArrayTypeAG;

@header {
package ag.type;
}

// OR: type ID ('[' INT ']')* ';'
arrDecl : basicType ID arrayType[$basicType.text]
            { System.out.println($ID.text + " : " + $arrayType.type); } ';' ;

arrayType[String basic_type]
    returns [String type]
          : '[' INT ']' arrayType[$basic_type]
            { $type = "(" + $INT.text + ", " + $arrayType.type + ")"; }
          | { $type = $basic_type; }
          ;

basicType : 'int' | 'float' ;

ID : [a-z]+ ;
INT : [0-9]+ ;
WS : [ \t\n\r]+ -> skip ;
