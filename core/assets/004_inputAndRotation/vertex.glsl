attribute vec2 a_position;
attribute vec3 a_color;

uniform mat4 u_rotationMatrix;
uniform mat4 u_moveMatrix;

varying vec4 vColor;

void main() {
    vColor = vec4(1.0f);
    //gl_Position =   u_rotationMatrix * u_moveMatrix  * vec4(a_position.xy, 0.0, 1.0); // first translates, then rotates - will rotate relative to axis
    gl_Position =   u_moveMatrix * u_rotationMatrix  * vec4(a_position.xy, 0.0, 1.0); // first rotates then translates - will always rotate relative to triangle edge
}