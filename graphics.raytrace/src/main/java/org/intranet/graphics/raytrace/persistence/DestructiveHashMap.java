package org.intranet.graphics.raytrace.persistence;

import java.util.HashMap;
import java.util.Map;

/**
* Getting an item also removes it.
* @param <K>
* @param <V>
*/
public class DestructiveHashMap<K, V>
	extends HashMap<K, V>
{
	private static final long serialVersionUID = 1L;

	public DestructiveHashMap(Map<? extends K, ? extends V> m)
	{
		super(m);
	}

	@Override
	public V get(Object key)
	{
		V value = super.get(key);
		remove(key);
		return value;
	}
}