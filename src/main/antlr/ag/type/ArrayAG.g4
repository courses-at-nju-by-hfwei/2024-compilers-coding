grammar ArrayAG;

@header {
package ag.type;
import java.util.*;
}

@parser::members {
private Map<String, String> typeMap = new HashMap<>();
}

prog : stat* EOF ;

stat : varDecl
        { String id = $varDecl.ctx.ID().getText();
          System.out.println(id + " : " + typeMap.get(id)); }
     | arrDecl
        { String id = $arrDecl.ctx.ID().getText();
          System.out.println(id + " : " + typeMap.get(id)); }
     | lhs = expr '=' rhs = expr ';'
        {  System.out.println($lhs.type + " <=> " + $rhs.type); }
     ;

varDecl : basicType ID ';'
    { typeMap.put($ID.text, $basicType.text); };
basicType : 'int' | 'float' ;

// OR: type ID ('[' INT ']')* ';'
arrDecl : basicType ID arrayType[$basicType.text]
  { typeMap.put($ID.text, $arrayType.array_type); }
  ';' ;
arrayType[String basic_type]
    returns [String array_type]
  : '[' INT ']' arrayType[$basic_type]
     { $array_type = "(" + $INT.int + ", " + $arrayType.array_type + ")"; }
  |  { $array_type = $basic_type; }
  ;

expr returns [String type]
    : ID { String expr_type = typeMap.get($ID.text); }
      ('[' INT ']'
         {
           int start = expr_type.indexOf(',');
           int end = expr_type.lastIndexOf(')');
           expr_type = expr_type.substring(start + 1, end);
         }
      )* { $type = expr_type; }
    | ID { $type = typeMap.get($ID.text); }
    | INT { $type = "int"; }
    ;

ID : [a-z]+ ;
INT : [0-9]+ ;
WS : [ \t\n\r]+ -> skip ;