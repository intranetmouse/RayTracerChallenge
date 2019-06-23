package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import org.intranet.graphics.raytrace.primitive.Matrix;

enum SphereProjectionType
{
	NORMAL("Normal", null),
	SHRINK_Y("Shrink Y", Matrix.newScaling(1, 0.5, 1)),
	SHRINK_X("Shrink X", Matrix.newScaling(0.5, 1, 1)),
	SHRINK_ROTATE("Shrink + Rotate", Matrix.newRotationZ(Math.PI / 4).multiply(Matrix.newScaling(0.5, 1, 1))),
	SHRINK_SKEW("Shink + Skew", Matrix.shearing(1, 0, 0, 0, 0, 0).multiply(Matrix.newScaling(0.5, 1, 1)));

	private final String name;
	public String getName() { return name; }

	private Matrix transform;
	public Matrix getTransform() { return transform; }

	private SphereProjectionType(String name, Matrix t) {
		this.name = name;
		transform = t;
	}
}