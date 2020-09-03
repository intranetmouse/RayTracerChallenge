package org.intranet.graphics.raytrace.primitive;

public final class Pair<T>
{
	private final T first;
	public T getFirst() { return first; }

	private final T second;
	public T getSecond() { return second; }

	public Pair(T first, T second)
	{
		this.first = first;
		this.second = second;
	}

	@Override
	public String toString()
	{ return "Pair [first=" + first + ", second=" + second + "]"; }
}