package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {

    private CalculatorModel _model;
    private SharedPreferences prefs;

    public void fun0(View view) { this._model.pushNumber(CalculatorModel.Number.Zero); this._updateDisplay(); }
    public void fun1(View view) { this._model.pushNumber(CalculatorModel.Number.One); this._updateDisplay(); }
    public void fun2(View view) { this._model.pushNumber(CalculatorModel.Number.Two); this._updateDisplay(); }
    public void fun3(View view) { this._model.pushNumber(CalculatorModel.Number.Three); this._updateDisplay(); }
    public void fun4(View view) { this._model.pushNumber(CalculatorModel.Number.Four); this._updateDisplay(); }
    public void fun5(View view) { this._model.pushNumber(CalculatorModel.Number.Five); this._updateDisplay(); }
    public void fun6(View view) { this._model.pushNumber(CalculatorModel.Number.Six); this._updateDisplay(); }
    public void fun7(View view) { this._model.pushNumber(CalculatorModel.Number.Seven); this._updateDisplay(); }
    public void fun8(View view) { this._model.pushNumber(CalculatorModel.Number.Eight); this._updateDisplay(); }
    public void fun9(View view) { this._model.pushNumber(CalculatorModel.Number.Nine); this._updateDisplay(); }

    public void funAdd(View view) { this._model.add(); this._updateDisplay(); }
    public void funSub(View view) { this._model.subtract(); this._updateDisplay(); }
    public void funMultiply(View view) { this._model.multiply(); this._updateDisplay(); }
    public void funDiv(View view) { this._model.divide(); this._updateDisplay(); }

    public void funEquals(View view) { this._model.calculate(); this._updateDisplay(); }
    public void funDecimal(View view) { this._model.pushDecimalPoint(); this._updateDisplay(); }
    public void funReset(View view) { this._model.reset(); this._updateDisplay(); }

    public void toggleTheme(View view)
    {
        if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this._setDarkThemeButtonText();
        this._saveNightModeToPreferences();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        this.prefs =  this.getSharedPreferences(getLocalClassName(), MODE_PRIVATE);
        this._model = new CalculatorModel();
        this._loadNightModeFromPreferences();
        this._setDarkThemeButtonText();
    }

    private void _updateDisplay()
    {
        TextView display = findViewById(R.id.numberDisplay);
        display.setText(String.valueOf(this._model.getDisplay()));
    }

    private void _setDarkThemeButtonText()
    {
        Button button = findViewById(R.id.nightButton);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO)
            button.setText(R.string.night_mode);
        else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            button.setText(R.string.day_mode);
        else
        {
            int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
                button.setText(R.string.day_mode);
            else
                button.setText(R.string.night_mode);
            button.setText(R.string.night_mode);
        }
    }

    private void _loadNightModeFromPreferences()
    {
        int nightMode = this.prefs.getInt("night_mode", -1);
        if (nightMode != -1)
            AppCompatDelegate.setDefaultNightMode(nightMode);
    }

    private void _saveNightModeToPreferences()
    {
        SharedPreferences.Editor ed = prefs.edit();
        ed.putInt("night_mode", AppCompatDelegate.getDefaultNightMode() );
        ed.commit();
    }
}
