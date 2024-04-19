grammar ArrayAG;

@header {
package ag.type;
import java.util.*;
}

@parser::members {
private Map<String, String> symbols = new HashMap<>();
}

prog : stat* EOF ;

stat : varDecl
        { String id = $varDecl.ctx.ID().getText();
          System.out.println(id + " : " + symbols.get(id));
        }
     | arrDecl
        { String id = $arrDecl.ctx.ID().getText();
          System.out.println(id + " : " + symbols.get(id));
        }
     | lhs = expr '=' rhs = expr ';'
        { System.out.println($lhs.type + " <=> " + $rhs.type); }
     ;

varDecl : basicType ID ';' { symbols.put($ID.text, $basicType.text); } ;
basicType : 'int' | 'float' ;

arrDecl : basicType ID arrayType[$basicType.text]
            { symbols.put($ID.text, $arrayType.type); } ';' ;

arrayType[String basic_type]
    returns [String type]
          : '[' INT ']' arrayType[$basic_type]
            { $type = "(" + $INT.text + ", " + $arrayType.type + ")"; }
          | { $type = $basic_type; }
          ;

//expr: primary = expr '[' subscript = expr ']'
expr returns [String type]
     : ID { String expr_type = symbols.get($ID.text); }
       ('[' INT ']'
         {
           int start = expr_type.indexOf(',');
           int end = expr_type.lastIndexOf(')');
           expr_type = expr_type.substring(start + 1, end);
         }
      )+    { $type = expr_type; }
    | ID    { $type = symbols.get($ID.text); }
    | INT   { $type = "int"; }
    ;

ID : [a-z]+ ;
INT : [0-9]+ ;
WS : [ \t\n\r]+ -> skip ;