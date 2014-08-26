attribute vec3 a_position;
attribute vec3 a_color;

uniform mat4 u_rotationMatrix;
uniform mat4 u_moveMatrix;

varying vec4 vColor;

void main() {
    vColor = vec4(a_color.xyz,0);
    gl_Position = u_rotationMatrix * vec4(a_position.xyz, 1.0f);
}