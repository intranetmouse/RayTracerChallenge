package org.intranet.graphics.raytrace.surface.map;

public final class DoublesReader
{
	private final LinesReader rdr;
	private String[] values;
	private int idx;

	public DoublesReader(LinesReader rdr)
	{
		this.rdr = rdr;
	}

	public Double nextDouble()
	{
		if (needMoreValues() && !tryReadMoreValues())
				return null;

		return retrieveNextValue();
	}

	private Double retrieveNextValue()
	{
		String value = values[idx++];
		Double nextValue = "".equals(value) ? nextDouble() :
			Double.valueOf(value);
		return nextValue;
	}

	private boolean tryReadMoreValues()
	{
		String colorLine = rdr.nextLine();

		if (colorLine == null)
			return false;

		values = colorLine.replaceAll(" [ ]+", " ").split(" ");
		idx = 0;
		return true;
	}

	private boolean needMoreValues()
	{
		return values == null || idx >= values.length;
	}
}