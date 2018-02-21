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


    private double volumeOne = 0.0;
    private double volumeTwo = 0.0;
    private double volumeThree = 0.0;
    private double volumeFour = 0.0;
    private TextView volumeForFirst;
    private TextView volumeForSecond;
    private TextView volumeForThird;
    private TextView volumeForFourth;
    private EditText nameForFirst;
    private EditText nameForSecond;
    private EditText nameForThird;
    private EditText nameForForth;
    private double SMALL_BEER = 0.3;
    private double REGULAR_BEER = 0.5;
    private Button btnUndo;
    private Stack st = new Stack();
    private DecimalFormat twoDecimal = new DecimalFormat("0.0");


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
        volumeForFirst.setText("0,0 l");
        volumeForSecond.setText("0,0 l");
        volumeForThird.setText("0,0 l");
        volumeForFourth.setText("0,0 l");
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

    /**
     * Method for saving values
     * @param outState
     */

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

    /**
     * Method for restore values
     * @param savedInstanceState
     */

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

    /**
     *
     * @param total is quantity of litres
     * @param volume is Textview where we set the total
     */

    public void display(double total, TextView volume) {
        volume.setText(twoDecimal.format(total) + " l");
    }

    /**
     * Method for reseting score
     * @param view
     */

    public void reset(View view) {
        volumeOne = 0.0;
        volumeTwo = 0.0;
        volumeThree = 0.0;
        volumeFour = 0.0;
        displayAll();
        btnUndo.setEnabled(true);
    }

    /**
     * Method for display all four volumes
     */

    private void displayAll() {
        display(volumeOne, volumeForFirst);
        display(volumeTwo, volumeForSecond);
        display(volumeThree, volumeForThird);
        display(volumeFour, volumeForFourth);
    }

    /**
     * Method for go back one step
     * @param view
     */

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

    /**
     * Method for create message
     * @return String with message
     */

    private String createMessage() {
        return nameForFirst.getText().toString() + ": " + volumeOne + " l\n" + nameForSecond.getText().toString()+ ": " + volumeTwo + " l\n"
                + nameForThird.getText().toString()+ ": " + volumeThree + " l\n" + nameForForth.getText().toString()+ ": " + volumeFour + " l\n";
    }

    /**
     * Method for open an email app and create message
     * @param view
     */

    public void sendOnEmail(View view) {
    composeEmail(getString(R.string.mail_text), createMessage());
    }

    /**
     * Method for compose an Email
     * @param subject subject of email
     * @param message message of email
     */

    public void composeEmail(String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
