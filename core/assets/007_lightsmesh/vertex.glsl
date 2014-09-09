attribute vec3 a_position;
attribute vec3 a_color;
attribute vec3 a_normal;

uniform mat4 u_rotationMatrix;
uniform mat4 u_moveMatrix;

varying vec3 v_Position;
varying vec4 v_Color;
varying vec3 v_Normal;


void main() {
    v_Position = vec3(u_rotationMatrix * vec4(a_position, 0.0f));
    v_Normal = vec3(u_rotationMatrix * vec4(a_normal, 0.0f));
    v_Color = vec4(a_color.xyz,0);

    gl_Position = u_rotationMatrix * vec4(a_position.xyz, 1.0f);
}