package com.medialab.android_gles_sample;

import com.medialab.android_gles_sample.sample.ColoringView;
import com.medialab.android_gles_sample.sample.FragLightingView;
import com.medialab.android_gles_sample.sample.NormalMappingView;


// Type of Sample View
enum ViewType
{
	VIEW_COLOR,
	VIEW_FRAG_LIGHT,
	VIEW_NORMAL
}


public class SampleLauncher {

	// class singleton instance
	private static SampleLauncher instance = new SampleLauncher();
	private SampleView curView;


	private SampleLauncher()
	{
		//Singleton class
	}

	public static SampleLauncher getInstance()
	{
		return instance;
	}


	public SampleView InitSampleView(ViewType type)
	{
		switch (type)
		{
			case VIEW_COLOR:
				curView = new ColoringView();
				break;
			case VIEW_FRAG_LIGHT:
				curView = new FragLightingView();
				break;
			case VIEW_NORMAL:
				curView = new NormalMappingView();
				break;

			default:

				break;


		}

		return curView;

	}


}
