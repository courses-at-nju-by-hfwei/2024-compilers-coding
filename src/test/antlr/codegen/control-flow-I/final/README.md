# Test Cases: Final Version

## `bool.txt`
```
if (! true || false && true || a > b) {
  a = b;
}
```

## `if.txt`
```
if (true) {
  a = b;
  b = c;
  if (false) {
    x = 1;
    y = 2;
  } else {
    z = 3;
  }
} else {
  c = d;
}
```

## `while.txt`
```
while (a > b && false && true) {
  a = 1;
  if (true) {
    b = 2;
  } else {
    c = 3;
  }
}
d = 4;
```

## `break.txt`
```
while (true) {
  a = 0;
}

while (true) {
  while (false) {
    if (true) {
      break;
    }
    a = 1;
  }
  a = 2;
  break;
  a = 3;
}
a = 4;
```

## `bool-short-circuit.txt`
```
while (true && false) {
  a = 1;
}
a = 2;
```