package org.intranet.graphics.raytrace.steps;

public abstract class StepsParent
{
	protected final RaytraceData data;
	public static final String wordPattern = "([a-zA-Z_][a-zA-Z0-9_]*)";
	public static final String twoWordPattern = wordPattern + ", " + wordPattern;
	public static final String threeWordPattern = twoWordPattern + ", " + wordPattern;
	public static final String fourWordPattern = threeWordPattern + ", " + wordPattern;
	public static final String intPattern = "(-?\\d+)";
	public static final String twoIntsPattern = intPattern + ", " + intPattern;
	public static final String threeIntsPattern = twoIntsPattern + ", " + intPattern;
	public static final String doublePattern = "(-?\\d+\\.?\\d*)";
	public static final String fourDoublesPattern = doublePattern + ", " + doublePattern + ", " + doublePattern + ", " + doublePattern;
	public static final String threeDoublesPattern = doublePattern + ", " + doublePattern + ", " + doublePattern;
	public static final String sixDoublesPattern = threeDoublesPattern + ", " + threeDoublesPattern;

	protected StepsParent(RaytraceData data)
	{
		this.data = data;
	}
}
