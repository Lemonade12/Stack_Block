package com.medialab.android_gles_sample.renderer;

import android.opengl.Matrix;

public class BasicCamera {

	public static double TORADIAN = Math.PI / 180.0;
	public static float ROTATION_VEL = 45.0f;
	public static float ZOOM_MIN_LENGTH = 5.0f;

	Vec3 mEye;
	float mDist;
	Vec3 mUp;
	Vec3 mFw;
	Vec3 mAngle;

	float m_zNear;
	float m_zFar;

	Vec3 mAt;

	float[] mLookatMat;
	float[] mPerspectiveMat;

	int direction = 1;
	BasicCamera()
	{
		mEye = new Vec3(150.0f, 25.0f, -100.f);
		mDist = 150.0f;
		mUp = new Vec3(0, 1.0f, 0.0f);
		mFw = new Vec3(0, 0, -1.0f);
		mAngle = new Vec3(0, -45.0f, 0);
		m_zNear = 2.0f;
		m_zFar = 10000.0f;
		mAt = new Vec3(0);
		mPerspectiveMat = new float[16];
		mLookatMat = new float[16];

		mAt = new Vec3(0);
		mDist = Vec3.length(Vec3.sub(mEye, mAt));
		UpdateAngle();
	}

	Vec3 projToVector(Vec3 target, Vec3 axis)
	{
		float numerator = Vec3.dot(target, axis);
		float denom = Vec3.length(axis);
		denom *= denom;
		denom = 1.0f / denom;

		return Vec3.mul(axis, numerator * denom);
	}

	Vec3 projToPlane(Vec3 target, Vec3 normal)
	{
		return Vec3.sub(target, projToVector(target, normal));
	}

	double getAngle(Vec3 a, Vec3 b)
	{
		return Math.acos(Vec3.dot(Vec3.normalize(a), Vec3.normalize(b)));
	}

	void limitAngle(double angle)
	{
		if (angle > 180.0) angle -= 360.0;
		else if (angle < -180.0) angle += 360.0;
	}

	void ComputePerspective(float fovy_degree, int w, int h)
	{
		Matrix.perspectiveM(mPerspectiveMat, 0,
				fovy_degree, (float)w/(float)h, m_zNear, m_zFar);
	}

	public float[] GetPerspectiveMat() {
		return mPerspectiveMat;
	}

	public float[] GetViewMat() {
		if(direction==1) {
			MoveForward(0.2f);
			if(mAt.z<-10) direction = 0;
		}else if(direction ==0){
			MoveBackward(0.2f);
			if(mAt.z>10) direction = 1;
		}
		Matrix.setLookAtM(mLookatMat, 0,
				mEye.x, mEye.y, mEye.z,
				mAt.x, mAt.y, mAt.z,
				mUp.x, mUp.y, mUp.z);
		return mLookatMat;
	}


	void UpdateAngle()
	{
		Vec3 dir = Vec3.sub(mAt, mEye);
		Vec3 projDir = projToPlane(dir, mUp);
		if (Vec3.dot(dir, mUp) > 0)
		{
			mAngle.y = (float)(getAngle(projDir, dir) / TORADIAN); // pitch
		}
		else
		{
			mAngle.y = (float) (-getAngle(projDir, dir) / TORADIAN);
		}
		mAngle.x = (float) (getAngle(mFw, projDir) / TORADIAN); // yaw
	}


	void UpdateAt()
	{
		float [] rotateMat = new float[16];
		float [] dirVec = new float[4];
		dirVec[0] = mFw.x;
		dirVec[1] = mFw.y;
		dirVec[2] = mFw.z;
		dirVec[3] = 0;

//		Matrix.setRotateEulerM(rotateMat, 0, mAngle.x, mAngle.y, mAngle.z);
		Matrix.setRotateM(rotateMat, 0, mAngle.x, mUp.x, mUp.y, mUp.z);
		Matrix.multiplyMV(dirVec, 0, rotateMat, 0, dirVec, 0);
		Vec3 pitchAxis = Vec3.cross(new Vec3(dirVec), mUp);

		Matrix.setRotateM(rotateMat, 0, mAngle.y, pitchAxis.x, pitchAxis.y, pitchAxis.z);
		Matrix.multiplyMV(dirVec, 0, rotateMat, 0, dirVec, 0);

		Vec3 dir = new Vec3(dirVec);
		mAt = dir.mul(mDist).add(mEye);

	}

	void RotateAuto(double deltaTime)
	{
		double degree = ROTATION_VEL * deltaTime;

		// Add yaw angle;
		mAngle.x += degree;
		limitAngle(mAngle.x);

		Vec3 prevAt = mAt;
		UpdateAt();

		Vec3 deltaAt = Vec3.sub(prevAt, mAt);

		mAt = prevAt;
		mEye.add(deltaAt);
	}


/*
	void BasicCamera::ZoomIn(const float& vel)
	{
		vec3 dir = mAt - mEye;
		float remain = glm::length(dir);
		remain -= ZOOM_MIN_LENGTH;
		dir = normalize(dir);

		remain = (remain > vel) ? vel : remain;
		dir *= remain;
		mEye += dir;
		mDist -= remain;
	}
*/

/*
	void BasicCamera::ZoomOut(const float& vel)
	{
		vec3 dir = mEye - mAt;
		dir = normalize(dir);
		dir *= vel;
		mEye += dir;
		mDist += vel;
	}
*/


	public void SetEye(float x, float y, float z)
	{
		mEye.x = x;
		mEye.y = y;
		mEye.z = z;
		mDist = Vec3.sub(mEye, mAt).length();
		UpdateAngle();
	}

	public void SetAt(float x, float y, float z)
	{
		mAt.x = x;
		mAt.y = y;
		mAt.z = z;
		mDist = Vec3.sub(mEye, mAt).length();
		UpdateAngle();
	}

	Vec3 GetEye()
	{
		return mEye;
	}

	Vec3 GetAt()
	{
		return mAt;
	}

	void MoveForward(float vel)
	{
		Vec3 abc = new Vec3(0,0,25);
		Vec3 dir = Vec3.sub(mAt, abc);
		dir.normalize().mul(vel);
		mEye.add(dir);
		UpdateAt();
	}

	void MoveBackward(float vel)
	{
		Vec3 abc = new Vec3(0,0,25);
		Vec3 dir = Vec3.sub(abc, mAt);
		dir.normalize().mul(vel);
		mEye.add(dir);
		UpdateAt();
	}

	void MoveRight(float vel)
	{
		Vec3 abc = new Vec3(25,0,0);
		Vec3 dir = Vec3.sub(abc, mAt);
		dir.normalize();
		dir = Vec3.cross(mUp, dir).mul(-vel);
		mEye.add(dir);
		UpdateAt();
	}

	void MoveLeft(float vel)
	{
		Vec3 abc = new Vec3(25,0,0);
		Vec3 dir = Vec3.sub(abc, mAt);
		dir.normalize();
		dir = Vec3.cross(mUp, dir).mul(vel);
		mEye.add(dir);
		UpdateAt();
	}

	void MoveUp(float vel)
	{
		Vec3 dir = Vec3.mul(mUp, vel);
		mEye.add(dir);
		UpdateAt();
	}

	void MoveDown(float vel)
	{
		Vec3 dir = Vec3.mul(mUp, -vel);
		mEye.add(dir);
		UpdateAt();
	}

}
