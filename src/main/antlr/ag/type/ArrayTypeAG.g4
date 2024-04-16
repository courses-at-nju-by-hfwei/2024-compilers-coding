grammar ArrayTypeAG;

@header {
package ag.type;
}

// OR: type ID ('[' INT ']')* ';'
arrDecl : basicType ID arrayType[$basicType.text]
  { System.out.println($ID.text + " : " + $arrayType.array_type); } ';' ;

arrayType[String basic_type]
    returns [String array_type]
  : '[' INT ']' arrayType[$basic_type]
     { $array_type = "(" + $INT.int + ", " + $arrayType.array_type + ")"; }
  |  { $array_type = $basic_type; }
  ;

basicType : 'int' | 'float' ;

ID : [a-z]+ ;
INT : [0-9]+ ;
WS : [ \t\n\r]+ -> skip ;