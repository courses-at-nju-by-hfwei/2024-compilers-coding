# Test Cases

## `bool.txt`

if true a = b

## `if.txt`

if (true && false && true || ! false && ! true || ! a >= 5 && a == b) {
  a = b;
  b = c;
} else {
  c = d;
}

## `while.txt`

while (a > b && false && true) {
  a = 1;
}
a = 2;

## `break.txt`

while (true) {
  while (false) {
    if (true) {
      break;
    }
    a = 1;
  }
  a = 2;
}
a = 3;

## `bool-short-circuit.txt`

while (a > b && false && true) {
  a = 1;
}
a = 2;
