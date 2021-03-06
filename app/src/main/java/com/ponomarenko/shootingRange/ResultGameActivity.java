package com.ponomarenko.shootingRange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ResultGameActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_AMOUNT_KILLED_ENEMIES = "killedEnemies";
    public static final String KEY_SPENT_TIME = "spentTime";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_game);

        Button okBtn = (Button) findViewById(R.id.okBtn);
        okBtn.setOnClickListener(this);

        Button resetBtn = (Button) findViewById(R.id.restartBtn);
        resetBtn.setOnClickListener(this);

        int amountKilledEnemies = getIntent().getIntExtra(KEY_AMOUNT_KILLED_ENEMIES, 0);
        TextView killedAmount = (TextView) findViewById(R.id.killed_enemies_tv);
        killedAmount.setText(String.valueOf(amountKilledEnemies));


        TextView spentTimeTV = (TextView) findViewById(R.id.spent_time_tv);
        String spentTime = getIntent().getIntExtra(KEY_SPENT_TIME, 0) + " sec";
        spentTimeTV.setText(spentTime);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.okBtn:
                Intent runStartActivity = new Intent(ResultGameActivity.this, StartActivity.class);
                runStartActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(runStartActivity);
                break;

            case R.id.restartBtn:
                Intent restartIntent = new Intent();
                restartIntent.setClass(this, MainActivity.class);
                restartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(restartIntent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent runStartActivity = new Intent(ResultGameActivity.this, StartActivity.class);
        runStartActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(runStartActivity);
    }
}
