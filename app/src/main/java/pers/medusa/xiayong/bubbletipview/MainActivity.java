package pers.medusa.xiayong.bubbletipview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pers.medusa.xiayong.library.helper.BubbleViewHelper;

public class MainActivity extends AppCompatActivity {

    private boolean inited;
    private Button btn1,btn2,btn3,btn4;
    private BubbleViewHelper helper1,helper2,helper3,helper4;
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
        btn2 = (Button) findViewById(R.id.bt2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper2.show();
            }
        });
        btn3 = (Button) findViewById(R.id.bt3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper3.show();
            }
        });
        btn4 = (Button) findViewById(R.id.bt4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper4.show();
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

            helper2 = new BubbleViewHelper();
            helper2.init(btn2, R.layout.view_demo_textview_bubble2);
            helper2.dismissBubblePopupWindow();

            helper3 = new BubbleViewHelper();
            helper3.init(btn3, R.layout.view_demo_textview_bubble3);
            helper3.dismissBubblePopupWindow();

            helper4 = new BubbleViewHelper();
            helper4.init(btn4, R.layout.view_demo_textview_bubble4);
            helper4.dismissBubblePopupWindow();

        }
    }
}
