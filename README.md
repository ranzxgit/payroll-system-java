# Payroll System (Java)

COPROG1 Final Project: Payroll Computation Using Functions

A Java program that computes the monthly salary of an employee (4 weeks per month) and prints a payroll receipt.

## Team S12-10
- Steven 
- Ranz 
- Carlos

## How it works
The user enters the employee's information and the hours worked for each of the 4 weeks. The program computes the gross pay, the deductions, and the net pay, then prints a payroll receipt. After each receipt, the user can process another employee or exit.

## Rules
- **Regular pay:** first 40 hours each week x rate per hour
- **Overtime pay:** hours beyond 40 x rate x 1.25
- **Transport allowance:** ₱5,000 per week (4 weeks) if the position contains "manager"
- **Gross pay:** regular pay + overtime pay + allowance
- **Deductions:** SSS, Pag-IBIG, PhilHealth, tax, and ₱1,000 per dependent
- **Net pay:** gross pay - total deductions

## Features
- Input validation: invalid input shows an error and asks again until it is valid
- Itemized payroll receipt (employee information, earnings, deductions, gross pay, net pay)
- Option to process another employee or exit
- Typing and loading animations

## Code structure
1. **Display helpers:** `slowPrint`, `loading`, `startup`
2. **Input functions:** `getText`, `getLabel`, `getRate`, `getWholeNumber`
3. **Computation functions:** `computeReg`, `computeOTpay`, `computeGP`, `computeSSS`, `computePagibig`, `computePhilhealth`, `computeTax`, `computeDependent`, `computeDed`, `computeNP`
4. **main:** controls the flow of the program

The required functions are `computeGP`, `computeDed`, and `computeNP`. Each one returns a value to the calling function.

## How to run
Requires the Java Development Kit (JDK).

```
javac finalCode_c1a_GRP1.java
java finalCode_c1a_GRP1
```

## Sample run
Position `Cashier`, rate `100`, hours `40, 40, 40, 40`, 1 dependent:

| Item | Amount |
|---|---|
| Gross pay | ₱16,000.00 |
| Total deductions | ₱4,910.00 |
| **Net pay** | **₱11,090.00** |
