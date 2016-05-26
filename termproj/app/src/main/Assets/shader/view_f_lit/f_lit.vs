uniform mat4 worldMat, viewMat, projMat, invTransWorldMat;
uniform vec3 eyePos, lightPos;

attribute vec3 position;
attribute vec3 normal;
attribute vec2 texCoord;
//여기는 무대를 마련하는곳이다.
//you should define your output variables
varying vec3 v_normal;
varying vec3 v_lightDir,v_viewDir;
varying vec2 v_texCoord;
//varying vec3 ___
//...

void main() {
    gl_Position = projMat * viewMat * worldMat * vec4(position,1.0);
    v_texCoord = texCoord;
    v_normal = mat3(worldMat) * normal;

    vec3 posWS = (worldMat*vec4(position,1.0)).xyz;
    v_lightDir = normalize(lightPos-posWS);
    v_viewDir = normalize(eyePos - posWS);
    // you should fill in this function
}

