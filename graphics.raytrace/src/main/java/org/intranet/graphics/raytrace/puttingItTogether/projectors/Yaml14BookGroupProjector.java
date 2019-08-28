package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.InputStream;

import org.intranet.graphics.raytrace.puttingItTogether.projector.WorldProjector;

public final class Yaml14BookGroupProjector
	extends WorldProjector
{
	public static final String GROUP_YML =
		"/org/intranet/graphics/raytrace/yml/group.yml";

	public Yaml14BookGroupProjector()
	{ super("14-Book Groups"); }


	@Override
	protected void fillWorld()
	{
		InputStream ymlStream = getClass().getResourceAsStream(GROUP_YML);
		YamlWorldParser parser = new YamlWorldParser();
		world = parser.parse(ymlStream);
	}
}