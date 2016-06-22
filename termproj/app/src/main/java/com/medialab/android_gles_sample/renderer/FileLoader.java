package com.medialab.android_gles_sample.renderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by hobbang5 on 2016-01-12.
 */
public class FileLoader {

	private	FileLoader()
	{

	}

	public static TexData ReadTexture(Context c, int resId)
	{
		Bitmap tex = BitmapFactory.decodeResource(c.getResources(), resId);
		ByteBuffer buffer = ByteBuffer.allocateDirect(tex.getByteCount()).order(ByteOrder.nativeOrder());
		tex.copyPixelsToBuffer(buffer);
		buffer.position(0);

		TexData out = new TexData();
		out.SetData(tex.getByteCount(), buffer, tex.getWidth(), tex.getHeight(), GLES20.GL_RGBA);

		return out;
	}

	public static String ReadTxtFile(Context c, String filename)
	{
		return GetString(GetStream(c, filename));
	}

	public static InputStream GetStream(Context c, String filename)
	{
		InputStream is = null;

		try {
			is = c.getAssets().open(filename);

		} catch (IOException e) {
			e.printStackTrace();

		}

		return is;

	}

	// convert InputStream to String
	public static String GetString(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return sb.toString();

	}

}
