// a_position is a default name for position attribute
attribute vec2 a_position;

// camera matrix (set programatically in java code)
uniform mat4 u_projTrans;

void main() {
    // compute vertex position using camera matrix
    gl_Position = u_projTrans * vec4(a_position.xy, 0.0, 1.0);
}