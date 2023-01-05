package com.example.calculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorModelUnitTests {
    @Test
    public void pushingANumberPutsItInDisplay() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.One);
        assertEquals("1", m.getDisplay());
    }

    @Test
    public void pushingANumberAppendsItToDisplay() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.One);
        m.pushNumber(CalculatorModel.Number.Two);
        assertEquals("12", m.getDisplay());
    }

    @Test
    public void numbersAreSeparatedAtThousandsByLocaleSeparator() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.One);
        m.pushNumber(CalculatorModel.Number.Zero);
        m.pushNumber(CalculatorModel.Number.Zero);
        m.pushNumber(CalculatorModel.Number.Zero);
        String s = String.format("%,d", 1000);
        assertEquals(s, m.getDisplay());
    }

    @Test
    public void pushingAZeroAppendsItToDisplay() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.One);
        m.pushNumber(CalculatorModel.Number.Zero);
        assertEquals("10", m.getDisplay());
    }

    @Test
    public void pushingADecimalPointPutsItInDisplay() {
        CalculatorModel m = new CalculatorModel();
        m.pushDecimalPoint();
        assertEquals("0.", m.getDisplay());
    }

    @Test
    public void pushingADecimalPointAppendsItToDisplay() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.One);
        m.pushDecimalPoint();
        assertEquals("1.", m.getDisplay());
    }

    @Test
    public void pushingANumberAfterADecimalPointAppendsItToDisplay() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.One);
        m.pushDecimalPoint();
        m.pushNumber(CalculatorModel.Number.Two);
        assertEquals("1.2", m.getDisplay());
    }

    @Test
    public void pushingAZeroAfterADecimalPointAppendsItToDisplay() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.One);
        m.pushDecimalPoint();
        m.pushNumber(CalculatorModel.Number.Zero);
        assertEquals("1.0", m.getDisplay());
    }

    @Test
    public void pushingSeveralNumbersAfterADecimalPointAppendsThemToDisplay() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.One);
        m.pushDecimalPoint();
        m.pushNumber(CalculatorModel.Number.One);
        m.pushNumber(CalculatorModel.Number.One);
        m.pushNumber(CalculatorModel.Number.One);
        m.pushNumber(CalculatorModel.Number.One);
        m.pushNumber(CalculatorModel.Number.One);
        m.pushNumber(CalculatorModel.Number.One);
        assertEquals("1.111111", m.getDisplay());
    }

    @Test
    public void canEnterNumbersUpTo200DigitsAccurately() {
        CalculatorModel m = new CalculatorModel();
        for (int i = 0; i<200;i++)
            m.pushNumber(CalculatorModel.Number.One);
        assertEquals("11,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111,111", m.getDisplay());
    }

    @Test
    public void canEnterFractionsUpTo200DigitsAccurately() {
        CalculatorModel m = new CalculatorModel();
        m.pushDecimalPoint();
        for (int i = 0; i<200;i++)
            m.pushNumber(CalculatorModel.Number.One);
        assertEquals("0.11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", m.getDisplay());
    }

    @Test
    public void pushingANumberAfterAnOperationPutsItInDisplay() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.One);
        m.add();
        m.pushNumber(CalculatorModel.Number.Two);
        assertEquals("2", m.getDisplay());
    }

    @Test
    public void addingNumbersGivesCorrectResult() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.One);
        m.add();
        m.pushNumber(CalculatorModel.Number.One);
        m.calculate();
        assertEquals("2", m.getDisplay());
    }

    @Test
    public void subtractingNumbersGivesCorrectResult() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.Two);
        m.subtract();
        m.pushNumber(CalculatorModel.Number.One);
        m.calculate();
        assertEquals("1", m.getDisplay());
    }

    @Test
    public void multiplyingNumbersGivesCorrectResult() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.Two);
        m.multiply();
        m.pushNumber(CalculatorModel.Number.Two);
        m.calculate();
        assertEquals("4", m.getDisplay());
    }

    @Test
    public void dividingNumbersGivesCorrectResult() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.One);
        m.divide();
        m.pushNumber(CalculatorModel.Number.Two);
        m.calculate();
        assertEquals("0.5", m.getDisplay());
    }

    @Test
    public void appendingZeroAfterDecimalPointShowsInDisplay() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.One);
        m.pushDecimalPoint();
        m.pushNumber(CalculatorModel.Number.Zero);
        assertEquals("1.0", m.getDisplay());
    }

    @Test
    public void pushingMultipleDecimalPointsHasNoEffect() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.One);
        m.pushDecimalPoint();
        m.pushDecimalPoint();
        assertEquals("1.", m.getDisplay());
    }

    @Test
    public void dividingByZeroDisplaysZero() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.One);
        m.divide();
        m.pushNumber(CalculatorModel.Number.Zero);
        m.calculate();
        assertEquals("0", m.getDisplay());
    }

    @Test
    public void canCalculateInfiniteDecimalsToSomePrecision() {
        CalculatorModel m = new CalculatorModel();
        m.pushNumber(CalculatorModel.Number.One);
        m.pushDecimalPoint();
        m.pushNumber(CalculatorModel.Number.Six);
        m.divide();
        m.pushNumber(CalculatorModel.Number.Nine);
        m.pushDecimalPoint();
        m.pushNumber(CalculatorModel.Number.Two);
        m.calculate();
    }

}