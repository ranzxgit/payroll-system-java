/*
 * ============================================================
 *  finalCode_c1a_GRP1.java
 *  COPROG1 Final Project: Payroll Computation Using Functions
 * ============================================================
 *  Programmed by Team S12-10:
 *    Steven Hernandez, Ranz Xyriz Magboo, Carlos Hernandez
 *  Flowchart: https://drive.google.com/file/d/1ANhaoIQeidKM8Ujf5ZdadNtbaWLVcaoK/view?usp=sharing
 *
 *  DESCRIPTION
 *    Computes the monthly salary of one employee (4 weeks per
 *    month) and prints a payroll receipt. After each receipt,
 *    the user can process another employee or exit.
 *
 *  INPUTS
 *    Last, first and middle name, department, position,
 *    rate per hour, hours worked for each of 4 weeks,
 *    and number of dependents.
 *
 *  PROCESS
 *    - Regular pay: first 40 hours each week x rate
 *    - Overtime pay: hours beyond 40 x rate x 1.25
 *    - Transport allowance: P5,000 per week (4 weeks) if the
 *      position contains "manager"
 *    - Gross pay = regular pay + overtime pay + allowance
 *    - Deductions: SSS, Pag-IBIG, PhilHealth, tax, and
 *      P1,000 per dependent
 *    - Net pay = gross pay - total deductions
 *
 *  OUTPUT
 *    Payroll receipt with employee information, earnings,
 *    deductions, gross pay and net pay.
 *
 *  PROGRAM STRUCTURE
 *    1. Display helpers : slowPrint, loading, startup
 *    2. Input functions : getText, getLabel, getRate,
 *                         getWholeNumber (repeat until valid)
 *    3. Computations    : computeReg, computeOTpay, computeGP,
 *                         computeSSS, computePagibig,
 *                         computePhilhealth, computeTax,
 *                         computeDependent, computeDed, computeNP
 *    4. main            : input, compute, print receipt, repeat?
 *
 *  NOTE: All money values use double for accuracy.
 * ============================================================
 */

import java.util.Scanner;

class finalCode_c1a_GRP1 {

    static Scanner sc = new Scanner(System.in);

    // ============================================================
    //  DISPLAY HELPERS
    // ============================================================

    // Prints text one character at a time (typing effect). delay is in milliseconds.
    public static void slowPrint(String text, int delay) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    // Shows the "Calculating salary..." animation before the receipt.
    public static void loading() {
        slowPrint("Calculating salary", 20);
        for (int i = 0; i < 3; i++) {
            slowPrint(".", 300);
        }
        System.out.println("\n");
    }

    // Startup screen shown once, when the program first runs.
    public static void startup() {
        slowPrint("Initializing", 30);
        for (int i = 0; i < 3; i++) {
            slowPrint(".", 400);
        }
        System.out.println("\n");
        slowPrint("========================================\n", 5);
        slowPrint("       PAYROLL SYSTEM by S12-10\n", 30);
        slowPrint("========================================\n", 5);
    }

    // ============================================================
    //  INPUT FUNCTIONS (each repeats until the input is valid)
    // ============================================================

    // Req 12: Name fields. Letters, spaces and . ' - only.
    // minLen lets the middle name be 1 character. Returns the valid name.
    public static String getText(String prompt, int minLen) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();

            if (s.length() >= minLen && s.length() <= 50
                    && s.matches("[\\p{L} .'-]+")
                    && s.matches(".*[\\p{L}].*")) {
                return s;
            }

