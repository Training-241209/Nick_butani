# as const

## What is as const?
`as const` is a type assertion that tells TypeScript that a variable or expression should be treated as a literal value. 

## Basic Syntax
```typescript
const myValue = "hello" as const;
```

## Key Features
1. **Literal Types:** Ensures that values retain their literal types, making them immutable.
   ```typescript
   const myNumber = 10 as const; // myNumber is of type 10 (not number)
   ```

2. **Readonly Properties:** Arrays and objects become readonly when used with `as const`.
   ```typescript
   const myArray = [1, 2, 3] as const; // readonly [1, 2, 3]
   const myObject = { key: "value" } as const; // { readonly key: "value" }
   ```

3. **Improved Type Safety:** Prevents unintended modifications and ensures stricter type checking.

## Use Cases
- Defining constants in configurations.
- Enforcing immutability in data structures.
- Working with discriminated unions and enums.

## Example
```typescript
const STATUS = {
  SUCCESS: "success",
  FAILURE: "failure",
} as const;

type Status = typeof STATUS[keyof typeof STATUS];

function handleStatus(status: Status) {
  if (status === STATUS.SUCCESS) {
    console.log("Operation was successful");
  } else {
    console.log("Operation failed");
  }
}

handleStatus(STATUS.SUCCESS); // Valid
handleStatus("unknown"); // Error
```

`as const` enhances TypeScript by locking down values and improving type safety, making it an essential tool for developers aiming for robust, maintainable code.


This is useful when you want to ensure that a variable or expression is not modified after it has been assigned a value.