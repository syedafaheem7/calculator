package edu.qc.seclass.tipcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TipCalculatorActivity extends AppCompatActivity {

    public void computeTipClicked(View view){
        EditText myCheckAmountValue=(EditText)findViewById(R.id.checkAmountValue);  //this points to amountValue
        EditText myPartySizeValue= (EditText)findViewById(R.id.partySizeValue);     //this points to partySizeValue

        //empty inputs  toast a message
        if(myCheckAmountValue.getText().toString().isEmpty()||myPartySizeValue.getText().toString().isEmpty()){
            Toast.makeText(this, "Empty or incorrect value(s)!", Toast.LENGTH_SHORT).show();
        }else{
            //assign amount and partySize
            int amount= Integer.valueOf(myCheckAmountValue.getText().toString());  //get amount from checkAmountValue
            int party=Integer.valueOf(myPartySizeValue.getText().toString());      //get number of people sharing from partysizeValue
            //invalid inputs? is the amount negative? or is the party size 0 or negative? then toast error message
            if(amount<0||party<=0){
                Toast.makeText(this, "Empty or incorrect value(s)!", Toast.LENGTH_SHORT).show();
            }else {
                //assign tip values
                EditText myFifteenPercentValue = (EditText) findViewById(R.id.fifteenPercentTipValue);
                EditText myTwentyPercentValue = (EditText) findViewById(R.id.twentyPercentTipValue);
                EditText myTwentyFivePercentValue = (EditText) findViewById(R.id.twentyfivePercentTipValue);

                //assign Total values
                EditText myFifteenPercentTotalValue = (EditText) findViewById(R.id.fifteenPercentTotalValue);
                EditText myTwentyPercentTotalValue = (EditText) findViewById(R.id.twentyPercentTotalValue);
                EditText myTwentyFivePercentTotalValue = (EditText) findViewById(R.id.twentyfivePercentTotalValue);

                //calculate tip
                double eachAmount = amount / party;                        //each person's amount
                int fifteenVal = (int) ((Math.round(eachAmount * 0.15)));   //recommend tip 15%
                int twentyVal = (int) ((Math.round(eachAmount * 0.20)));    //recommend tip 20%
                int twentyFiveVal = (int) ((Math.round(eachAmount * 0.25))); //recommend tip 25%

                //assign tipValue
                myFifteenPercentValue.setText("" + fifteenVal);  //parse to string
                myTwentyPercentValue.setText("" + twentyVal);  //parse to string
                myTwentyFivePercentValue.setText("" + twentyFiveVal);  //parse to string

                //calculate totalValues and assign rthe totals
                myFifteenPercentTotalValue.setText("" + (int) (Math.round(fifteenVal + eachAmount)));
                myTwentyPercentTotalValue.setText("" + (int) (Math.round(twentyVal + eachAmount)));
                myTwentyFivePercentTotalValue.setText("" + (int) (Math.round(twentyFiveVal + eachAmount)));
            } //
        }  //end else
    }  //end computeTipClicked
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
    }
}