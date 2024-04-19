grammar VarsDeclAG;

@header {
package ag.type;
}

decl : type vars ;
type : ;
vars : ;
//type : 'int'
//     | 'float'
//     ;
//vars[String typeStr]
//     : vars[$typeStr] ',' ID
//            { System.out.println($ID.text + ":" + $typeStr); }
//     | ID   { System.out.println($ID.text + ":" + $typeStr); }
//     ;

ID : [a-z]+ ;
WS : [ \t\r\n]+ -> skip ;