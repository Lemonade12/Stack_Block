package com.medialab.android_gles_sample.renderer;

import android.util.Log;

/**
 * Created by hobbang5 on 2015-07-22.
 */
public class Vec3 {
    static final String TAG = "Util_Vec3";

    public float x, y, z;

    Vec3() { x = 0.0f; y = 0.0f; z = 0.0f; }
    Vec3(float in) { x = in; y = in; z = in; }

    Vec3(float[] _elem) {
        if (_elem.length >= 3) {
            x = _elem[0]; y = _elem[1]; z = _elem[2];
        }
    }

    Vec3(float _x, float _y, float _z) {
        x = _x;
        y = _y;
        z = _z;
    }

    Vec3(Vec3 in){ x = in.x; y = in.y; z = in.z; }

    public static Vec3 copy(Vec3 in)
    {
        Vec3 ret = new Vec3();
        ret.x = in.x;
        ret.y = in.y;
        ret.z = in.z;

        return ret;
    }

    public float[] getArray() {
        float[] ret = {x, y, z};
        return ret;
    }

    public Vec3 add(Vec3 in)  {
        x += in.x; y += in.y; z += in.z;
        return this;
    }
    public Vec3 add(float in) {
        x += in;   y += in;   z += in;
        return this;
    }
    public Vec3 add(float[] in) {
        if(in.length >= 3)
        x += in[0];   y += in[1];   z += in[2];
        return this;
    }
    public Vec3 sub(Vec3 in)  {
        x -= in.x; y -= in.y; z -= in.z;
        return this;
    }
    public Vec3 sub(float in) {
        x -= in;   y -= in;   z -= in;
        return this;
    }
    public Vec3 mul(Vec3 in)  {
        x *= in.x; y *= in.y; z *= in.z;
        return this;
    }
    public Vec3 mul(float in) {
        x *= in;   y *= in;   z *= in;
        return this;
    }

    public static Vec3 add(Vec3 a, Vec3 b) {
        Vec3 ret = new Vec3();
        ret.x = a.x + b.x;
        ret.y = a.y + b.y;
        ret.z = a.z + b.z;

        return ret;
    }

    public static Vec3 add(Vec3 a, float b) {
        Vec3 ret = new Vec3();
        ret.x = a.x + b;
        ret.y = a.y + b;
        ret.z = a.z + b;

        return ret;
    }
    public static Vec3 add(Vec3 a, float[] b) {
        Vec3 ret = new Vec3();
        if(b.length >= 3) {
            ret.x = a.x + b[0];
            ret.y = a.y + b[1];
            ret.z = a.z + b[2];
        }

        return ret;
    }


    public static Vec3 divide(Vec3 a, Vec3 b) {
        if(b.x == 0 || b.y == 0 || b.z == 0) {
            Log.e(TAG, "Divide by zero");
            return null;
        }

        Vec3 ret = new Vec3();
        ret.x = a.x / b.x;
        ret.y = a.y / b.y;
        ret.z = a.z / b.z;

        return ret;
    }

    public static Vec3 divide(Vec3 a, float b) {
        if(b == 0) {
            Log.e(TAG, "Divide by zero");
            return null;
        }

        Vec3 ret = new Vec3();
        ret.x = a.x / b;
        ret.y = a.y / b;
        ret.z = a.z / b;

        return ret;
    }

    public static Vec3 sub(Vec3 a, Vec3 b) {
        Vec3 ret = new Vec3();
        ret.x = a.x - b.x;
        ret.y = a.y - b.y;
        ret.z = a.z - b.z;

        return ret;
    }

    public static Vec3 sub(Vec3 a, float b) {
        Vec3 ret = new Vec3();
        ret.x = a.x - b;
        ret.y = a.y - b;
        ret.z = a.z - b;

        return ret;
    }

    public static Vec3 mul(Vec3 a, float b) {
        Vec3 ret = new Vec3();
        ret.x = a.x * b;
        ret.y = a.y * b;
        ret.z = a.z * b;

        return ret;
    }

    public static float dot(Vec3 a, Vec3 b) {
        float ret = a.x * b.x + a.y * b.y + a.z * b.z;

        return ret;
    }

    public static Vec3 cross(Vec3 a, Vec3 b) {
        Vec3 ret = new Vec3();
        ret.x = a.y * b.z - a.z * b.y;
        ret.y = a.z * b.x - a.x * b.z;
        ret.z = a.x * b.y - a.y * b.x;

        return ret;
    }

    public Vec3 normalize() {
        float denom = this.length();
        x /= denom;
        y /= denom;
        z /= denom;

        return this;
    }

    public static Vec3 normalize(Vec3 a) {
        Vec3 ret = new Vec3(a);
        float denom = a.length();
        ret.x /= denom;
        ret.y /= denom;
        ret.z /= denom;

        return ret;
    }

    public float length() {
        return (float)Math.sqrt(
                Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public static float length(Vec3 in) {
        return (float)Math.sqrt(
                Math.pow(in.x, 2) + Math.pow(in.y, 2) + Math.pow(in.z, 2));
    }

}
