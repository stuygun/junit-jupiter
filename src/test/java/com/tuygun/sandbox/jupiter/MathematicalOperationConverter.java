package com.tuygun.sandbox.jupiter;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

public class MathematicalOperationConverter implements ArgumentConverter {
    @Override
    public Object convert(Object source, ParameterContext parameterContext) throws ArgumentConversionException {
        return checkAndConvertSource(source);
    }

    private MathematicalOperation checkAndConvertSource(Object source) {
        Integer a = null;
        Integer b = null;
        Integer result = null;
        Double divisitionResult = null;
        MathematicalOperation.Operation op = null;

        if (source == null) {
            throw new ArgumentConversionException("Cannot convert null source object");
        }

        if (!source.getClass().equals(String.class)) {
            throw new ArgumentConversionException(
                    "Cannot convert source object because it's not a string"
            );
        }

        String sourceString = (String) source;
        if (sourceString.trim().isEmpty()) {
            throw new ArgumentConversionException(
                    "Cannot convert an empty source string"
            );
        }

        try {
            int opInd = sourceString.indexOf("+");
            if (opInd != -1) {
                a = Integer.parseInt(sourceString.substring(0, opInd));
                b = Integer.parseInt(sourceString.substring(opInd + 1, sourceString.indexOf("=")));
                result = Integer.parseInt(sourceString.substring(sourceString.indexOf("=") + 1));

                return new MathematicalOperation(a, b, MathematicalOperation.Operation.ADD, result);
            }

            opInd = sourceString.indexOf("-");
            if (opInd != -1) {
                a = Integer.parseInt(sourceString.substring(0, opInd));
                b = Integer.parseInt(sourceString.substring(opInd + 1, sourceString.indexOf("=")));
                result = Integer.parseInt(sourceString.substring(sourceString.indexOf("=") + 1));

                return new MathematicalOperation(a, b, MathematicalOperation.Operation.SUBTRACT, result);
            }

            opInd = sourceString.indexOf("*");
            if (opInd != -1) {
                a = Integer.parseInt(sourceString.substring(0, opInd));
                b = Integer.parseInt(sourceString.substring(opInd + 1, sourceString.indexOf("=")));
                result = Integer.parseInt(sourceString.substring(sourceString.indexOf("=") + 1));

                return new MathematicalOperation(a, b, MathematicalOperation.Operation.MULTIPLY, result);
            }

            opInd = sourceString.indexOf("/");
            if (opInd != -1) {
                a = Integer.parseInt(sourceString.substring(0, opInd));
                b = Integer.parseInt(sourceString.substring(opInd + 1, sourceString.indexOf("=")));
                divisitionResult = Double.parseDouble(sourceString.substring(sourceString.indexOf("=") + 1));

                return new MathematicalOperation(a, b, MathematicalOperation.Operation.DIVIDE, divisitionResult);
            }

            opInd = sourceString.indexOf("!");
            if (opInd != -1) {
                a = Integer.parseInt(sourceString.substring(0, opInd));
                b = -1;
                result = Integer.parseInt(sourceString.substring(sourceString.indexOf("=") + 1));

                return new MathematicalOperation(a, b, MathematicalOperation.Operation.FACTORIAL, result);
            }
        } catch (NumberFormatException nfe){
            throw new ArgumentConversionException("Unable to parse the expression!");
        }

        throw new ArgumentConversionException("Unable to parse the expression!");
    }
}
