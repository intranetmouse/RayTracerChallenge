package org.intranet.graphics.raytrace.surface.map;

public interface LinesReader
{
	String nextLine();
	boolean hasMoreLines();

	long getDurationReading();
	static boolean isCommentOrBlankLine(String line)
	{
		int lineLength = line.length();
	
		char latestChar;
		int idx = 0;
		while (idx < lineLength)
		{
			latestChar = line.charAt(idx);
			if (latestChar == '#')
				return true;
			if (latestChar != ' ')
				return false;
			idx++;
		}
		// the line is blank if we got to the end
		return idx == lineLength;
	}
}