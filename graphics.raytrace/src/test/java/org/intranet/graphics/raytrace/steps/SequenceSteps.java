package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.shape.Sequence;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class SequenceSteps
	extends StepsParent
{
	public SequenceSteps(RaytraceData data)
	{
		super(data);
	}

	@Given("{identifier} ← {sequence3}")
	public void genSequence(String sequenceName, Sequence s)
	{
		data.putSequence(sequenceName, s);
	}

	@Then("next\\({identifier}) = {double}")
	public void nextGen(String sequenceName, double expectedValue)
	{
		Sequence s = data.getSequence(sequenceName);
		double actualValue = s.next();

		Assert.assertEquals(expectedValue, actualValue, Tuple.EPSILON);
	}
}
