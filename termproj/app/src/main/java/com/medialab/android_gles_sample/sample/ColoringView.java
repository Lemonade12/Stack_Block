package com.medialab.android_gles_sample.sample;

import com.medialab.android_gles_sample.R;
import com.medialab.android_gles_sample.SampleView;
import com.medialab.android_gles_sample.renderer.FileLoader;
import com.medialab.android_gles_sample.renderer.TexData;

import java.io.InputStream;

public class ColoringView extends SampleView {

	@Override
	public void OnInit()
	{
		String vs = FileLoader.ReadTxtFile(this, "shader/view_color/color.vs");
		String fs = FileLoader.ReadTxtFile(this, "shader/view_color/color.fs");
		mRenderer.SetProgram(vs, fs);

		InputStream block = FileLoader.GetStream(this, "obj3d/block");


		TexData[] textJ = new TexData[1];
		TexData[] normal_texJ = new TexData[1];

		textJ[0] = FileLoader.ReadTexture(this, R.drawable.pyramid);
		normal_texJ[0] = FileLoader.ReadTexture(this, R.drawable.pyramid_n6);

		mRenderer.SetNewModel(block);
		mRenderer.SetTexture(TexData.Type.TEXDATA_GENERAL, textJ);
		mRenderer.SetTexture(TexData.Type.TEXDATA_NORMAL_MAP, normal_texJ);

		mRenderer.Initialize();

		//mViewRenderer->OffAutoRotate();
		mRenderer.GetCamera().SetEye(30.0f, 30.0f, 30.0f);
		mRenderer.GetCamera().SetAt(0, 0, 0);
	}

}
