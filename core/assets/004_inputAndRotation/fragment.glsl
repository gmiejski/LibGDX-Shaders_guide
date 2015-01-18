#ifdef GL_ES
precision mediump float;
#endif

void main() {
    // compute Euclidean distance between 0 point (left bottom corner of screen) and fragment coordinates
    float distanceFactor = sqrt(gl_FragCoord.x * gl_FragCoord.x + gl_FragCoord.y * gl_FragCoord.y);

    // compute fragment color basing on distance between fragment and left bottom corner of the screen
    // fragment rendered closer to left bottom corner of the screen is darker
    vec3 color = vec3(distanceFactor / 512);
    gl_FragColor = vec4(color, 1.0);
}