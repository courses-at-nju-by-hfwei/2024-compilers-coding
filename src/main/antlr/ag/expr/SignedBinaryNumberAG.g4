grammar SignedBinaryNumberAG;

@header {
package ag.expr;
}

number : sign list {
  if($sign.neg) {
    System.out.println("-" + $list.val);
  } else {
    System.out.println($list.val);
  } };

sign returns [boolean neg]
  : '+' { $neg = false; }
  | '-' { $neg = true; }
  ;

//list[int pos] returns [int val]
//  : left = list[$pos + 1] bit[$pos] { $val =  $left.val + $bit.val; }
//  | bit[int pos] { $val = $bit.val; }
//  ;

// WARNING: This is wrong!
// This grammar with Kleene Star generates bits from left to right.
// I have not fixed it! (2024-04-24)
list returns [int val]
  locals [int pos = 0]
  : ( bit[$pos] { $val += $bit.val; $pos++; } )+;

bit[int pos] returns [int val]
  : '0' { $val = 0; }
  | '1' { $val = (int) Math.pow(2, $pos); System.out.println($val); }
  ;