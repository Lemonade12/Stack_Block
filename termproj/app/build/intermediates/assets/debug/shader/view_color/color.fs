precision mediump float;

varying vec3 v_normal;

void main() {
	gl_FragColor = vec4(0.5 * normalize(v_normal) + vec3(0.5), 1.0);
}