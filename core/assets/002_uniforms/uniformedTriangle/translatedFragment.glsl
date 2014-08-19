#ifdef GL_ES
precision mediump float;
#endif

varying vec4 vColor;
uniform float u_scale;

void main() {
    gl_FragColor = vColor;
}