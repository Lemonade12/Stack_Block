precision mediump float;

uniform sampler2D s_tex0;
uniform vec3 materialDiff, materialSpec, 		  // Md, Ms, Ma, Me
			 materialAmbi, materialEmit;
uniform float materialSh;
uniform vec3 sourceDiff, sourceSpec, sourceAmbi;  // Sd, Ss, Sa

varying vec3 v_lightDir,v_viewDir;
varying vec3 v_normal;
varying vec2 v_texCoord;

struct Material{
	float sh;
	vec3 diff,spec,ambi,emit;
};
struct Light{
	vec3 dir,diff,spec,ambi;
};

vec3 phongLight(vec3 view,vec3 normal,Material M,Light S){
	float diff = max(dot(normal,S.dir),0.0);
	vec3 refl = 2.0 * normal*dot(normal,S.dir)-S.dir;
	float spec = 0.0;
	if(diff > 0.0) spec = pow(max(dot(refl,view),0.0),M.sh);
	vec3 sum = vec3(0.0);
	sum+=diff * S.diff *M.diff;
	sum+=spec*S.spec*M.spec;
	sum+=S.ambi*M.ambi;
	sum+=M.emit;
	return sum;
}

void main() {
	//you should fill in this function
	vec3 materialDiff = texture2D(s_tex0,v_texCoord).xyz;

	Material material = Material(materialSh,materialDiff,materialSpec,materialAmbi,materialEmit);
	Light source = Light(normalize(v_lightDir),sourceDiff,sourceSpec,sourceAmbi);

	vec3 color = phongLight(normalize(v_viewDir),normalize(v_normal),material,source);

	gl_FragColor = vec4(color,1.0);
}
