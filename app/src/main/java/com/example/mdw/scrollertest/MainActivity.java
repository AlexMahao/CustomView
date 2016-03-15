package com.example.mdw.scrollertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TopBar.TopBarClickListener {

    private TopBar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_vertical_scroll);

       /* mTopBar = ((TopBar) findViewById(R.id.top_bar));
        mTopBar.setTopBarClickListener(this);*/
    }


    @Override
    public void leftClick() {
        Toast.makeText(getApplicationContext(),"left",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void rightClick() {
        Toast.makeText(getApplicationContext(),"right",Toast.LENGTH_SHORT).show();
    }


    public void show(View view){
        Toast.makeText(getApplicationContext(),"one click",Toast.LENGTH_LONG).show();
    }
}
