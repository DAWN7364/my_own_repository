package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_result;
    private Button bt_cancel;
    private Button bt_genhao;
    private Button bt_chu;
    private Button bt_cheng;
    private Button bt_clear;
    private Button bt_7;
    private Button bt_8;
    private Button bt_9;
    private Button bt_jia;
    private Button bt_4;
    private Button bt_5;
    private Button bt_6;
    private Button bt_jian;
    private Button bt_1;
    private Button bt_2;
    private Button bt_3;
    private Button bt_daoshu;
    private Button bt_0;
    private Button bt_point;
    private Button bt_result;

    private String firstNumber ="";
    private String operator ="";
    private String secondNumber = "";
    private String result = "";

    private String showText ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_result = findViewById(R.id.tv_result);
        bt_cancel = findViewById(R.id.bt_cancel);
        bt_chu = findViewById(R.id.bt_chu);
        bt_cheng = findViewById(R.id.bt_cheng);
        bt_clear = findViewById(R.id.bt_clear);
        bt_7 = findViewById(R.id.bt_7);
        bt_8 = findViewById(R.id.bt_8);
        bt_9 = findViewById(R.id.bt_9);
        bt_jia = findViewById(R.id.bt_jia);
        bt_4 = findViewById(R.id.bt_4);
        bt_5 = findViewById(R.id.bt_5);
        bt_6 = findViewById(R.id.bt_6);
        bt_jian = findViewById(R.id.bt_jian);
        bt_1 = findViewById(R.id.bt_1);
        bt_2 = findViewById(R.id.bt_2);
        bt_3 = findViewById(R.id.bt_3);
        bt_genhao = findViewById(R.id.bt_genhao);
        bt_daoshu = findViewById(R.id.bt_daoshu);
        bt_0 = findViewById(R.id.bt_0);
        bt_point = findViewById(R.id.bt_point);
        bt_result = findViewById(R.id.bt_result);

        bt_cancel.setOnClickListener(this);
        bt_chu.setOnClickListener(this);
        bt_cheng.setOnClickListener(this);
        bt_clear.setOnClickListener(this);
        bt_7.setOnClickListener(this);
        bt_8.setOnClickListener(this);
        bt_9.setOnClickListener(this);
        bt_jia.setOnClickListener(this);
        bt_4.setOnClickListener(this);
        bt_5.setOnClickListener(this);
        bt_6.setOnClickListener(this);
        bt_jian.setOnClickListener(this);
        bt_1.setOnClickListener(this);
        bt_2.setOnClickListener(this);
        bt_3.setOnClickListener(this);
        bt_genhao.setOnClickListener(this);
        bt_daoshu.setOnClickListener(this);
        bt_0.setOnClickListener(this);
        bt_point.setOnClickListener(this);
        bt_result.setOnClickListener(this);

    }


    public void onClick(View v)
    {
        //获取按钮的值
        String inputText;
        if(v.getId() == R.id.bt_genhao)
            inputText = "√";
        else
        {
            inputText = ((TextView)v).getText().toString();
        }

        switch (v.getId())//点击按钮的操作
        {
            case R.id.bt_clear:
                clear();
                break;
            case R.id.bt_cancel:
                cancel();
                break;
            case R.id.bt_result:
                if(operator.equals("") || secondNumber.equals(""))
                {
                    SyncText(firstNumber);
                }
                else
                {
                    double result_four = FourCal();
                    Syncoperator(String.valueOf(result_four));
                    SyncText(showText + "=" + result);
                }
                break;
            case R.id.bt_daoshu:
                Double result_daoshu = 1.0/(Double.parseDouble(firstNumber));
                Syncoperator(String.valueOf(result_daoshu));
                SyncText(showText + "/=" + result);
                break;
            case R.id.bt_genhao:
                double resuult_genhao = Math.sqrt(Double.parseDouble(firstNumber));
                Syncoperator(String.valueOf(resuult_genhao));
                SyncText(showText + "√" + "=" + result);
                break;
            case R.id.bt_jia:
            case R.id.bt_jian:
            case R.id.bt_cheng:
            case R.id.bt_chu:
                operator = inputText;
                SyncText(showText + operator);
                break;
            default:
                 if(result.length() > 0 && operator.equals(""))
                     clear();

                if(operator.equals(""))
                    firstNumber = firstNumber + inputText;
                else
                {
                    secondNumber = secondNumber +inputText;
                }

                if(showText.equals("0") && !inputText.equals("."))
                {
                    SyncText(inputText);
                }
                else
                {
                    SyncText(showText + inputText);
                }
                break;

        }
    }

    private void cancel() {
        if(operator.equals(""))
        {
            firstNumber = firstNumber.substring(0,firstNumber.length()-1);
            SyncText(firstNumber);
        }
        else if (secondNumber.equals(""))
        {
            operator = "";
            showText = showText.substring(0,showText.length()-1);
            SyncText(showText);
        }
        else {
            secondNumber = secondNumber.substring(0,secondNumber.length()-1);
            showText = showText.substring(0,showText.length()-1);
            SyncText(showText);
        }
    }

    private double FourCal() {
        switch (operator) {
            case "+":
                return Double.parseDouble(firstNumber) + Double.parseDouble(secondNumber);
            case "-":
                return Double.parseDouble(firstNumber) - Double.parseDouble(secondNumber);
            case "×":
                return Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber);
            case "/":
                return Double.parseDouble(firstNumber) / Double.parseDouble(secondNumber);

        }
        return 0;
    }
    private void clear() {
        SyncText("");
        Syncoperator("");

    }
    private void Syncoperator(String new_result)
    {
        result = new_result;
        operator = "";
        firstNumber = result;
        secondNumber = "";
    }
    private void SyncText(String text)
       {
           showText = text;
           tv_result.setText(showText);
       }

}