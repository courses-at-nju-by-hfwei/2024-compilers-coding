grammar SignedBinaryNumber;

@header {
package ag.expr;
}

number : sign list ;

sign : '+' | '-' ;

list : list bit
     | bit
     ;

bit : '0' | '1' ;