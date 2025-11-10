// Vignette effect fragment shader
varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform vec4 u_viewport;   // x, y, width, height
uniform vec2 u_resolution; // viewport resolution

//vec2 getUV(vec2 pos, vec2 offset) {
//    return (2.0 * (pos.xy + offset) - u_resolution.xy) / u_resolution.y;
//}

vec2 projectToViewportUV(vec2 pos) {
    vec2 uv;
    uv.x = (pos.x) / (1.0 * u_viewport.z);
    uv.y = (pos.y) / (1.0 * u_viewport.w);
    uv.x -= u_viewport.x / (u_viewport.z);
    uv.y -= u_viewport.y / (u_viewport.w);
    return uv;
}

void main() {
    // fetch sprite texel color
    vec4 colour = texture2D(u_texture, v_texCoords) * v_color;

    // gl_FragColor = vec4(getUV(gl_FragCoord.xy, vec2(0.0)), 0.0, 1.0);
    //gl_FragColor = colour;
    // gl_FragColor = center(gl_FragCoord.xy);

    vec2 uv = projectToViewportUV(gl_FragCoord.xy);

    float dist = distance(uv, vec2(0.5, 0.5));
    float vignette = smoothstep(0.6, 0.0, dist);
    colour.rgb *= vignette;

    // make greyscale
//     float grey = dot(colour.rgb, vec3(0.299, 0.587, 0.114));
//     colour = vec4(vec3(grey), colour.a);

    gl_FragColor = colour;
    //gl_FragColor = vec4(uv, 0.0, 1.0);
}
