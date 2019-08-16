package org.intranet.graphics.raytrace.shape;

import org.intranet.graphics.raytrace.Shape;

public abstract class TubeLike
	extends Shape
{

	protected double minimum = Double.NEGATIVE_INFINITY;
	public double getMinimum() { return minimum; }
	public void setMinimum(double value) { minimum = value; }

	protected double maximum = Double.POSITIVE_INFINITY;
	public double getMaximum() { return maximum; }
	public void setMaximum(double value) { maximum = value; }

	protected boolean closed;
	public boolean isClosed() { return closed; }
	public void setClosed(boolean value) { closed = value; }

}
