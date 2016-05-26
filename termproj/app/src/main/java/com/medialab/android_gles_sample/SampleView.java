package com.medialab.android_gles_sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medialab.android_gles_sample.renderer.BasicRenderer;

public abstract class SampleView extends Activity {
    private GLView mGLView;
    private GLViewCallback mGLViewCallback;
    protected BasicRenderer mRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGLView = new GLView(this);
        mGLViewCallback = new GLViewCallback(this);
        mRenderer = new BasicRenderer();


        mGLView.setRenderer(mGLViewCallback);
        setContentView(mGLView);
        addUi();

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
