package org.intranet.graphics.raytrace.steps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.intranet.graphics.raytrace.persistence.ObjFileParser;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.shape.Group;
import org.intranet.graphics.raytrace.shape.Triangle;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ObjFileSteps
	extends StepsParent
{
	public ObjFileSteps(RaytraceData data)
	{
		super(data);
	}

	@Given("{identifier} ← a file containing:")
	public void setAFileContaining(String variableName, String docString)
	{
		String[] testLinesArray = docString.split("[\n\r]");
		List<String> testLinesList = Arrays.asList(testLinesArray);
		data.put(variableName, testLinesList);
	}

	@When("{identifier} ← parse_obj_file\\({identifier})")
	public void setParsedObjFileFromStringLinesVar(String objFileParseName,
		String stringListName)
	{
		List<String> stringList = data.getStringList(stringListName);

		ObjFileParser parser = new ObjFileParser(stringList);

		data.put(objFileParseName, parser);
	}

	@Then("{identifier} should have ignored {int} lines")
	public void assertParserShouldHaveIgnoredLines(String objParserName,
		int expectedNumIgnoredLines)
	{
		ObjFileParser parser = data.getObjParser(objParserName);
		Assert.assertEquals(expectedNumIgnoredLines, parser.getNumIgnoredLines());
	}

	@Then("{identifier}.vertices[{int}] = {point}")
	public void assertParserVertexNEqPoint(String objParserName, int idx1,
		Point expectedPoint)
	{
		ObjFileParser parser = data.getObjParser(objParserName);
		Point actualPoint = parser.getVertex1(idx1);
		Assert.assertEquals(expectedPoint, actualPoint);
	}

	@Then("{identifier} ← {identifier}.default_group")
	public void setParserVertexNEqPoint(String groupName, String objParserName)
	{
		ObjFileParser parser = data.getObjParser(objParserName);
		Group group = parser.getDefaultGroup();
		data.put(groupName, group);
	}

	@When("{identifier} ← first child of {identifier}")
	public void setFirstChildOfGroup(String triangleName, String groupName)
	{
		setNthChildOfGroup(triangleName, groupName, 0);
	}

	@When("{identifier} ← second child of {identifier}")
	public void setSecondChildOfGroup(String triangleName, String groupName)
	{
		setNthChildOfGroup(triangleName, groupName, 1);
	}

	@When("{identifier} ← third child of {identifier}")
	public void setThirdChildOfGroup(String triangleName, String groupName)
	{
		setNthChildOfGroup(triangleName, groupName, 2);
	}

	private void setNthChildOfGroup(String triangleName, String groupName,
		int idx)
	{
		Group g = (Group)data.getShape(groupName);
		Triangle t = (Triangle)g.getChildren().get(idx);
		data.put(triangleName, t);
	}

	@Then("{identifier}.p{int} = {identifier}.vertices[{int}]")
	public void assertTrianglePointEqVertexN(String triangleName, int pointNum,
		String parserName, Integer int1)
	{
		Triangle t = (Triangle)data.getShape(triangleName);
		Point triangleP1 = pointNum == 1 ? t.getP1() :
			pointNum == 2 ? t.getP2() :
			pointNum == 3 ? t.getP3() :
			((Triangle)null).getP1();

		ObjFileParser p = data.getObjParser(parserName);
		Point vertex = p.getVertex1(int1);

		Assert.assertEquals(vertex, triangleP1);
	}

	@Given("{identifier} ← the file {string}")
	public void setFileStrFromFile(String fileStrVar, String filename)
		throws IOException
	{
		try (InputStream stream = getClass().getResourceAsStream(filename);
			InputStreamReader isr = new InputStreamReader(stream);
			BufferedReader br = new BufferedReader(isr);)
		{
			List<String> lines = br.lines().collect(Collectors.toList());
			data.put(fileStrVar, lines);
		}
	}

	@When("{identifier} ← {string} from {identifier}")
	public void setGroupFromParser(String destGroupName, String parserGroupName, String parserName)
	{
		ObjFileParser parser = data.getObjParser(parserName);
		Group g = parser.getGroup(parserGroupName);
		data.put(destGroupName, g);
	}
}
