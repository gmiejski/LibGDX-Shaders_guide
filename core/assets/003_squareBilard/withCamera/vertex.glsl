// a_position is a default name for position attribute (set programatically as ShaderProgram.POSITION_ATTRIBUTE in java code)
attribute vec2 a_position;

// camera matrix (set programatically in java code)
uniform mat4 u_projTrans;
// move matrix (set programatically in java code)
uniform mat4 u_moveMatrix;

// color passed to fragment shader (set in main())
varying vec4 vColor;

void main() {
    // set color to white vec4(float) = [float, float, float, float]
    vColor = vec4(1.0f);

    // compute vertex position on screen and translate it by vector encoded in move matrix
    gl_Position = u_moveMatrix * u_projTrans * vec4(a_position, 0.0, 1.0);
}