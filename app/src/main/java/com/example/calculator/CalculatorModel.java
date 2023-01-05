package com.example.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

enum Operation
{
    None, Add, Subtract, Multiply, Divide
}

enum State
{
    Start, FirstEntry, OpSet, SecondEntry, Result
}


class CalculatorModel
{
    enum Number
    {
        Zero(0), One(1), Two(2), Three(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9);

        private final int value;

        private Number(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private State _state;
    private Operation _currentOperation;
    private BigDecimal _firstNumber;
    private BigDecimal _currentNumber;
    private BigDecimal _result;
    private boolean _decimal;
    private int _decimals = 0;
    private DecimalFormat _decimalFormat;

    private static final int _scale = 200;

    CalculatorModel()
    {
        this._decimalFormat = new DecimalFormat();
        this._decimalFormat.setGroupingUsed(true);
        this.reset();
    }

    public String getDisplay()
    {
        if (this._state == State.Start)
            return "";
        String s;
        this._decimalFormat.setMinimumFractionDigits(this._decimals);
        if (this._state == State.OpSet)
                s = this._decimalFormat.format(this._firstNumber);
        else if (this._state == State.Result)
            s = this._decimalFormat.format(this._result);
        else
            s = this._decimalFormat.format(this._currentNumber);
        return this._formatDecimals(s);
    }

    public void reset()
    {
        this._currentOperation = Operation.None;
        this._firstNumber = BigDecimal.ZERO;
        this._currentNumber = BigDecimal.ZERO;
        this._setState(State.Start);
        this._result = BigDecimal.ZERO;
        this._decimal = false;
        this._decimals = 0;
    }

    public void pushNumber(Number numberButton)
    {
        int number = numberButton.getValue();
        BigDecimal num = BigDecimal.valueOf(number);
        if ((this._state == State.Start) || (this._state == State.Result))
        {
            this._currentNumber = num;
            this._setState(State.FirstEntry);
        }
        else if (this._state == State.OpSet)
        {
            this._currentNumber = num;
            this._setState(State.SecondEntry);
        }
        else if ((this._state == State.FirstEntry) || (this._state == State.SecondEntry))
        {
            if (this._decimal)
            {
                this._decimals++;
                this._currentNumber = this._currentNumber.add(num.divide(BigDecimal.TEN.pow(this._decimals), this._scale, RoundingMode.HALF_UP));
            } else
                this._currentNumber = this._currentNumber.multiply(BigDecimal.TEN).add(num);
        }
    }

    public void pushDecimalPoint()
    {
        if (!this._decimal)
        {
            if ((this._state == State.Start) || (this._state == State.Result))
            {
                this._currentNumber = BigDecimal.ZERO;
                this._setState(State.FirstEntry);
            }
            else if (this._state == State.OpSet)
            {
                this._currentNumber = BigDecimal.ZERO;
                this._setState(State.SecondEntry);
            }

            this._decimal = true;
            this._decimals = 0;
        }
    }

    public void add() { this._setOperation(Operation.Add); }
    public void subtract() { this._setOperation(Operation.Subtract); }
    public void multiply() { this._setOperation(Operation.Multiply); }
    public void divide() { this._setOperation(Operation.Divide); }

    public void calculate()
    {
        if (this._state == State.SecondEntry) {
            BigDecimal result = BigDecimal.ZERO;
            if (this._currentOperation == Operation.Add)
                result = this._firstNumber.add(this._currentNumber);
            else if (this._currentOperation == Operation.Subtract)
                result = this._firstNumber.subtract(this._currentNumber);
            else if (this._currentOperation == Operation.Multiply)
                result = this._firstNumber.multiply(this._currentNumber);
            else if (this._currentOperation == Operation.Divide)
            {
                if (this._currentNumber.intValue() == 0)
                    result = BigDecimal.ZERO;
                else
                    result = this._firstNumber.divide(this._currentNumber, this._scale, RoundingMode.HALF_UP);
            }
            this.reset();
            this._result = result;
            this._setState(State.Result);
        }
        else if (this._state == State.FirstEntry)
        {
            BigDecimal result = this._currentNumber;
            this.reset();
            this._result = result;
            this._setState(State.Result);
        }
    }

    private void _setOperation(Operation op)
    {
        if (this._state == State.SecondEntry)
        {
            this.calculate();
            this._currentNumber = this._result;
        }
        else if (this._state == State.Result)
        {
            this._currentNumber = this._result;
        }

        if (((this._state == State.Start) && (op == Operation.Subtract)) || (this._state == State.FirstEntry) || (this._state == State.Result))
        {
            this._firstNumber = this._currentNumber;
            this._currentOperation = op;
            this._setState(State.OpSet);
            this._currentNumber = BigDecimal.ZERO;
            this._decimal = false;
            this._decimals = 0;
        }
        else if (this._state == State.OpSet)
            this._currentOperation = op;
        else
            this.reset();
    }

    private void _setState(State state)
    {
        this._state = state;
    }

    private String _formatDecimals(String s)
    {
        if (!this._decimal)
            s = this._removeTrailingDecimal(s);
        if ((this._decimal) && (this._decimals == 0))
            s += ".";
        return s;
    }

    private String _removeTrailingDecimal(String s)
    {
        if (s.contains("."))
            return s.replaceAll("\\.0*$", "");
        return s;
    }
}
