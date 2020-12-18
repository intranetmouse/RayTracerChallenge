package org.intranet.graphics.raytrace.surface.map;

import java.io.BufferedReader;
import java.io.IOException;

public final class BufferedReaderLinesReader
	implements LinesReader
{
	private final BufferedReader br;
	String nextLine;

	public BufferedReaderLinesReader(BufferedReader br)
		throws IOException
	{
		this.br = br;
		nextLine = br.readLine();
	}

	@Override
	public String nextLine()
	{
		String lineToReturn = nextLine;
		if (nextLine != null)
			try
			{
				for (nextLine = br.readLine();
					nextLine != null && nextLine.trim().startsWith("#");
					nextLine = br.readLine())
				{
					// Just keep going
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
				nextLine = null;
			}
		return lineToReturn;
	}

	@Override
	public boolean hasMoreLines()
	{
		return nextLine != null;
	}
}