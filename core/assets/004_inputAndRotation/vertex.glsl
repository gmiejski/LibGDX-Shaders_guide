// a_position is a default name for position attribute (set programatically as ShaderProgram.POSITION_ATTRIBUTE in java code)
attribute vec2 a_position;

// rotation matrix (set programatically in java code)
uniform mat4 u_rotationMatrix;
// move matrix (set programatically in java code)
uniform mat4 u_moveMatrix;

void main() {
    // first rotate vertex then translate it - will always rotate relative to triangle edge
    gl_Position = u_moveMatrix * u_rotationMatrix * vec4(a_position, 0.0, 1.0);
}