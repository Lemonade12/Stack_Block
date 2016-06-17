package com.medialab.android_gles_sample.renderer;

import android.util.Log;

/**
 * Created by hobbang5 on 2015-07-27.
 */
public class Quat {
    static final String TAG = "Util_Quat";

    public float x, y, z, w;

    Quat() { x = 0.0f; y = 0.0f; z = 0.0f; w = 0.0f; }
    Quat(float in) { x = in; y = in; z = in; w = in; }

    Quat(float[] _elem) {
        if (_elem.length == 4) {
            x = _elem[0]; y = _elem[1]; z = _elem[2]; w = _elem[3];
        }
    }

    Quat(Quat in){ x = in.x; y = in.y; z = in.z; w = in.w; }

    Quat(float _x, float _y, float _z, float _w) {
        x = _x;
        y = _y;
        z = _z;
        w = _w;
    }

    Quat(Vec3 in) {
        x = in.x;
        y = in.y;
        z = in.z;
        w = 0;
    }

    public float[] getArray() {
        float[] ret = {x, y, z, w};
        return ret;
    }

    public Quat add(Quat in)  {
        x += in.x; y += in.y; z += in.z; w += in.w;
        return this;
    }
    public Quat add(float in) {
        x += in;   y += in;   z += in; w += in;
        return this;
    }
    public Quat sub(Quat in)  {
        x -= in.x; y -= in.y; z -= in.z; w -= in.w;
        return this;
    }
    public Quat sub(float in) {
        x -= in;   y -= in;   z -= in; w -= in;
        return this;
    }
    public Quat mul(float in) {
        x *= in;   y *= in;   z *= in; w *= in;
        return this;
    }

    public static Quat add(Quat a, Quat b) {
        Quat ret = new Quat();
        ret.x = a.x + b.x;
        ret.y = a.y + b.y;
        ret.z = a.z + b.z;
        ret.w = a.w + b.w;

        return ret;
    }

    public static Quat add(Quat a, float b) {
        Quat ret = new Quat();
        ret.x = a.x + b;
        ret.y = a.y + b;
        ret.z = a.z + b;
        ret.w = a.w + b;

        return ret;
    }

    public static Quat sub(Quat a, Quat b) {
        Quat ret = new Quat();
        ret.x = a.x - b.x;
        ret.y = a.y - b.y;
        ret.z = a.z - b.z;
        ret.w = a.w - b.w;

        return ret;
    }

    public static Quat sub(Quat a, float b) {
        Quat ret = new Quat();
        ret.x = a.x - b;
        ret.y = a.y - b;
        ret.z = a.z - b;
        ret.w = a.w - b;

        return ret;
    }

    public static Quat mul(Quat a, Quat b) {
        Quat ret = new Quat();
        Vec3 v1 = new Vec3(a.x, a.y, a.z);
        Vec3 v2 = new Vec3(b.x, b.y, b.z);
        ret.w = a.w * b.w - Vec3.dot(v1, v2);
        Vec3 v = Vec3.cross(v1, v2);
        v1.mul(b.w);
        v2.mul(a.w);
        v.add(v1).add(v2);
        ret.x = v.x;
        ret.y = v.y;
        ret.z = v.z;

        return ret;

    }

    public static Quat mul(Quat a, float b) {
        Quat ret = new Quat();
        ret.x = a.x * b;
        ret.y = a.y * b;
        ret.z = a.z * b;
        ret.w = a.w * b;

        return ret;
    }

    public Quat normalize() {
        float denom = (float)Math.sqrt(x * x + y * y + z * z + w * w);
        x /= denom;
        y /= denom;
        z /= denom;
        w /= denom;

        return this;
    }

    public static Quat normalize(Quat a) {
        Quat ret = new Quat(a);
        float denom = a.length();
        ret.x /= denom;
        ret.y /= denom;
        ret.z /= denom;
        ret.w /= denom;

        return ret;
    }

    public float length() {
        return (float)Math.sqrt(
                Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2) + Math.pow(w, 2));
    }

    public static float length(Quat in) {
        return (float)Math.sqrt(
                Math.pow(in.x, 2) + Math.pow(in.y, 2) + Math.pow(in.z, 2) +  Math.pow(in.w, 2));
    }

    public static Quat convertRotationMatToQuat(float[] m){
        if(m.length != 9){
            Log.e(TAG, "convertRotationMatToQuat():: input matrix must be 3 by 3");
            return null;
        }
        Quat ret = new Quat();
        ret.w =  0.5f * (float)Math.sqrt(1.0f+m[0]+m[4]+m[8]); //w = root(1+r_11+r_22+r_33)/2
        ret.x = (m[5] - m[7]) / (4.0f * ret.w);
        ret.y = (m[6] - m[2]) / (4.0f * ret.w);
        ret.z = (m[1] - m[3]) / (4.0f * ret.w);

        return ret;
    }

    public static Quat getRoationQuat(float angle, Vec3 axis){
        Vec3 u = Vec3.normalize(axis);
        Quat ret = new Quat();
        ret.w = (float)Math.cos(Math.toRadians(angle) / 2.0f);
        u.mul((float) Math.sin(Math.toRadians(angle) / 2.0f));
        ret.x = u.x;
        ret.y = u.y;
        ret.z = u.z;

        return ret;
    }

    public static Quat conjugateQuat(Quat in)
    {
        Quat ret = new Quat();
        ret.x = -in.x;
        ret.y = -in.y;
        ret.z = -in.z;
        ret.w = in.w;

        return  ret;

    }



}
