package org.intranet.graphics.raytrace.surface.map;

import java.util.List;

public final class StringLinesReader
	implements LinesReader
{
	private final List<String> lines;
	private int currentLine;

	public StringLinesReader(List<String> lines)
	{
		this.lines = lines;
		skipCommentLines();
	}

	public String nextLine()
	{
		if (hasMoreLines())
		{
			String line = lines.get(currentLine++);
			skipCommentLines();
			return line;
		}
		return null;
	}

	private void skipCommentLines()
	{
		while (currentLine < lines.size() && LinesReader.isCommentOrBlankLine(lines.get(currentLine)))
			currentLine++;
	}

	public boolean hasMoreLines()
	{
		return currentLine < lines.size();
	}

	@Override
	public long getDurationReading()
	{
		// An in-memory implementation is zero!
		return 0;
	}
}