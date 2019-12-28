package org.intranet.graphics.raytrace.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.intranet.graphics.raytrace.World;
import org.junit.Before;
import org.junit.Test;

public class PersistenceTest
{
	private final YamlWorldParser parser = new YamlWorldParser();
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
	public void loadGrouptest1()
		throws FileNotFoundException
	{
		testFile("grouptest1.yml");
	}

	@Test
	public void loadGroup()
		throws FileNotFoundException
	{
		testFile("group.yml");
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

	private void testFile(String yamlName)
		throws FileNotFoundException
	{
		File file = new File(defaultDirectory, yamlName);
		World world = parser.parse(new FileInputStream(file));

	}
}
