# Test Cases: First Version

## `bool.txt`
```
if (true) {
  a = b;
}
```

## `if.txt`
```
if (true) {
  a = b;
  b = c;
}
```

## `while.txt`

```
while (a > b) {
  a = 1;
}
a = 2;
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
