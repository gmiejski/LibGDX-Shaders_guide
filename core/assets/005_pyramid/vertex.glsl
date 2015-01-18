// a_position is a default name for position attribute (set programatically as ShaderProgram.POSITION_ATTRIBUTE in java code)
attribute vec3 a_position;
// a_color is a default name for color attribute (set programatically as ShaderProgram.COLOR_ATTRIBUTE in java code)
attribute vec4 a_color;

// rotation matrix (set programatically in java code)
uniform mat4 u_rotationMatrix;

// move matrix (set programatically in java code)
uniform mat4 u_moveMatrix;

// color passed to fragment shader (set in main())
varying vec4 vColor;

void main() {
    // we reassingn color to the new variable because "attribute" cannot be declared in fragment shader
    vColor = a_color;

    // compute rotate vertex position on screen
    gl_Position = u_rotationMatrix * vec4(a_position, 1.0);
}