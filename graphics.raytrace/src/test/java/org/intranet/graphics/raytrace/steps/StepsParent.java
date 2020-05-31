package org.intranet.graphics.raytrace.steps;

public abstract class StepsParent
	implements StepPatterns
{
	protected final RaytraceData data;

	protected StepsParent(RaytraceData data)
	{
		this.data = data;
	}
}
