package org.intranet.graphics.raytrace.surface.map;

import java.io.BufferedReader;
import java.io.IOException;

public final class BufferedReaderLinesReader
	implements LinesReader
{
	private final BufferedReader br;
	private String nextLine;

	private long durationReading = 0;
	@Override
	public long getDurationReading() { return durationReading; }

	public BufferedReaderLinesReader(BufferedReader br)
		throws IOException
	{
		this.br = br;
		nextLine = readLine();
	}

	private String readLine()
		throws IOException
	{
		long start = System.currentTimeMillis();
		String value = br.readLine();
		long end = System.currentTimeMillis();
		durationReading += end - start;
		return value;
	}

	@Override
	public String nextLine()
	{
		String lineToReturn = nextLine;
		if (nextLine != null)
			try
			{
				for (nextLine = readLine();
					nextLine != null && LinesReader.isCommentOrBlankLine(nextLine);
//					nextLine != null && nextLine.trim().startsWith("#");
					nextLine = readLine())
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