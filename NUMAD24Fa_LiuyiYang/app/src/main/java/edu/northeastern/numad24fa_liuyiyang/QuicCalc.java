package edu.northeastern.numad24fa_liuyiyang;

import android.os.Bundle;

import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuicCalc extends AppCompatActivity {
    private TextView textInput;
    private StringBuilder inputBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quic_calc);


        textInput = findViewById(R.id.textInput);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.buttonZero).setOnClickListener(v -> appendToInput("0"));
        findViewById(R.id.buttonOne).setOnClickListener(v -> appendToInput("1"));
        findViewById(R.id.buttonTwo).setOnClickListener(v -> appendToInput("2"));
        findViewById(R.id.buttonThree).setOnClickListener(v -> appendToInput("3"));
        findViewById(R.id.buttonFour).setOnClickListener(v -> appendToInput("4"));
        findViewById(R.id.buttonFive).setOnClickListener(v -> appendToInput("5"));
        findViewById(R.id.buttonSix).setOnClickListener(v -> appendToInput("6"));
        findViewById(R.id.buttonSeven).setOnClickListener(v -> appendToInput("7"));
        findViewById(R.id.buttonEight).setOnClickListener(v -> appendToInput("8"));
        findViewById(R.id.buttonNine).setOnClickListener(v -> appendToInput("9"));

        findViewById(R.id.buttonPlus).setOnClickListener(v -> appendToInput("+"));
        findViewById(R.id.buttonMinus).setOnClickListener(v -> appendToInput("-"));

        findViewById(R.id.buttonDeleteNum).setOnClickListener(v -> deleteLastChar());
        findViewById(R.id.buttonEqual).setOnClickListener(v -> evaluateExpression());
    }


    private void appendToInput(String value) {
        inputBuilder.append(value);
        textInput.setText(inputBuilder.toString());
    }


    private void deleteLastChar() {
        if (inputBuilder.length() > 0) {
            inputBuilder.deleteCharAt(inputBuilder.length() - 1);
            textInput.setText(inputBuilder.toString());
        }
    }

    private void evaluateExpression() {
        try {
            String expression = inputBuilder.toString();

            if (!isValidExpression(expression)) {
                textInput.setText("Error");
                return;
            }

            int result = evaluateMathExpression(expression);
            textInput.setText(String.valueOf(result));

        } catch (Exception e) {
            textInput.setText("Error");
        }
    }
    private boolean isValidExpression(String expression) {
        if (expression == null || expression.isEmpty()) {
            return false;
        }

        if (expression.startsWith("+") || expression.startsWith("-") ||
                expression.endsWith("+") || expression.endsWith("-")) {
            return false;
        }

        for (int i = 1; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            char previousChar = expression.charAt(i - 1);

            if ((currentChar == '+' || currentChar == '-') && (previousChar == '+' || previousChar == '-')) {
                return false;
            }
        }

        return true;
    }

    private int evaluateMathExpression(String expression) {
        int result = 0;
        int currentNum = 0;
        char currentPerator = '+';

        for (int i=0;i<expression.length();i++){
            char currentChar = expression.charAt(i);

            if(Character.isDigit(currentChar)){
                currentNum = currentNum *10 +(currentChar-'0');
            }
            if(!Character.isDigit(currentChar)||i==expression.length()-1){
                if (currentPerator == '+') {
                    result += currentNum;
                } else if (currentPerator == '-') {
                    result -= currentNum;
                }
                currentPerator = currentChar;
                currentNum = 0;

            }

        }


        return result;
    }
}
