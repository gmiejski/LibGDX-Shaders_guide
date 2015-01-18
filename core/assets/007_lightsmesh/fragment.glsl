#ifdef GL_ES
precision mediump float;
#endif

uniform vec3 u_LightPos;

varying vec3 v_Position;
varying vec4 v_Color;
varying vec3 v_Normal;

void main()
{
    // compute and normalize light vector
    float distance = length(u_LightPos - v_Position);
    vec3 lightVector = normalize(u_LightPos - v_Position);

    // compute dot product between normal vector and light vector
    // consider only values 0.1 or higher so dark fragments are not totally black
    float diffuse = max(dot(v_Normal, lightVector), 0.1);

    // make more distant points darker
    diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance)));

    gl_FragColor = v_Color * diffuse;
}