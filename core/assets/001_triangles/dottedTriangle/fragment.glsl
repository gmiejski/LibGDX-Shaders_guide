#ifdef GL_ES
precision mediump float;
#endif

varying vec4 vColor;

void main() {

    int f1 = int(gl_FragCoord.x) + int(gl_FragCoord.y);
    int f2 = int(gl_FragCoord.x) - int(gl_FragCoord.y);

    if ( mod(f1 ,10) != 0 && mod(f2 ,10) != 0) {
        gl_FragColor = vec4(0.0f);
    } else {
        gl_FragColor = vec4(1.0f);
    }


}