grammar VarsDeclStarAG;

@header {
package ag.type;
}

decl : type vars[$type.text] ;
type : 'int'
     | 'float'
     ;

vars[String typeStr]
    : ID { System.out.println($ID.text + " : " + $typeStr); }
      (',' ID { System.out.println($ID.text + " : " + $typeStr); })* ;

ID : [a-z]+ ;
WS : [ \t\r\n]+ -> skip ;