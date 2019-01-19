package com.yagiyagi21.android.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalcActivity extends AppCompatActivity {

    private static CalcActivity _instance = null;
    private static DatabaseAdapter _dbAdapter;
    private static RecyclerListAdapter _recyclerListAdapter;
    private List<Map<String, String>> _memoList = new ArrayList<>();
    private RecyclerView _rvMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        _instance = CalcActivity.this;

        // Buttonオブジェクトを生成してリスナーを設定
        List<Button> buttonList = getButtonList();
        ButtonListener listener = new ButtonListener();
        for(Button button : buttonList){
            button.setOnClickListener(listener);
        }

        // DBからすべてのメモを取得
        _dbAdapter = new DatabaseAdapter(_instance);
        _dbAdapter.open();
        _memoList = _dbAdapter.getAllMemos();
        _dbAdapter.close();

        //RecyclerViewを生成
        _rvMemo = findViewById(R.id.rvMemo);
        LinearLayoutManager layout = new LinearLayoutManager(_instance);
        _recyclerListAdapter = new RecyclerListAdapter(_memoList);
        DividerItemDecoration decorator = new DividerItemDecoration(_instance, layout.getOrientation());
        _rvMemo.setLayoutManager(layout);
        _rvMemo.setAdapter(_recyclerListAdapter);
        _rvMemo.addItemDecoration(decorator);

        // スワイプでリストの要素を削除
        SwipeToDeleteCallback swipeHandler = new SwipeToDeleteCallback(0, (ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPosition = viewHolder.getAdapterPosition();
                String memoId = _recyclerListAdapter.getMemoByPosition(swipedPosition).get("id");

                // DBから削除
                _dbAdapter.open();
                _dbAdapter.deleteMemo(Integer.parseInt(memoId));
                _dbAdapter.close();

                // RecyclerViewから削除
                _recyclerListAdapter.removeAt(swipedPosition);
                _rvMemo.setAdapter(_recyclerListAdapter);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHandler);
        itemTouchHelper.attachToRecyclerView(_rvMemo);
    }

    public static CalcActivity getInstance() {
        return _instance;
    }

    private List<Button> getButtonList() {
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
        buttonList.add((Button) findViewById(R.id.btMemo));

        return buttonList;
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
            boolean isTvFormulaMax = 22 < tvFormula.getText().toString().length();
            char inputChar;
            switch (btId) {
                // 数字ボタンの場合
                case R.id.btZero:
                    if(isTvFormulaMax) return;
                    inputChar = '0';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btOne:
                    if(isTvFormulaMax) return;
                    inputChar = '1';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btTwo:
                    if(isTvFormulaMax) return;
                    inputChar = '2';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btThree:
                    if(isTvFormulaMax) return;
                    inputChar = '3';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btFour:
                    if(isTvFormulaMax) return;
                    inputChar = '4';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btFive:
                    if(isTvFormulaMax) return;
                    inputChar = '5';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btSix:
                    if(isTvFormulaMax) return;
                    inputChar = '6';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btSeven:
                    if(isTvFormulaMax) return;
                    inputChar = '7';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btEight:
                    if(isTvFormulaMax) return;
                    inputChar = '8';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.btNine:
                    if(isTvFormulaMax) return;
                    inputChar = '9';
                    addTextView(tvFormula, inputChar);
                    _inputValue += inputChar;
                    break;
                // 記号ボタンの場合
                case R.id.btAdd:
                    inputChar = '+';
                    if(!(_inputValue.equals("")) || isLastOpeIsPercent()) {
                        addList(tvFormula, _inputValue, inputChar);
                    }
                    break;
                case R.id.btSubtract:
                    inputChar = '-';
                    if(!(_inputValue.equals("") || isLastOpeIsPercent())) {
                        addList(tvFormula, _inputValue, inputChar);
                    }
                    break;
                case R.id.btMultiply:
                    inputChar = '×';

                    if(!(_inputValue.equals("")) || isLastOpeIsPercent()) {
                        addList(tvFormula, _inputValue, inputChar);
                    }
                    break;
                case R.id.btDivide:
                    inputChar = '÷';
                    if(!(_inputValue.equals("") || isLastOpeIsPercent())) {
                        addList(tvFormula, _inputValue, inputChar);
                    }
                    break;
                case R.id.btEqual:
                    inputChar = '=';
                    if(!(_inputValue.equals("") || isLastOpeIsPercent())) {
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

                    char c = formulaStr.charAt(formulaStr.length() - 1);
                    boolean isFourArithmeticOpe = (c == '+' | c == '-' | c == '*' | c == '/');
                    if (isFourArithmeticOpe) {
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
                    inputChar = '%';
                    if(!(_inputValue.equals(""))) {
                        addList(tvFormula, _inputValue, inputChar);
                    }
                    break;
                case R.id.btMemo:
                    EditText etMemo = findViewById(R.id.etMemo);
                    String memo = etMemo.getText().toString();
                    String formula = tvFormula.getText().toString();

                    // DBに値を保存
                    _dbAdapter.open();
                    String memoId =String.valueOf(_dbAdapter.saveMemo(memo, formula));
                    _dbAdapter.close();

                    // メモ一覧にメモを追加
                    Map<String, String> memoItem = new HashMap<>();
                    memoItem.put("id", memoId);
                    memoItem.put("memo", memo);
                    memoItem.put("formula", formula);
                    _memoList.add(memoItem);
                    RecyclerListAdapter adapter = new RecyclerListAdapter(_memoList);
                    _rvMemo.setAdapter(adapter);

                    break;
            }
        }

        // opeListの末端文字が"%"であるかを確認
        private boolean isLastOpeIsPercent() {
            if(0 < _opeList.size()) {
                return _inputValue.equals("") && _opeList.get(_opeList.size() - 1) == '%';
            }
            return false;
        }

        // 数字リストと文字リストに要素を追加
        private void addList(TextView tvFormula, String inputValue, char ope) {
            addTextView(tvFormula, ope);
            if(!isLastOpeIsPercent()) _numList.add(new BigDecimal(inputValue));
            _opeList.add(ope);
            _inputValue = "";
        }

        // TextViewに文字列を追加
        private void addTextView(TextView textView, char addStr) {
            String str = textView.getText().toString() + addStr;
            textView.setText(str);
        }

        // 計算を実行
        private BigDecimal calculate() {
            int i = 0;

            // %を少数に変換
            while(i < _opeList.size()) {
                if(_opeList.get(i) == '%') {
                    _numList.set(i, _numList.get(i).divide(new BigDecimal("100"), 20, BigDecimal.ROUND_HALF_UP));
                    _opeList.remove(i);
                }
                i++;
            }

            i = 0;
            while(i < _opeList.size()) {
                if(_opeList.get(i) == '×' | _opeList.get(i) == '÷') {
                    BigDecimal resultMultiDiv;
                    if(_opeList.get(i) == '×') {
                        resultMultiDiv = _numList.get(i).multiply(_numList.get(i+1));
                    } else {
                        resultMultiDiv = _numList.get(i).divide(_numList.get(i+1), 20, BigDecimal.ROUND_HALF_UP);
                    }

                    _numList.set(i, resultMultiDiv);
                    _numList.remove(i+1);
                    _opeList.remove(i);
                    i--;
                }
                else if(_opeList.get(i) == '-') {
                    _opeList.set(i, '＋');
                    _numList.set(i+1, _numList.get(i+1).negate());
                }
                i++;
            }

            BigDecimal result = new BigDecimal("0");
            for(BigDecimal j : _numList) {
                result = result.add(j);
            }

            return result.stripTrailingZeros();
        }
    }
}