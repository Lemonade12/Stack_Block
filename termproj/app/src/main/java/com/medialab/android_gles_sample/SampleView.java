package com.medialab.android_gles_sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import com.medialab.android_gles_sample.renderer.BasicRenderer;
import com.medialab.android_gles_sample.renderer.BasicCamera;

public abstract class SampleView extends Activity {
    private GLView mGLView;
    private GLViewCallback mGLViewCallback;
    public BasicRenderer mRenderer;
    public BasicCamera mCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGLView = new GLView(this);
        mGLViewCallback = new GLViewCallback(this);
        mRenderer = new BasicRenderer();
        mCamera = new BasicCamera();

        mGLView.setRenderer(mGLViewCallback);

        setContentView(mGLView);

        addUi();
        Button btn = (Button)findViewById(R.id.button);
        Button btn6 = (Button)findViewById(R.id.button6);
        Button btn2 = (Button)findViewById(R.id.button2);
        Button btn3 = (Button)findViewById(R.id.button3);
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mRenderer.ButtonClick();
            }
        });
        btn6.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mRenderer.Button6Click();
            }
        });
        btn2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mRenderer.Button2Click();
            }
        });
        btn3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mRenderer.Button3Click();
            }
        });

        findViewById(R.id.button4). setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mRenderer.Button4clickoff();
            }
        });
        findViewById(R.id.button4).setOnLongClickListener(new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                mRenderer.Button4clickset();
                return false;
            }
        });
        findViewById(R.id.button5).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mRenderer.Button5clickoff();
            }
        });
        findViewById(R.id.button5).setOnLongClickListener(new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                mRenderer.Button5clickset();
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        mGLView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mGLView.onResume();
        super.onResume();
    }

    public void addUi() {
        View btnLayout = getLayoutInflater().inflate(R.layout.sample_ui, null);
        this.addContentView(btnLayout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        mGLViewCallback.tvFpsText  = (TextView) findViewById(R.id.tvFps);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){

        switch (e.getAction()) {
            case MotionEvent.ACTION_UP:
                mRenderer.TouchOff();
                break;

            case MotionEvent.ACTION_MOVE:
                mRenderer.SetTouchPoint(e.getX(), e.getY());
                break;

            case MotionEvent.ACTION_DOWN:
                mRenderer.TouchOn();
                mRenderer.SetTouchPoint(e.getX(), e.getY());
                break;
            case MotionEvent.ACTION_BUTTON_PRESS:
                System.out.println("pressed");
                break;
        }

        return super.onTouchEvent(e);
    }


    protected abstract void OnInit();

    protected void OnWindowUpdate(int w, int h)
    {
        mRenderer.SetViewPort(w, h);
    }

    protected void OnStep()
    {
        mRenderer.RenderFrame();
    }


}
