package com.medialab.android_gles_sample.renderer;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by hobbang5 on 2016-01-11.
 */
public class BasicUtils {

	private static String TAG = "BasicUtils";


	private BasicUtils()
	{
		// DON'T OVERRIDE THIS CLASS!
	}

	public static boolean CheckGLerror(String op)
	{
		int error = GLES20.glGetError();
		if(error != GLES20.GL_NO_ERROR)
		{
			Log.e(TAG, String.format("after %s() glError (0x%x)\n", op, error));
			return false;
		}

		return true;

	}

	public static void PrintGLstring(String name, int s)
	{
		Log.i(TAG, String.format("GL %s = %s\n", name, GLES20.glGetString(s)));
	}
}
