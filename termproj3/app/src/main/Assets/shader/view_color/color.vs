uniform mat4 worldMat, viewMat, projMat, invTransWorldMat;
uniform vec3 eyePos, lightPos;
uniform vec3 relPos;

attribute vec3 position;
attribute vec3 normal;
attribute vec2 texCoord;

varying vec3 v_normal;
varying vec2 v_texCoord;
varying vec3 v_lightDir, v_viewDir;


void main() {
    gl_Position = projMat * viewMat * worldMat * vec4(position+relPos, 1.0);
    v_normal = mat3(worldMat) * normal;
    v_texCoord = texCoord;

    vec3 posWS = (worldMat * vec4(position, 1.0)).xyz;
    v_lightDir = normalize(lightPos - posWS);
    v_viewDir = normalize(eyePos - posWS);
}



