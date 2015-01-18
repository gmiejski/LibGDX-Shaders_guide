// a_position is a default name for position attribute (set programatically as ShaderProgram.POSITION_ATTRIBUTE in java code)
attribute vec3 a_position;
// a_color is a default name for color attribute (set programatically as ShaderProgram.COLOR_ATTRIBUTE in java code)
attribute vec3 a_color;
// a_normal is a default name for normal vector attribute (set programatically as ShaderProgram.NORMAL_ATTRIBUTE in java code)
attribute vec3 a_normal;

// rotation matrix (set programatically in java code)
uniform mat4 u_rotationMatrix;

// vertex position passed to fragment shader (set in main())
varying vec3 v_Position;
// vertex color passed to fragment shader (set in main())
varying vec4 v_Color;
// vertex color passed to fragment shader (set in main())
varying vec3 v_Normal;


void main() {
    // compute vertex position on screen (rotate it using rotation maxtrix)
    v_Position = vec3(u_rotationMatrix * vec4(a_position, 0.0));
    // compute normal position on screen (rotate it using rotation maxtrix)
    v_Normal = vec3(u_rotationMatrix * vec4(a_normal, 0.0));
    // reassign color to v_Color so it can be passed to fragment shader
    v_Color = vec4(a_color, 0.0);

    gl_Position = u_rotationMatrix * vec4(a_position, 1.0);
}