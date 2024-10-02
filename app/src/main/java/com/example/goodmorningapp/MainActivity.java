package com.example.goodmorningapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

abstract class SetInterval{
    protected int interval;
    protected boolean isRunning;

    public SetInterval(int delay_ms){
        this.interval = delay_ms;
        this.isRunning = false;
    }
    abstract void callback();
    public void start(){
        Handler h1 = new Handler();
        h1.postDelayed(()->{
            this.callback();
            if (this.isRunning) start();
        }, this.interval);
        this.isRunning = true;
    }
    public void stop(){
        this.isRunning = false;
    }
}

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String [] messages = {"Hello", "Good Morning", "Have a good day", "Love you", "Bye : ]"};
        int [] images = {R.drawable.gm_bg_1, R.drawable.gm_bg_2, R.drawable.gm_bg_3, R.drawable.gm_bg_4, R.drawable.gm_bg_5};

        TextView tv = findViewById(R.id.gm_text);
        ImageView iv = findViewById(R.id.slide_show);
        SetInterval neI = new SetInterval(1000) {
            int i = 1;
            @Override
            void callback() {
                tv.setText(messages[i]);
                iv.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), images[i]));
                i ++;
                if (i >= messages.length){
                    i = 0;
                }
            }
        };

        findViewById(R.id.start_btn).setOnClickListener((View v1)->{
            if (neI.isRunning){
                Toast.makeText(getApplicationContext(), "Already Running...", Toast.LENGTH_LONG).show();
            }
            else {
                neI.start();
            }
        });
        findViewById(R.id.stop_btn).setOnClickListener((View v1)->{neI.stop();});
    }
}