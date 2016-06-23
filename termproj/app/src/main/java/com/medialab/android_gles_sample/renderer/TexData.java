package com.medialab.android_gles_sample.renderer;

import java.nio.ByteBuffer;

/**
 * Created by hobbang5 on 2016-01-12.
 */

public class TexData {

	public enum Type {
		TEXDATA_GENERAL,
		TEXDATA_NORMAL_MAP
	}

	ByteBuffer pixels;
	int width;
	int height;
	int format;

	TexData()
	{
		width = 0;
		height = 0;
		format = 0;
	}

	public void SetData(int size, ByteBuffer source,
				   int w, int h, int fm)
	{
		pixels = source;
		width = w;
		height = h;
		format = fm;
	}

}
