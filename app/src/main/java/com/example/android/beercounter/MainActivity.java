package com.example.android.beercounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    double volumeOne = 0.0;
    double volumeTwo = 0.0;
    double volumeThree = 0.0;
    double volumeFour = 0.0;
    TextView volumeForFirst;
    TextView volumeForSecond;
    TextView volumeForThird;
    TextView volumeForFourth;
    EditText nameForFirst;
    EditText nameForSecond;
    EditText nameForThird;
    EditText nameForForth;
    double SMALL_BEER = 0.3;
    double REGULAR_BEER = 0.5;
    Button btnUndo;
    Stack st = new Stack();
    DecimalFormat twoDecimal = new DecimalFormat("0.0");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnUndo = findViewById(R.id.btn_undo);
        btnUndo.setEnabled(false);
        volumeForFirst = findViewById(R.id.person_1_volume);
        volumeForSecond = findViewById(R.id.person_2_volume);
        volumeForThird = findViewById(R.id.person_3_volume);
        volumeForFourth = findViewById(R.id.person_4_volume);
        nameForFirst = findViewById(R.id.name_first);
        nameForSecond = findViewById(R.id.name_second);
        nameForThird = findViewById(R.id.name_third);
        nameForForth = findViewById(R.id.name_forth);
        ImageButton buttonOne = findViewById(R.id.small_first);
        ImageButton buttonTwo = findViewById(R.id.regular_first);
        ImageButton buttonThree = findViewById(R.id.small_second);
        ImageButton buttonFour = findViewById(R.id.regular_second);
        ImageButton buttonFive = findViewById(R.id.small_third);
        ImageButton buttonSix = findViewById(R.id.regular_third);
        ImageButton buttonSeven = findViewById(R.id.small_fourth);
        ImageButton buttonEight = findViewById(R.id.regular_fourth);
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnUndo.setEnabled(true);
                st.push(new double[]{volumeOne, volumeTwo, volumeThree, volumeFour});
                switch (view.getId()) {
                    case R.id.small_first:
                        volumeOne += SMALL_BEER;
                        break;

                    case R.id.regular_first:
                        volumeOne += REGULAR_BEER;
                        break;

                    case R.id.small_second:
                        volumeTwo += SMALL_BEER;
                        break;

                    case R.id.regular_second:
                        volumeTwo += REGULAR_BEER;
                        break;

                    case R.id.small_third:
                        volumeThree += SMALL_BEER;
                        break;

                    case R.id.regular_third:
                        volumeThree += REGULAR_BEER;
                        break;

                    case R.id.small_fourth:
                        volumeFour += SMALL_BEER;
                        break;

                    case R.id.regular_fourth:
                        volumeFour += REGULAR_BEER;
                        break;
                }
                displayAll();
            }
        };
        buttonOne.setOnClickListener(myOnClickListener);
        buttonTwo.setOnClickListener(myOnClickListener);
        buttonThree.setOnClickListener(myOnClickListener);
        buttonFour.setOnClickListener(myOnClickListener);
        buttonFive.setOnClickListener(myOnClickListener);
        buttonSix.setOnClickListener(myOnClickListener);
        buttonSeven.setOnClickListener(myOnClickListener);
        buttonEight.setOnClickListener(myOnClickListener);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("volumeOne", volumeOne);
        outState.putDouble("volumeTwo", volumeTwo);
        outState.putDouble("volumeThree", volumeThree);
        outState.putDouble("volumeFour", volumeFour);
        outState.putString("nameFirst", nameForFirst.getText().toString());
        outState.putString("nameSecond", nameForSecond.getText().toString());
        outState.putString("nameThird", nameForThird.getText().toString());
        outState.putString("nameForth", nameForForth.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        volumeOne = savedInstanceState.getDouble("volumeOne");
        volumeTwo = savedInstanceState.getDouble("volumeTwo");
        volumeThree = savedInstanceState.getDouble("volumeThree");
        volumeFour = savedInstanceState.getDouble("volumeFour");
        nameForFirst.setText(savedInstanceState.getString("nameFirst"));
        nameForSecond.setText(savedInstanceState.getString("nameSecond"));
        nameForThird.setText(savedInstanceState.getString("nameThird"));
        nameForForth.setText(savedInstanceState.getString("nameForth"));
        displayAll();
    }

    public void display(double total, TextView volume) {

        volume.setText(twoDecimal.format(total) + " l");
    }

    public void reset(View view) {
        volumeOne = 0.0;
        volumeTwo = 0.0;
        volumeThree = 0.0;
        volumeFour = 0.0;
        displayAll();
        btnUndo.setEnabled(true);
    }

    private void displayAll() {
        display(volumeOne, volumeForFirst);
        display(volumeTwo, volumeForSecond);
        display(volumeThree, volumeForThird);
        display(volumeFour, volumeForFourth);
    }

    public void undo(View view) {
        if (!st.empty()) {
            double[] pole = (double[]) st.pop();
            volumeOne = pole[0];
            volumeTwo = pole[1];
            volumeThree = pole[2];
            volumeFour = pole[3];
            displayAll();
        }
    }

    private String createMessage() {
        return nameForFirst.getText().toString() + ": " + volumeOne + " l\n" + nameForSecond.getText().toString()+ ": " + volumeTwo + " l\n"
                + nameForThird.getText().toString()+ ": " + volumeThree + " l\n" + nameForForth.getText().toString()+ ": " + volumeFour + " l\n";
    }

    public void sendOnEmail(View view) {
        composeEmail(new String[] {"martina.hanus86@gmail.com"}, "Tolik jsme toho vypili", createMessage());
    }

    public void composeEmail(String[] addresses, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
