package org.intranet.graphics.raytrace.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.shape.Cube;
import org.intranet.graphics.raytrace.shape.Group;
import org.intranet.graphics.raytrace.shape.Plane;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PersistenceTest
{
	private final File defaultDirectory = new File(getClass().getResource(
		"/org/intranet/graphics/raytrace/yml/reflect-refract.yml").getFile()).getParentFile();

	@Before
	public void init()
	{

	}


	@Test
	public void load7_3_balls_sphere_walls()
		throws FileNotFoundException
	{
		testFile("07-3_balls_sphere_walls.yml");
	}

	@Test
	public void load9_3_balls_plane_walls()
		throws FileNotFoundException
	{
		testFile("09-3_balls_plane_walls.yml");
	}

	@Test
	public void load10_patterns_striped()
		throws FileNotFoundException
	{
		testFile("10-patterns-striped.yml");
	}

	@Test
	public void load11a_reflect_refract()
		throws FileNotFoundException
	{
		testFile("11a-reflect-refract.yml");
	}

	@Test
	public void load11b_refract_glass_sphere()
		throws FileNotFoundException
	{
		testFile("11b-refract-glass-sphere.yml");
	}

	@Test
	public void load11c_refract_fresnel()
		throws FileNotFoundException
	{
		testFile("11c-refract-fresnel.yml");
	}

	@Test
	public void loadChristmas()
		throws FileNotFoundException
	{
		testFile("christmas.yml");
	}

	@Test
	public void loadCylinders()
		throws FileNotFoundException
	{
		testFile("cylinders.yml");
	}

	@Test
	public void loadCheckeredCube()
		throws FileNotFoundException
	{
		testFile("checkered-cube.yml");
	}

	@Test
	public void loadEarth()
		throws FileNotFoundException
	{
		testFile("earth.yml");
	}

	@Test
	public void loadGroupOneLevel()
		throws FileNotFoundException
	{
		World world = testFile("grouptest1.yml");
		List<Shape> objs = world.getSceneObjects();

		Assert.assertEquals(5, objs.size());

		Shape s1 = objs.get(0);
		Assert.assertEquals(Cube.class, s1.getClass());
	}

	@Test
	public void loadGrouptest2()
		throws FileNotFoundException
	{
		World world = testFile("grouptest2.yml");
		List<Shape> objs = world.getSceneObjects();

		Assert.assertEquals(3, objs.size());

		Shape s1 = objs.get(0);
		Assert.assertEquals(Group.class, s1.getClass());

		Group g1 = (Group)s1;

		List<Shape> children = g1.getChildren();
		Assert.assertEquals(3, children.size());
		Assert.assertEquals(Cube.class, children.get(0).getClass());
	}

	@Test
	public void loadGroup()
		throws FileNotFoundException
	{
		World world = testFile("group.yml");
		List<Shape> objs = world.getSceneObjects();

		Assert.assertEquals(4, objs.size());

		Shape plane = objs.get(0);
		Assert.assertTrue(Plane.class.isInstance(plane));

		Shape obj = objs.get(1);
		Assert.assertTrue(Group.class.isInstance(obj));

		Group wackyGrp = (Group)obj;
		List<Shape> wackyChildren = wackyGrp.getChildren();
		Assert.assertEquals(8, wackyChildren.size());

		Group firstLegGrp = (Group)wackyChildren.get(0);
		List<Shape> firstLegChildren = firstLegGrp.getChildren();
		Assert.assertEquals(2, firstLegChildren.size());
	}

	@Test
	public void loadMetal()
		throws FileNotFoundException
	{
		testFile("metal.yml");
	}

	@Test
	public void loadPuppets()
		throws FileNotFoundException
	{
		testFile("puppets.yml");
	}

	@Test
	public void loadReflectRefract()
		throws FileNotFoundException
	{
		testFile("reflect-refract.yml");
	}

	@Test
	public void loadRefractionBend()
		throws FileNotFoundException
	{
		testFile("refraction-bend.yml");
	}

	@Test
	public void loadTable()
		throws FileNotFoundException
	{
		testFile("table.yml");
	}

	@Test
	public void loadTableGroups()
		throws FileNotFoundException
	{
		testFile("table-groups.yml");
	}

	private World testFile(String yamlName)
		throws FileNotFoundException
	{
		File file = new File(defaultDirectory, yamlName);
		FileInputStream ymlStream = new FileInputStream(file);
		YamlWorldParser parser = new YamlWorldParser(ymlStream, defaultDirectory);
		return parser.getWorld();
	}
}
