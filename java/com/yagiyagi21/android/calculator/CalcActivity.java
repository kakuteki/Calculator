package com.yagiyagi21.android.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        List<Button> buttonList = new ArrayList<>();
        // 数字ボタンを取得
        buttonList.add((Button) findViewById(R.id.btZero));
        buttonList.add((Button) findViewById(R.id.btOne));
        buttonList.add((Button) findViewById(R.id.btTwo));
        buttonList.add((Button) findViewById(R.id.btThree));
        buttonList.add((Button) findViewById(R.id.btFour));
        buttonList.add((Button) findViewById(R.id.btFive));
        buttonList.add((Button) findViewById(R.id.btSix));
        buttonList.add((Button) findViewById(R.id.btSeven));
        buttonList.add((Button) findViewById(R.id.btEight));
        buttonList.add((Button) findViewById(R.id.btNine));
        // 記号ボタンを取得
        buttonList.add((Button) findViewById(R.id.btAdd));
        buttonList.add((Button) findViewById(R.id.btSubtract));
        buttonList.add((Button) findViewById(R.id.btMultiply));
        buttonList.add((Button) findViewById(R.id.btDivide));
        buttonList.add((Button) findViewById(R.id.btEqual));
        // その他ボタンを取得
        buttonList.add((Button) findViewById(R.id.btClear));
        buttonList.add((Button) findViewById(R.id.btDelete));
        buttonList.add((Button) findViewById(R.id.btPoint));
        buttonList.add((Button) findViewById(R.id.btPlusMinus));
        buttonList.add((Button) findViewById(R.id.btPercent));

        ButtonListener listener = new ButtonListener();

        for(Button button : buttonList){
            button.setOnClickListener(listener);
        }
    }

    private class ButtonListener implements View.OnClickListener {

        private List<BigDecimal> _numList = new ArrayList<>();
        private List<Character> _opeList = new ArrayList<>();
        private String _inputValue = "";

        @Override
        public void onClick(View view) {
            TextView tvFormula = findViewById(R.id.tvFormula);

            // ボタン毎の処理を定義
            int btId = view.getId();
            char inputChar;
            switch (btId) {
                // 数字ボタンの場合
                case R.id.btZero:
                    inputChar = '0';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btOne:
                    inputChar = '1';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btTwo:
                    inputChar = '2';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btThree:
                    inputChar = '3';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btFour:
                    inputChar = '4';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btFive:
                    inputChar = '5';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btSix:
                    inputChar = '6';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btSeven:
                    inputChar = '7';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btEight:
                    inputChar = '8';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btNine:
                    inputChar = '9';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                // 記号ボタンの場合
                case R.id.btAdd:
                    inputChar = '+';
                    if(!(_inputValue.equals(""))) {
                        addList(tvFormula, _inputValue, inputChar);
                    }
                    break;
                case R.id.btSubtract:
                    inputChar = '-';
                    if(!(_inputValue.equals(""))) {
                        addList(tvFormula, _inputValue, inputChar);
                    }
                    break;
                case R.id.btMultiply:
                    inputChar = '×';
                    if(!(_inputValue.equals(""))) {
                        addList(tvFormula, _inputValue, inputChar);
                    }
                    break;
                case R.id.btDivide:
                    inputChar = '÷';
                    if(!(_inputValue.equals(""))) {
                        addList(tvFormula, _inputValue, inputChar);
                    }
                    break;
                case R.id.btEqual:
                    inputChar = '=';
                    if(!(_inputValue.equals(""))) {
                        addList(tvFormula, _inputValue, inputChar);
                    }
                    String result = calculate().toString();
                    tvFormula.setText(result);
                    _inputValue = result;

                    _numList.clear();
                    _opeList.clear();
                    break;
                // その他ボタンの場合の処理
                case R.id.btClear:
                    tvFormula.setText("");
                    _numList.clear();
                    _opeList.clear();
                    _inputValue= "";
                    break;
                case R.id.btDelete:
                    String formulaStr = tvFormula.getText().toString();
                    char formulaStrLastChar = formulaStr.charAt(formulaStr.length() - 1);

                    if (isFourArithmeticOpe(formulaStrLastChar)) {
                        _opeList.remove(_opeList.size() - 1);
                    }
                    if (!formulaStr.isEmpty()) {
                        tvFormula.setText(formulaStr.subSequence(0, formulaStr.length() - 1));
                    }
                    if (!_inputValue.isEmpty()) {
                        _inputValue = _inputValue.substring(0, _inputValue.length() - 1);
                    }
                    break;
                case R.id.btPoint:
                    inputChar = '.';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btPlusMinus:
                    break;
                case R.id.btPercent:
                    break;
            }
        }

        private void addList(TextView tvFormula, String inputValue, char ope) {
            addTextView(tvFormula, ope);
            _numList.add(new BigDecimal(inputValue));
            _opeList.add(ope);
            _inputValue = "";
        }

        private void addTextView(TextView textView, char addStr) {
            textView.setText(textView.getText().toString() + addStr);
        }

        private BigDecimal calculate() {
            int i = 0;

            while(i < _opeList.size()) {
                if(_opeList.get(i) == '×' | _opeList.get(i) == '÷') {
                    BigDecimal resultMultiDiv = _opeList.get(i) == '×' ? _numList.get(i).multiply(_numList.get(i+1)) : _numList.get(i).divide(_numList.get(i+1));

                    _numList.set(i, resultMultiDiv);
                    _numList.remove(i+1);
                    _opeList.remove(i);
                    i--;
                }
                else if(_opeList.get(i) == 'ー') {
                    _opeList.set(i, '＋');
                    _numList.set(i+1, _numList.get(i+1).negate());
                }
                i++;
            }

            BigDecimal result = new BigDecimal("0");
            for(BigDecimal j : _numList) {
                result = result.add(j);
            }

            return result;
        }

        private boolean isFourArithmeticOpe(char c) {
            if(c == '+' | c == '-' | c == '*' | c == '/') return true;
            return false;
        }
    }
}
