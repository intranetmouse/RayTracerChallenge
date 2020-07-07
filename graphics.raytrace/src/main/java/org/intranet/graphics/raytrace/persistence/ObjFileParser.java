package org.intranet.graphics.raytrace.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.shape.Group;
import org.intranet.graphics.raytrace.shape.Triangle;

public final class ObjFileParser
{
	private static final String DEFAULT_GROUP = "defaultGroup";

	int ignoredLines = 0;
	public int getNumIgnoredLines() { return ignoredLines; }

	private Group currentGroup = new Group();
	private Map<String, Group> groups = new HashMap<>();

	public Group getDefaultGroup()
	{
		return getGroup(DEFAULT_GROUP);
	}

	public Group getGroup(String groupName)
	{
		return groups.get(groupName);
	}

	public Group getGroup()
	{
		if (groups.size() == 1)
			return groups.values().iterator().next();

		Group g = new Group();
		for (Group child : groups.values())
			g.addChild(child);
		return g;
	}

	public ObjFileParser()
	{
System.out.println("ObjFileParser putting currentGroup="+currentGroup+" into "+DEFAULT_GROUP);
		groups.put(DEFAULT_GROUP, currentGroup);
	}
	public ObjFileParser(List<String> stringList)
	{
		this();
		parserStringList(stringList);
	}

	public void parserStringList(List<String> stringList)
	{
		for (String str : stringList)
		{
			String[] parts = str.split("[ ]+");
			String command = parts[0];
			switch (command)
			{
				case "v":
				{
					double x = Double.valueOf(parts[1]);
					double y = Double.valueOf(parts[2]);
					double z = Double.valueOf(parts[3]);
					vertices.add(new Point(x, y, z));
					break;
				}
				case "g":
				{
					currentGroup = new Group();
					groups.put(parts[1], currentGroup);
					break;
				}
				case "f":
				{
					List<Point> points = Arrays.asList(parts).stream()
						.skip(1)
						.mapToInt(t -> Integer.valueOf(t))
						.mapToObj(idx -> vertices.get(idx - 1))
						.collect(Collectors.toList());

					Point p1 = points.get(0);
					for (int i = 1; i <= points.size() - 2; i++)
					{
						Point p2 = points.get(i);
						Point p3 = points.get(i + 1);
						currentGroup.addChild(new Triangle(p1, p2, p3));
					}
					break;
				}
//				case "vn":
//				{
//
//				}
				default:
					ignoredLines++;
			}
		}
	}

	private List<Point> vertices = new ArrayList<>();
	public Point getVertex0(int i) { return vertices.get(i); }
	public Point getVertex1(int i) { return vertices.get(i - 1); }
}