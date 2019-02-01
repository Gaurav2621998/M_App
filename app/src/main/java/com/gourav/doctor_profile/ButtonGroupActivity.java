package com.gourav.doctor_profile;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ButtonGroupActivity extends Activity implements View.OnClickListener{

    private Button[] btn = new Button[8];
    private Button btn_unfocus;
    private int[] btn_id = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radiogroup);

        for(int i = 0; i < btn.length; i++){
            btn[i] = (Button) findViewById(btn_id[i]);
          //  btn[i].setBackgroundColor(Color.rgb(207, 207, 207));
            btn[i].setOnClickListener(this);
        }

        btn_unfocus = btn[0];
    }

    @Override
    public void onClick(View v) {
        //setForcus(btn_unfocus, (Button) findViewById(v.getId()));
        //Or use switch
        switch (v.getId()){
            case R.id.btn0 :
                setFocus(btn_unfocus, btn[0]);
                break;

            case R.id.btn1 :
                setFocus(btn_unfocus, btn[1]);
                break;

            case R.id.btn2 :
                setFocus(btn_unfocus, btn[2]);
                break;

            case R.id.btn3 :
                setFocus(btn_unfocus, btn[3]);
                break;
            case R.id.btn4 :
                setFocus(btn_unfocus, btn[4]);
                break;

            case R.id.btn5 :
                setFocus(btn_unfocus, btn[5]);
                break;

            case R.id.btn6 :
                setFocus(btn_unfocus, btn[6]);
                break;

            case R.id.btn7 :
                setFocus(btn_unfocus, btn[7]);
                break;


        }
    }

    private void setFocus(Button btn_unfocus, Button btn_focus){
        btn_unfocus.setTextColor(Color.rgb(49, 50, 51));
        btn_unfocus.setBackgroundColor(Color.rgb(207, 207, 207));
        btn_focus.setTextColor(Color.rgb(255, 255, 255));
        btn_focus.setBackgroundColor(Color.rgb(3, 106, 150));
        this.btn_unfocus = btn_focus;
    }
}


