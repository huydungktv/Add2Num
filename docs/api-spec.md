# API Specification

## `MyBigNumber.sum`

```java
String sum(String first, String second)
```

### Input

Hai chuỗi biểu diễn số nguyên không âm.

### Output

Chuỗi biểu diễn tổng của hai số.

### Errors

Ném `IllegalArgumentException` khi input là `null`, rỗng hoặc chứa ký tự không phải chữ số.
