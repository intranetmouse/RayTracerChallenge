package org.intranet.graphics.raytrace.persistence;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
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

	public static final int BVH_DIVIDE = 100;
	public Group getGroup()
	{
		Instant startInstant = Instant.now();

		try
		{
			if (groups.size() == 1)
			{
				Group onlyGroup = groups.values().iterator().next();
				onlyGroup.divide(BVH_DIVIDE);
				return onlyGroup;
			}

			Group g = new Group();
			for (Group child : groups.values())
				g.addChild(child);

			g.divide(BVH_DIVIDE);
			return g;
		}
		finally
		{
			Instant endInstant = Instant.now();

			Duration d = Duration.between(startInstant, endInstant);
			System.out.println("Split time="+d);
		}
	}

	public ObjFileParser()
	{
		groups.put(DEFAULT_GROUP, currentGroup);
	}
	public ObjFileParser(Stream<String> stringList)
	{
		this();

		Instant startInstant = Instant.now();

		parserStringList(stringList);

		Instant endInstant = Instant.now();

		Duration d = Duration.between(startInstant, endInstant);
		System.out.println("Parsing time="+d);
	}
	public ObjFileParser(List<String> stringList)
	{
		this();

		Instant startInstant = Instant.now();

		parserStringList(stringList);

		Instant endInstant = Instant.now();

		Duration d = Duration.between(startInstant, endInstant);
		System.out.println("Parsing time="+d);
	}

	public void parserStringList(List<String> stringList)
	{
		parserStringList(stringList.stream());
	}
	public void parserStringList(Stream<String> stringList)
	{
		stringList.forEach(str -> {
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
						.map(ObjFileParser::removeTextureAndNormals)
						.mapToInt(t -> Integer.valueOf(t))
						.mapToObj(idx -> vertices.get(idx - 1))
						.collect(Collectors.toList());

					List<Vector> vertexNormals = Arrays.asList(parts).stream()
						.skip(1)
						.map(ObjFileParser::removeVertexAndNormals)
						.filter(n -> n != null)
						.mapToInt(t -> Integer.valueOf(t))
						.mapToObj(idx -> normals.get(idx - 1))
						.collect(Collectors.toList());

					Point p1 = points.get(0);
					for (int i = 1; i <= points.size() - 2; i++)
					{
						Point p2 = points.get(i);
						Point p3 = points.get(i + 1);
						if (vertexNormals.isEmpty())
							currentGroup.addChild(new Triangle(p1, p2, p3));
						else
						{
							Vector vertexNormal1 = vertexNormals.get(0);
							Vector vertexNormal2 = vertexNormals.get(i);
							Vector vertexNormal3 = vertexNormals.get(i + 1);
							currentGroup.addChild(new Triangle(p1, p2, p3,
								vertexNormal1, vertexNormal2, vertexNormal3));
						}
					}
					break;
				}
				case "vn":
				{
					double x = Double.valueOf(parts[1]);
					double y = Double.valueOf(parts[2]);
					double z = Double.valueOf(parts[3]);
					normals.add(new Vector(x, y, z));
					break;
				}
				default:
					ignoredLines++;
			}
		});
	}

	public static String removeTextureAndNormals(String s)
	{
		int slashIndex = s.indexOf('/');
		if (slashIndex < 0)
			return s;
		return s.substring(0, slashIndex);
	}

	public static String removeVertexAndNormals(String s)
	{
		int slashIndex = s.lastIndexOf('/');
		if (slashIndex < 0)
			return null;
		return s.substring(slashIndex + 1);
	}

	private List<Point> vertices = new ArrayList<>();
	public Point getVertex0(int i) { return vertices.get(i); }
	public Point getVertex1(int i) { return vertices.get(i - 1); }

	private List<Vector> normals = new ArrayList<>();
	public Vector getNormal0(int i) { return normals.get(i); }
	public Vector getNormal1(int i) { return normals.get(i - 1); }
}