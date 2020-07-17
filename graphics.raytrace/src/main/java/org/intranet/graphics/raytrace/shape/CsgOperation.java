package org.intranet.graphics.raytrace.shape;

public enum CsgOperation
{
	UNION("union") {
		public boolean intersectionAllowed(boolean lhit, boolean inl, boolean inr)
		{
			return (lhit && !inr) || (!lhit && !inl);
		}
	},
	INTERSECTION("intersection") {
		public boolean intersectionAllowed(boolean lhit, boolean inl, boolean inr)
		{
			return (lhit && inr) || (!lhit && inl);
		}
	},
	DIFFERENCE("difference") {
		public boolean intersectionAllowed(boolean lhit, boolean inl, boolean inr)
		{
			return (lhit && !inr) || (!lhit && inl);
		}
	}
	;

	public abstract boolean intersectionAllowed(boolean lhit, boolean inl, boolean inr);

	public static CsgOperation get(String name)
	{
		for (CsgOperation op : CsgOperation.values())
			if (name.equals(op.getName()))
				return op;
		throw new IllegalArgumentException(name);
	}

	private final String name;
	public String getName() { return name; }

	CsgOperation(String name)
	{
		this.name = name;
	}
}