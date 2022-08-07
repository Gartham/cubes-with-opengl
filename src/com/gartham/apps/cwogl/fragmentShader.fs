#version 330

out vec4 outputColor;

in vec3 pos;

void main()
{
	outputColor = vec4(pos.x, pos.y, pos.z, 1f);
}