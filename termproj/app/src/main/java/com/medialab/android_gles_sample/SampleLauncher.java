package com.medialab.android_gles_sample;

import com.medialab.android_gles_sample.sample.ColoringView;


// Type of Sample View

enum ViewType
{
	VIEW_COLOR
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
			default:

				break;


		}

		return curView;

	}


}
