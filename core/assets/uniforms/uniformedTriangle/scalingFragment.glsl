#ifdef GL_ES
precision mediump float;
#endif

varying vec4 vColor;
uniform float u_scale;

void main() {
    gl_FragColor = vec4(vec3(u_scale),1.0f);
}