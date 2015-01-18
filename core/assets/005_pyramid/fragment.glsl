#ifdef GL_ES
precision mediump float;
#endif

// color (set in vertex shader)
varying vec4 vColor;

void main() {
    gl_FragColor = vColor;
}