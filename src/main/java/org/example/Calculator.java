package org.example;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    static float finalResult;

    static class Operations {

        static final char ADDITION_SYMBOL = '+';
        static final char SUBTRACTION_SYMBOL = '-';
        static final char MULTIPLICATION_SYMBOL = '*';
        static final char DIVISION_SYMBOL = '/';

        private Operations() {
        }

        public static String ToString() {
            return "" + ADDITION_SYMBOL + MULTIPLICATION_SYMBOL + DIVISION_SYMBOL + SUBTRACTION_SYMBOL;
        }

    }

    public static String Run(String expression) {
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Expresie invalidă");
        }
        return evaluateExpression(expression);
    }

    private static String evaluateExpression(String expression) {
        if (expression.charAt(0) == Operations.ADDITION_SYMBOL
                || expression.charAt(0) == Operations.SUBTRACTION_SYMBOL) {
            expression = 0 + expression;
        }

        // Split the expression by operators to extract numbers
        String[] numbers = expression.split("[" + Operations.ToString() + "]");

        // Parse the operators
        List<String> operationList = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            if (currentChar == Operations.ADDITION_SYMBOL
                    || currentChar == Operations.SUBTRACTION_SYMBOL
                    || currentChar == Operations.MULTIPLICATION_SYMBOL
                    || currentChar == Operations.DIVISION_SYMBOL) {
                operationList.add(String.valueOf(currentChar));
            }
        }

        // Parse the numbers, converting them from strings to floats
        List<Float> numberList = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            try {
                numberList.add(Float.parseFloat(numbers[i].trim()));  // trim whitespace
            } catch (NumberFormatException exc) {
                throw new IllegalArgumentException("Expresie invalidă");
            }
        }

        // Handle division by zero
        for (int i = 0; i < operationList.size(); i++) {
            if (operationList.get(i).equals(String.valueOf(Operations.DIVISION_SYMBOL)) && numberList.get(i + 1) == 0) {
                throw new ArithmeticException("Împărțirea la zero nu este permisă");
            }
        }

        // Calculate the result
        Calculate(numberList, operationList);
        return Float.toString(finalResult);
    }

    private static void Calculate(List<Float> numbers, List<String> operations) {

        if (numbers.size() == 1) {
            finalResult = numbers.get(0);
            return;
        }

        float result = 0;

        // Handle multiplication and division first
        int indexMultiply = operations.indexOf(String.valueOf(Operations.MULTIPLICATION_SYMBOL));
        int indexDivide = operations.indexOf(String.valueOf(Operations.DIVISION_SYMBOL));

        if (indexMultiply != -1 && indexDivide != -1) {
            if (indexMultiply < indexDivide) {
                result = numbers.get(indexMultiply) * numbers.get(indexMultiply + 1);
                numbers.set(indexMultiply, result);
                numbers.remove(indexMultiply + 1);
                operations.remove(indexMultiply);
            } else {
                result = numbers.get(indexDivide) / numbers.get(indexDivide + 1);
                numbers.set(indexDivide, result);
                numbers.remove(indexDivide + 1);
                operations.remove(indexDivide);
            }
            Calculate(numbers, operations);
            return;
        }

        if (indexMultiply != -1) {
            result = numbers.get(indexMultiply) * numbers.get(indexMultiply + 1);
            numbers.set(indexMultiply, result);
            numbers.remove(indexMultiply + 1);
            operations.remove(indexMultiply);
            Calculate(numbers, operations);
            return;
        }

        if (indexDivide != -1) {
            result = numbers.get(indexDivide) / numbers.get(indexDivide + 1);
            numbers.set(indexDivide, result);
            numbers.remove(indexDivide + 1);
            operations.remove(indexDivide);
            Calculate(numbers, operations);
            return;
        }

        // Handle addition and subtraction next
        int indexPlus = operations.indexOf(String.valueOf(Operations.ADDITION_SYMBOL));
        int indexMinus = operations.indexOf(String.valueOf(Operations.SUBTRACTION_SYMBOL));

        if (indexPlus != -1 && indexMinus != -1) {
            if (indexPlus < indexMinus) {
                result = numbers.get(indexPlus) + numbers.get(indexPlus + 1);
                numbers.set(indexPlus, result);
                numbers.remove(indexPlus + 1);
                operations.remove(indexPlus);
            } else {
                result = numbers.get(indexMinus) - numbers.get(indexMinus + 1);
                numbers.set(indexMinus, result);
                numbers.remove(indexMinus + 1);
                operations.remove(indexMinus);
            }
            Calculate(numbers, operations);
            return;
        }

        if (indexPlus != -1) {
            result = numbers.get(indexPlus) + numbers.get(indexPlus + 1);
            numbers.set(indexPlus, result);
            numbers.remove(indexPlus + 1);
            operations.remove(indexPlus);
            Calculate(numbers, operations);
            return;
        }

        if (indexMinus != -1) {
            result = numbers.get(indexMinus) - numbers.get(indexMinus + 1);
            numbers.set(indexMinus, result);
            numbers.remove(indexMinus + 1);
            operations.remove(indexMinus);
            Calculate(numbers, operations);
            return;
        }
    }

}
