package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.Cone;
import org.intranet.graphics.raytrace.shape.Cube;
import org.intranet.graphics.raytrace.shape.Cylinder;
import org.intranet.graphics.raytrace.shape.FixedSequence;
import org.intranet.graphics.raytrace.shape.Group;
import org.intranet.graphics.raytrace.shape.Plane;
import org.intranet.graphics.raytrace.shape.Sequence;
import org.intranet.graphics.raytrace.shape.Sphere;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.map.Canvas;
import org.junit.Assert;

import io.cucumber.java.ParameterType;

public class CustomParameters
	implements StepPatterns
{
	@ParameterType(wordPattern)
	public String identifier(String s)
	{
		return s;
	}

	@ParameterType("(" + doublePattern + "|-?infinity)")
	public double dbl(String s)
	{
		if ("infinity".equals(s))
			return Double.POSITIVE_INFINITY;
		if ("-infinity".equals(s))
			return Double.NEGATIVE_INFINITY;
		return Double.valueOf(s);
	}

	@ParameterType(name="boolean", value="(" + TRUE_PATTERN + "|[Ff][Aa][lL][sS][eE]|0|[nN][oO])")
	public Boolean bln(String str)
	{
		return str.matches("(" + TRUE_PATTERN + ")");
	}

	@ParameterType("(min|max)")
	public String minMax(String s)
	{
		return s;
	}

	@ParameterType("tuple\\(" + fourDoublesPattern + "\\)")
	public Tuple tuple(String xStr, String yStr, String zStr, String wStr)
	{
		double x = Double.valueOf(xStr);
		double y = Double.valueOf(yStr);
		double z = Double.valueOf(zStr);
		double w = Double.valueOf(wStr);
		if (Tuple.dblEqual(1.0, w)) return new Point(x, y, z);
		else if (Tuple.dblEqual(0.0, w)) return new Vector(x, y, z);
		else return new Tuple(x, y, z, w);

	}


	@ParameterType(preferForRegexMatch = true, value = "point\\(" + threeDoublesPattern + "\\)")
	public Point point(String xStr, String yStr, String zStr)
	{
		double x = Double.valueOf(xStr);
		double y = Double.valueOf(yStr);
		double z = Double.valueOf(zStr);

		return new Point(x, y, z);
	}

	@ParameterType("point\\(" + doublePattern + ", " + doublePattern + ", (-?)√" + doublePattern + "/" + doublePattern + "\\)")
	public Point pointNNS(String xStr, String yStr, String zNeg, String zSqrtStr, String zDenomStr)
	{
		double x = Double.valueOf(xStr);

		double y = Double.valueOf(yStr);

		double z = calcSqrtValue(zNeg, zSqrtStr, zDenomStr);

		return new Point(x, y, z);
	}

	@ParameterType("point\\(" + doublePattern + ", (-?)√" + doublePattern + "/" + doublePattern + ", (-?)√" + doublePattern + "/" + doublePattern + "\\)")
	public Point pointNSS(String xStr, String yNeg, String ySqrtStr, String yDenomStr, String zNeg, String zSqrtStr, String zDenomStr)
	{
		double x = Double.valueOf(xStr);

		double y = calcSqrtValue(yNeg, ySqrtStr, yDenomStr);

		double z = calcSqrtValue(zNeg, zSqrtStr, zDenomStr);

		return new Point(x, y, z);
	}

	@ParameterType("point\\((-?)√" + doublePattern + "/" + doublePattern + ", " + doublePattern + ", (-?)√" + doublePattern + "/" + doublePattern + "\\)")
	public Point pointSNS(String xNeg, String xSqrtStr, String xDenomStr, String yStr, String zNeg, String zSqrtStr, String zDenomStr)
	{
		double x = calcSqrtValue(xNeg, xSqrtStr, xDenomStr);

		double y = Double.valueOf(yStr);

		double z = calcSqrtValue(zNeg, zSqrtStr, zDenomStr);

		return new Point(x, y, z);
	}

	@ParameterType("point\\((-?)√" + doublePattern + "/" + doublePattern + ", (-?)√" + doublePattern + "/" + doublePattern + ", (-?)√" + doublePattern + "/" + doublePattern + "\\)")
	public Point pointSSS(
		String xNeg, String xSqrtStr, String xDenomStr,
		String yNeg, String ySqrtStr, String yDenomStr,
		String zNeg, String zSqrtStr, String zDenomStr)
	{
		double x = calcSqrtValue(xNeg, xSqrtStr, xDenomStr);

		double y = calcSqrtValue(yNeg, ySqrtStr, yDenomStr);

		double z = calcSqrtValue(zNeg, zSqrtStr, zDenomStr);

		return new Point(x, y, z);
	}

	@ParameterType("point\\((-?)√" + doublePattern + "/" + doublePattern + ", (-?)√" + doublePattern + "/" + doublePattern + ", " + doublePattern + "\\)")
	public Point pointSSN(
		String xNeg, String xSqrtStr, String xDenomStr,
		String yNeg, String ySqrtStr, String yDenomStr,
		String zStr)
	{
		double x = calcSqrtValue(xNeg, xSqrtStr, xDenomStr);

		double y = calcSqrtValue(yNeg, ySqrtStr, yDenomStr);

		double z = Double.valueOf(zStr);

		return new Point(x, y, z);
	}

	private double calcSqrtValue(String neg, String sqrtStr, String denomStr)
	{
		int sign = "-".equals(neg) ? -1 : 1;
		double num = Math.sqrt(Double.valueOf(sqrtStr));
		double denom = Double.valueOf(denomStr);
		return sign * num / denom;
	}

	@ParameterType("point\\((-?)infinity, (-?)infinity, (-?)infinity\\)")
	public Point pointInfinity(String xNeg, String yNeg, String zNeg)
	{
		double x = infinityForSign(xNeg);
		double y = infinityForSign(yNeg);
		double z = infinityForSign(zNeg);
		return new Point(x, y, z);
	}

	@ParameterType("point\\(" + doublePattern + ", (-?)infinity, " + doublePattern + "\\)")
	public Point pointYinfinity(String xStr, String yNeg, String zStr)
	{
		double x = Double.valueOf(xStr);
		double y = infinityForSign(yNeg);
		double z = Double.valueOf(zStr);
		return new Point(x, y, z);
	}

	@ParameterType("point\\((-?)infinity, " + doublePattern + ", (-?)infinity\\)")
	public Point pointXZinfinity(String xNeg, String yStr, String zNeg)
	{
		double x = infinityForSign(xNeg);
		double y = Double.valueOf(yStr);
		double z = infinityForSign(zNeg);
		return new Point(x, y, z);
	}

	private double infinityForSign(String xNeg)
	{
		return "-".contentEquals(xNeg) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
	}


	@ParameterType("vector\\(" + threeDoublesPattern + "\\)")
	public Vector vector(String xStr, String yStr, String zStr)
	{
		double x = Double.valueOf(xStr);
		double y = Double.valueOf(yStr);
		double z = Double.valueOf(zStr);

		return new Vector(x, y, z);
	}

	@ParameterType("vector\\((-?)√2/2, (-?)√2/2, " + doublePattern + "\\)")
	public Vector vectorSSN(String xNeg, String yNeg, String zStr)
	{
		double x = calcSqrtValue(xNeg, "2", "2");

		double y = calcSqrtValue(yNeg, "2", "2");

		double z = Double.valueOf(zStr);

		return new Vector(x, y, z);
	}

	@ParameterType("vector\\(" + doublePattern + ", (-?)√2, " + doublePattern + "\\)")
	public Vector vectorNsN(String xStr, String yNeg, String zStr)
	{
		double x = Double.valueOf(xStr);

		int ySign = "-".equals(yNeg) ? -1 : 1;
		double yNum = Math.sqrt(2);

		double z = Double.valueOf(zStr);

		return new Vector(x, ySign * yNum, z);
	}

	@ParameterType("vector\\((-?)√" + doublePattern + "/" + doublePattern + ", " + doublePattern + ", (-?)√" + doublePattern + "/" + doublePattern + "\\)")
	public Vector vectorSNS(String xNeg, String xNumStr, String xDenomStr, String yStr, String zNeg, String zNumStr, String zDenomStr)
	{
		int xSign = "-".equals(xNeg) ? -1 : 1;
		double xNumSqrt = Math.sqrt(Double.valueOf(xNumStr));
		double xDenom = Double.valueOf(xDenomStr);

		double y = Double.valueOf(yStr);

		int zSign = "-".equals(zNeg) ? -1 : 1;
		double zNum = Math.sqrt(Double.valueOf(zNumStr));
		double zDenom = Double.valueOf(zDenomStr);

		return new Vector(xSign * xNumSqrt / xDenom, y, zSign * zNum / zDenom);
	}

	@ParameterType("vector\\((-?)√" + doublePattern + "/" + doublePattern + ", (-?)√" + doublePattern + "/" + doublePattern + ", (-?)√" + doublePattern + "/" + doublePattern + "\\)")
	public Vector vectorSSS(String xNeg, String xNumStr, String xDenomStr,
		String yNeg, String yNumStr, String yDenomStr,
		String zNeg, String zNumStr, String zDenomStr)
	{
		double x = calcSqrtValue(xNeg, xNumStr, xDenomStr);

		int ySign = "-".equals(yNeg) ? -1 : 1;
		double yNumSqrt = Math.sqrt(Double.valueOf(yNumStr));
		double yDenom = Double.valueOf(yDenomStr);
		double y = ySign * yNumSqrt / yDenom;

		int zSign = "-".equals(zNeg) ? -1 : 1;
		double zNumSqrt = Math.sqrt(Double.valueOf(zNumStr));
		double zDenom = Double.valueOf(zDenomStr);
		double z = zSign * zNumSqrt / zDenom;

		return new Vector(x, y, z);
	}

	@ParameterType("vector\\(" + doublePattern + ", (-?)√2/2, (-?)√2/2\\)")
	public Vector vectorNSS(String xStr, String yNeg, String zNeg)
	{
		double x = Double.valueOf(xStr);

		int ySign = "-".equals(yNeg) ? -1 : 1;
		double yNum = 2;
		double yDenom = 2;
		double y = Math.sqrt(yNum) / yDenom * ySign;

		double zNum = 2;
		double zDenom = 2;
		int zSign = "-".equals(zNeg) ? -1 : 1;
		double z = Math.sqrt(zNum) / zDenom * zSign;

		return new Vector(x, y, z);
	}



	@ParameterType("color\\(" + threeDoublesPattern + "\\)")
	public Color color(String redStr, String greenStr, String blueStr)
	{
		double red = Double.valueOf(redStr);
		double green = Double.valueOf(greenStr);
		double blue = Double.valueOf(blueStr);

		return new Color(red, green, blue);
	}



	@ParameterType("(translation|scaling)\\(" + threeDoublesPattern + "\\)")
	public Matrix matrix(String type, String xStr, String yStr, String zStr)
	{
		double x = Double.valueOf(xStr);
		double y = Double.valueOf(yStr);
		double z = Double.valueOf(zStr);

		return "translation".equals(type) ? Matrix.newTranslation(x, y, z) :
			Matrix.newScaling(x, y, z);
	}

	@ParameterType("shearing\\(" + sixDoublesPattern + "\\)")
	public Matrix shearing(String xyStr, String xzStr, String yxStr,
		String yzStr, String zxStr, String zyStr)
	{
		double xy = Double.valueOf(xyStr);
		double xz = Double.valueOf(xzStr);
		double yx = Double.valueOf(yxStr);
		double yz = Double.valueOf(yzStr);
		double zx = Double.valueOf(zxStr);
		double zy = Double.valueOf(zyStr);

		return Matrix.shearing(xy, xz, yx, yz, zx, zy);
	}

	@ParameterType("(rotation_x|rotation_y|rotation_z)\\(π[\\s ]*\\/[\\s ]*" + doublePattern + "\\)")
	public Matrix matrixRotationPiDiv(String type, String denomAmt)
	{
		double denom = Double.valueOf(denomAmt);

		double rotation = Math.PI / denom;

		switch (type)
		{
			case "rotation_z": return Matrix.newRotationZ(rotation);
			case "rotation_x": return Matrix.newRotationX(rotation);
			case "rotation_y": return Matrix.newRotationY(rotation);
			default: Assert.fail("Invalid type " + type);
		}
		return null;
	}



	@ParameterType("canvas\\(" + twoDoublesPattern + "\\)")
	public Canvas canvas(String widthStr, String heightStr)
	{
		int width = Integer.valueOf(widthStr);
		int height = Integer.valueOf(heightStr);

		return new Canvas(width, height);
	}

	@ParameterType("(sphere|glass_sphere|plane|cube|test_shape|group|cone|cylinder)\\(\\)")
	public Shape shape(String value)
	{
		switch (value)
		{
			case "sphere":
				return new Sphere();
			case "glass_sphere":
				return createGlassSphere();
			case "plane":
				return new Plane();
			case "cube":
				return new Cube();
			case "test_shape":
				return new TestShape();
			case "cone":
				return new Cone();
			case "cylinder":
				return new Cylinder();
			case "group":
				return new Group();
			default:
				Assert.fail("Unknown shape type " + value);
		}
		return null;
	}

	@ParameterType("default_world\\(\\)")
	public World default_world(String value)
	{
		return DefaultWorld.defaultWorld();
	}

	@ParameterType("world\\(\\)")
	public World world(String value)
	{
		return new World();
	}

	@ParameterType("test_pattern\\(\\)")
	public TestPattern test_pattern(String value)
	{
		return new TestPattern();
	}

	@ParameterType("material\\(\\)")
	public Material emptyMaterial(String value)
	{
		return new Material();
	}

	private Sphere createGlassSphere()
	{
		Sphere sphere = new Sphere();
		Material material = sphere.getMaterial();
		material.setTransparency(1.0);
		material.setRefractive(1.5);
		return sphere;
	}

	@ParameterType("(\\*|\\/)")
	public String multiplyDivide(String multDiv)
	{
		return multDiv;
	}

	@ParameterType("(\\+|\\-|\\*)")
	public String plusMinusTimes(String multDiv)
	{
		return multDiv;
	}

	@ParameterType("(\\+|\\-|)")
	public int sign(String multDiv)
	{
		return "-".equals(multDiv) ? -1 : 1;
	}

	@ParameterType("(=|!=)")
	public String equalNotEqual(String eqNe)
	{
		return eqNe;
	}

	@ParameterType("sequence\\(" + threeDoublesPattern + "\\)")
	public Sequence sequence3(String xStr, String yStr, String zStr)
	{
		double x = Double.valueOf(xStr);
		double y = Double.valueOf(yStr);
		double z = Double.valueOf(zStr);

		return new FixedSequence(x, y, z);
	}

	@ParameterType("sequence\\(" + twoDoublesPattern + "\\)")
	public Sequence sequence2(String xStr, String yStr)
	{
		double x = Double.valueOf(xStr);
		double y = Double.valueOf(yStr);

		return new FixedSequence(x, y);
	}

	@ParameterType("sequence\\(" + fiveDoublesPattern + "\\)")
	public Sequence sequence5(String str1, String str2, String str3, String str4, String str5)
	{
		double d1 = Double.valueOf(str1);
		double d2 = Double.valueOf(str2);
		double d3 = Double.valueOf(str3);
		double d4 = Double.valueOf(str4);
		double d5 = Double.valueOf(str5);

		return new FixedSequence(d1, d2, d3, d4, d5);
	}

//	@ParameterType("vector\\(" + threeDoublesPattern + ")")
//	public Vector vector(String type, String xStr, String yStr, String zStr)
//	{
//		double x = Double.valueOf(xStr);
//		double y = Double.valueOf(yStr);
//		double z = Double.valueOf(zStr);
//
//		return new Vector(x, y, z);
//	}

//	@ParameterType("(scaling|translation)\\(" + threeDoublesPattern + "\\)\\)")
//	public Matrix matrixOperation(String operation, String xStr, String yStr, String zStr)
//	{
//		double x = Double.valueOf(xStr);
//		double y = Double.valueOf(yStr);
//		double z = Double.valueOf(zStr);
//		return "scaling".equals(operation) ? Matrix.newScaling(x, y, z) :
//			Matrix.newTranslation(x, y, z);
//	}
}
