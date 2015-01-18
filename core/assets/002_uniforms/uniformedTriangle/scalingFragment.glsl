#ifdef GL_ES
precision mediump float;
#endif

// fragment color (set in vertex shader)
varying vec3 vColor;

void main() {
    gl_FragColor = vec4(vColor, 1.0);
}