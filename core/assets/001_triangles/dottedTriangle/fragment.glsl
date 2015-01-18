#ifdef GL_ES
precision mediump float;
#endif

void main() {

    int f1 = int(gl_FragCoord.x) + int(gl_FragCoord.y);
    int f2 = int(gl_FragCoord.x) - int(gl_FragCoord.y);

    // f1 is responsible for drawing \ lines whereas f2 for / lines
    // white lines are drawn every tenth pixel
    if (mod(f1, 10) != 0 && mod(f2, 10) != 0) {
        // set black color of fragment
        gl_FragColor = vec4(0.0f);
    } else {
        // set white color of fragment
        gl_FragColor = vec4(1.0f);
    }


}