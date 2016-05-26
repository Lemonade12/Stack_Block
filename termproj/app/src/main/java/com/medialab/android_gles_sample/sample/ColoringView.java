package com.medialab.android_gles_sample.sample;

import com.medialab.android_gles_sample.SampleView;
import com.medialab.android_gles_sample.joml.Matrix4f;
import com.medialab.android_gles_sample.joml.Vector4f;
import com.medialab.android_gles_sample.renderer.FileLoader;

import java.io.InputStream;

public class ColoringView extends SampleView {
	float prevtime = System.currentTimeMillis();
	float deltatime;
	@Override
	public void OnInit()
	{
		String vs = FileLoader.ReadTxtFile(this, "shader/view_color/color.vs");
		String fs = FileLoader.ReadTxtFile(this, "shader/view_color/color.fs");
		mRenderer.SetProgram(vs, fs);

		InputStream teapot = FileLoader.GetStream(this, "obj3d/teapot");

	//	TexData[] textJ = new TexData[1];
	//	textJ[0] = FileLoader.ReadTexture(this, R.drawable.tex_c_brick);

		mRenderer.SetNewModel(teapot);
	//	mRenderer.SetTexture(TexData.Type.TEXDATA_GENERAL, textJ);

		mRenderer.Initialize();

		//mViewRenderer->OffAutoRotate();
		mRenderer.GetCamera().SetEye(25.0f, 25.0f, 25.0f);
		mRenderer.GetCamera().SetAt(0, 0, 0);


	}

}
