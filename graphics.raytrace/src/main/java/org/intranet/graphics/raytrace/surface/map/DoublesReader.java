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

		values = stripDuplicateSpaces(colorLine).split(" ");
//		values = colorLine.replace(" [ ]+", " ").split(" ");
		idx = 0;
		return true;
	}

	/** Prevents re-allocation */
	StringBuilder lineCache = new StringBuilder();
	private String stripDuplicateSpaces(String colorLine)
	{
		lineCache.setLength(0);
		boolean lastSpace = false;
		for (int i = 0; i < colorLine.length(); i++)
		{
			char newChar = colorLine.charAt(i);
			boolean thisSpace = newChar == ' ';
			if (!(lastSpace && thisSpace))
				lineCache.append(newChar);
			lastSpace = thisSpace;
		}

		return lineCache.toString();
	}

	private boolean needMoreValues()
	{
		return values == null || idx >= values.length;
	}
}