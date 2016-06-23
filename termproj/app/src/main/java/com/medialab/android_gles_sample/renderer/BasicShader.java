package com.medialab.android_gles_sample.renderer;

import android.opengl.GLES20;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hobbang5 on 2016-01-11.
 */
public class BasicShader {

	private static String TAG = "BasicShader";


	public static int V_ATTRIB_POSITION = 0;
	public static int V_ATTRIB_NORMAL = 1;
	public static int V_ATTRIB_TEX = 2;
	public static int V_ATTRIB_TANGENT = 3;

	private int mProgram;
	private Map<String, Integer> mUniformLocations;

	// Constructor
	BasicShader()
	{
		mProgram = 0;
		mUniformLocations = new HashMap<>();

	}

	public int GetProgram() {
		return mProgram;
	}

	// Like c++ Destructor
	@Override
	protected void finalize() throws Throwable {
		super.finalize();

		if(mProgram > 0)
		{
			GLES20.glDeleteProgram(mProgram);
			BasicUtils.CheckGLerror("glDeleteProgram");
		}
	}


	boolean Use()
	{
		GLES20.glUseProgram(mProgram);

		return BasicUtils.CheckGLerror("glUseProgram");
	}


	int LoadShader(int shaderType, String source)
	{
		int shader = GLES20.glCreateShader(shaderType);

		if (shader > 0)
		{
			GLES20.glShaderSource(shader, source);
			GLES20.glCompileShader(shader);

			int[] compiled = {0};

			GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);

			if (compiled[0] == 0)
			{
				Log.e(TAG, String.format("Could not compile shader %d:\n%s\n",
						shaderType,
						GLES20.glGetShaderInfoLog(shader)));
				GLES20.glDeleteShader(shader);

				//Maybe change the code to Activity.finish();
				System.exit(1);
			}
		}
		return shader;
	}


	int LinkShaders(int... shaders)
	{
		int program = GLES20.glCreateProgram();
		if (program != 0)
		{
			for (int each_shader : shaders)
			{
				GLES20.glAttachShader(program, each_shader);
				BasicUtils.CheckGLerror("glAttachShader");
			}

			// GLES30 doesn't need this
			GLES20.glBindAttribLocation(program, V_ATTRIB_POSITION, "position");
			GLES20.glBindAttribLocation(program, V_ATTRIB_NORMAL, "normal");
			GLES20.glBindAttribLocation(program, V_ATTRIB_TEX, "texCoord");
			GLES20.glBindAttribLocation(program, V_ATTRIB_TANGENT, "tangent");

			GLES20.glLinkProgram(program);
			BasicUtils.CheckGLerror("glLinkProgram");

			int[] linkStatus = {GLES20.GL_FALSE};
			GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
			if (linkStatus[0] != GLES20.GL_TRUE)
			{
				Log.e(TAG, String.format("Could not link program:\n%s\n",
						GLES20.glGetProgramInfoLog(program)));
				GLES20.glDeleteProgram(program);

				//Maybe change the code to Activity.finish();
				System.exit(1);
			}
		}

		return program;
	}


	int CreateProgram(String vertexSource, String fragmentSource)
	{
		int vertexShader = LoadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
		int pixelShader = LoadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);


		if ((vertexShader == 0) || (pixelShader == 0)) return 0;

		mProgram = LinkShaders(vertexShader, pixelShader);
		return mProgram;
	}


	int GetUniformLocation(String name)
	{
		if (!mUniformLocations.containsKey(name))
		{
			mUniformLocations.put(name, GLES20.glGetUniformLocation(mProgram, name));
		}

		return mUniformLocations.get(name);
	}


	void SetUniform(String name, float x, float y, float z)
	{
		int loc = GetUniformLocation(name);
		GLES20.glUniform3f(loc, x, y, z);
		BasicUtils.CheckGLerror("GLES20.glUniform3f");
	}

	void SetUniform(String name, Vec3 v)
	{
		this.SetUniform(name, v.x, v.y, v.z);
	}

	/*
	void SetUniform(String name, const vec4& v)
	{
		int loc = GetUniformLocation(name);
		GLES20.glUniform4f(loc, v.x, v.y, v.z, v.w);
		BasicUtils.CheckGLerror("GLES20.glUniform4f");
	}
	*/

	/*
	void SetUniform(String name, const vec2& v)
	{
		int loc = GetUniformLocation(name);
		GLES20.glUniform2f(loc, v.x, v.y);
		BasicUtils.CheckGLerror("GLES20.glUniform2f");
	}
	*/

	void SetUniform(String name, float[] m)
	{
		int loc = GetUniformLocation(name);
		GLES20.glUniformMatrix4fv(loc, 1, false, m, 0);
		BasicUtils.CheckGLerror("GLES20.glUniformMatrix4fv");
	}

	/*
	void SetUniform(String name, const mat3& m)
	{
		int loc = GetUniformLocation(name);
		GLES20.glUniformMatrix3fv(loc, 1, GL_FALSE, value_ptr(m));
		BasicUtils.CheckGLerror("GLES20.glUniformMatrix3fv");
	}
	*/

	void SetUniform(String name, float val)
	{
		int loc = GetUniformLocation(name);
		GLES20.glUniform1f(loc, val);
		BasicUtils.CheckGLerror("GLES20.glUniform1f");
	}

	void SetUniform(String name, int val)
	{
		int loc = GetUniformLocation(name);
		GLES20.glUniform1i(loc, val);
		BasicUtils.CheckGLerror("GLES20.glUniform1i");
	}

	/*
	void SetUniform(String name, const GLuint& val)
	{
		int loc = GetUniformLocation(name);
		GLES20.glUniform1i(loc, val);
		BasicUtils.CheckGLerror("GLES20.glUniform3f");
	}
	*/

	/*
	void SetUniform(String name, boolean val)
	{
		int loc = GetUniformLocation(name);
		GLES20.glUniform1i(loc, val);
		BasicUtils.CheckGLerror("GLES20.glUniform1i");
	}
	*/
	
}