            System.out.println(
                "Error: letters only, " + minLen + " to 50 characters.");
        }
    }

    // Req 12: Department and position. Letters, numbers, spaces and symbols such as & / . , ' ( ) -
    // Returns the valid text.
    public static String getLabel(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();

            if (s.length() >= 2 && s.length() <= 50
                    && s.matches("[\\p{L}\\p{N} &/.,'()-]+")
                    && s.matches(".*[\\p{L}].*")) {
                return s;
            }

            System.out.println(
                "Error: use letters, numbers, spaces or & / . , ' ( ) - only, 2 to 50 characters.");
        }
    }

    // Req 12: Rate per hour. Positive amount, commas allowed, up to 2 decimals.
    // Returns the rate as a double.
    public static double getRate() {
        while (true) {
            System.out.print("Enter rate per hour: ");
            String s = sc.nextLine().trim();

            if (s.matches("(\\d{1,3}(,\\d{3})+|\\d+)(\\.\\d{1,2})?")) {
                try {
                    double rate = Double.parseDouble(s.replace(",", ""));
                    if (Double.isFinite(rate) && rate > 0) {
                        return rate;
                    }
                } catch (NumberFormatException e) {
                    // Show the input error below.
                }
            }

            System.out.println(
                "Error: enter a positive amount, e.g. 1,000 or 250.50.");
        }
    }

    // Req 12: Whole numbers (weekly hours, dependents). Valid range is 0 to max.
    // Returns the number as an int.
    public static int getWholeNumber(String prompt, int max) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();

            if (s.matches("(\\d{1,3}(,\\d{3})+|\\d+)")) {
                try {
                    int n = Integer.parseInt(s.replace(",", ""));
                    if (n >= 0 && n <= max) {
                        return n;
                    }
                } catch (NumberFormatException e) {
                    // Show the input error below.
                }
            }

            System.out.println(
                "Error: enter a whole number from 0 to " + max + ".");
        }
    }

    // ============================================================
    //  COMPUTATION FUNCTIONS (all return a value to the caller)
    // ============================================================

    // Req 1 and 3: Regular pay = first 40 hours of each week x rate.
    public static double computeReg(int[] hours, double rate) {
        double total = 0;
        for (int h : hours) {
            total += Math.min(h, 40) * rate;
        }
        return total;
    }

    // Req 1 and 2: Overtime pay = hours beyond 40 x rate x 1.25 (125%).
    public static double computeOTpay(int[] hours, double rate) {
        double total = 0;
        for (int h : hours) {
            if (h > 40) {
                total += (h - 40) * rate * 1.25;
            }
        }
        return total;
    }

    // Req 3 and 15a (REQUIRED): Returns gross pay = regular + overtime + transport allowance.
    public static double computeGP(double reg, double ot, double transport) {
        return reg + ot + transport;
    }

    // Req 5: SSS contribution based on the gross pay bracket.
    public static double computeSSS(double gp) {
        if (gp <= 5000) return 105;
        else if (gp <= 10000) return gp * 0.05;
        else if (gp <= 15000) return gp * 0.08 + 75;
        else return gp * 0.12 + 110;
    }

    // Req 6: Pag-IBIG is P100 for low gross pay, otherwise 3% of gross pay.
    public static double computePagibig(double gp) {
        if (gp <= 5000) return 100;
        return gp * 0.03;
    }

    // Req 7: PhilHealth is P120, or zero if total hours worked is less than 10.
    public static double computePhilhealth(int[] hours) {
        int total = 0;
        for (int h : hours) {
            total += h;
        }
        if (total < 10) return 0;   // total hours worked in the month
        return 120;
    }

    // Req 8: Tax based on the gross pay bracket.
    public static double computeTax(double gp) {
        if (gp <= 10000) return gp * 0.03;
        else if (gp <= 25000) return gp * 0.08;
        else if (gp <= 40000) return gp * 0.11;
        else return gp * 0.135;
    }

    // Req 10: P1,000 deduction for each dependent.
    public static int computeDependent(int count) {
        return count * 1000;
    }

    // Req 15b (REQUIRED): Returns total deductions = SSS + Pag-IBIG + PhilHealth + tax + dependents.
    public static double computeDed(double gp, int[] hours, int deps) {
        double total = computeSSS(gp) + computePagibig(gp)
                + computePhilhealth(hours) + computeTax(gp)
                + computeDependent(deps);   // P1,000 per dependent is ADDED to the deductions
        return total;
    }

    // Req 11 and 15c (REQUIRED): Returns net pay = gross pay - total deductions.
    public static double computeNP(double gp, double ded) {
        return gp - ded;
    }

    // ============================================================
    //  MAIN
    // ============================================================
    public static void main(String[] args) {
        startup();   // shown once, before the first employee

        while (true) {
            slowPrint("\n========================================\n", 5);
            slowPrint("         EMPLOYEE PAYROLL SYSTEM\n", 20);
            slowPrint("========================================\n", 5);

            // ---- INPUT ----
            String last = getText("Enter last name: ", 2);
            String first = getText("Enter first name: ", 2);
            String middle = getText("Enter middle name: ", 1);
            String dept = getLabel("Enter department: ");
            String position = getLabel("Enter position: ");

            double rate = getRate();
            int[] hours = new int[4];

            for (int i = 0; i < 4; i++) {
                hours[i] = getWholeNumber(
                    "Enter hours for week " + (i + 1) + ": ", 168);
            }

            int deps = getWholeNumber("Enter number of dependents: ", 20);

            // Req 9: Any position containing "manager" receives P5,000 x 4 weeks.
            double transport = 0;
            if (position.toLowerCase().contains("manager")) {
                transport = 4 * 5000;
            }

            loading();

            // ---- COMPUTATION ----
            double reg = computeReg(hours, rate);
            double ot = computeOTpay(hours, rate);
            double gp = computeGP(reg, ot, transport);

            double sss = computeSSS(gp);
            double pagibig = computePagibig(gp);
            double philhealth = computePhilhealth(hours);
            double tax = computeTax(gp);
            int exemption = computeDependent(deps);
            double ded = computeDed(gp, hours, deps);
            double net = computeNP(gp, ded);

            // ---- OUTPUT (Req 13: payroll receipt) ----
            slowPrint("========================================\n", 5);
            slowPrint("             PAYROLL RECEIPT\n", 20);
            slowPrint("========================================\n", 5);

            // Employee information
            System.out.println("Employee Name : " + last + ", " + first + " " + middle);
            System.out.println("Department    : " + dept);
            System.out.println("Position      : " + position);
            System.out.printf("Rate / Hour   : PHP %,.2f%n", rate);
            System.out.printf("Hours Worked  : W1=%d, W2=%d, W3=%d, W4=%d%n",
                    hours[0], hours[1], hours[2], hours[3]);

            // Earnings and gross pay
            System.out.println("----------------------------------------");
            System.out.println("EARNINGS (MONTHLY)");
            System.out.printf("Regular Pay          : PHP %,.2f%n", reg);
            System.out.printf("Overtime Pay         : PHP %,.2f%n", ot);
            System.out.printf("Transport Allowance  : PHP %,.2f%n", transport);
            System.out.printf("Gross Pay            : PHP %,.2f%n", gp);

            // Deductions
            System.out.println("----------------------------------------");
            System.out.println("DEDUCTIONS");
            System.out.printf("SSS                  : PHP %,.2f%n", sss);
            System.out.printf("Pag-IBIG             : PHP %,.2f%n", pagibig);
            System.out.printf("PhilHealth           : PHP %,.2f%n", philhealth);
            System.out.printf("Tax                  : PHP %,.2f%n", tax);
            System.out.printf("Dependents           : PHP %,.2f%n", (double) exemption);
            System.out.printf("Total Deductions     : PHP %,.2f%n", ded);

            // Net pay
            System.out.println("----------------------------------------");
            slowPrint("NET PAY              : PHP ", 20);
            slowPrint(String.format("%,.2f", net) + "\n", 20);
            slowPrint("========================================\n", 5);

            // ---- RUN AGAIN OR EXIT (Req 14) ----
            System.out.print("\nProcess another employee? Y/N: ");
            String answer = sc.nextLine().trim();

            // Keep asking until the answer is Y or N.
            while (!answer.equalsIgnoreCase("Y") && !answer.equalsIgnoreCase("N")) {
                System.out.print("Error: enter Y or N: ");
                answer = sc.nextLine().trim();
            }

            if (answer.equalsIgnoreCase("N")) {
                slowPrint("Thank you for using the Payroll System!\n", 20);
                slowPrint("Made by S12-10\n", 20);
                break;
            }
        }

        sc.close();
    }
}