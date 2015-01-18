// a_position is a default name for position attribute (set programatically as ShaderProgram.POSITION_ATTRIBUTE in java code)
attribute vec2 a_position;

// move matrix (set programaticall in java code)
uniform mat4 u_moveMatrix;

void main() {
    // compute vertex position on screen and translate it by vector encoded in move matrix (without camera matrix)
    gl_Position = u_moveMatrix * vec4(a_position, 0.0, 1.0);
}