uniform mat4 worldMat, viewMat, projMat, invTransWorldMat;
uniform vec3 eyePos, lightPos;
uniform vec3 relPos;
uniform vec3 scaling1;

attribute vec3 position;
attribute vec3 normal;
attribute vec2 texCoord;
attribute vec3 tangent;

varying vec2 v_texCoord;
varying vec3 v_lightDir, v_viewDir;
varying mat3 TangentToWorldSpace;


void main() {
    mat3 temp = mat3(scaling1.x, 0.0, 0.0,
                         0.0, scaling1.y, 0.0,
                         0.0, 0.0, scaling1.z);

    gl_Position = projMat * viewMat * ((worldMat * vec4(temp * position, 1.0)) +vec4(relPos, 0.0));
    v_texCoord = texCoord;

    vec3 nor = mat3(worldMat) * normal; // Vertex normal N
        vec3 tan = mat3(worldMat) * normalize(tangent); //Tangent T
        vec3 bin = cross(nor,tan); // Binormal B  = cross(N,T)
        TangentToWorldSpace = mat3(tan.x,tan.y,tan.z,                    // T | B | N
                                        bin.x,bin.y,bin.z,
                                        nor.x,nor.y,nor.z);

    vec3 posWS = (worldMat * vec4(position, 1.0)).xyz;
    v_lightDir = normalize(lightPos - posWS);
    v_viewDir = normalize(eyePos - posWS);
}



