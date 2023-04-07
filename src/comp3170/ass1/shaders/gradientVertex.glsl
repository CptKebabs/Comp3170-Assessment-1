#version 410

in vec4 a_position;	// vertex position as a homogeneous 3D point in model space
in vec4 a_colour; // vertex colour RGB

uniform mat4 u_mvpMatrix;	// MODEL -> NDC

out vec4 v_colour; // to fragment shader

void main() {
	v_colour = a_colour;
    gl_Position = u_mvpMatrix * a_position;
}

