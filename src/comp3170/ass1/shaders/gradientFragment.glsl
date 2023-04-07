#version 410

in vec4 v_colour; // RGB from vertex shader

layout(location = 0) out vec4 o_colour;	// RGBA

void main() {
    o_colour = v_colour;
}

