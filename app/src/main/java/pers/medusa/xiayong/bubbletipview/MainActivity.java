package pers.medusa.xiayong.bubbletipview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pers.medusa.xiayong.library.helper.BubbleViewHelper;

public class MainActivity extends AppCompatActivity {

    private boolean inited;
    private Button btn1;
    private BubbleViewHelper helper1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.bt);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper1.show();
            }
        });

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && !inited){
            inited = true;

            helper1 = new BubbleViewHelper();
            helper1.init(btn1, R.layout.view_demo_textview_bubble);
            helper1.dismissBubblePopupWindow();

        }
    }
}
