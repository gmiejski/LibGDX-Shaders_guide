#ifdef GL_ES
precision mediump float;
#endif

// color (set programatically in java code)
uniform vec4 u_colorFlash;

void main() {
    gl_FragColor = u_colorFlash;
}