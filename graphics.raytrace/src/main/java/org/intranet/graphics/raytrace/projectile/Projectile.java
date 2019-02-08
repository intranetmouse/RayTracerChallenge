package org.intranet.graphics.raytrace.projectile;

import org.intranet.graphics.raytrace.Point;
import org.intranet.graphics.raytrace.Vector;

public class Projectile
{
	private Point position;
	public Point getPosition() { return position; }

	private Vector velocity;

	public Projectile tick(Environment env)
	{
		Point newPosition = position.add(velocity);
		Vector newVelocity = velocity.add(env.getGravity()).add(env.getWind());
		return new Projectile(newPosition, newVelocity);
	}

	public Projectile(Point position, Vector velocity)
	{
		this.position = position;
		this.velocity = velocity;
	}
}
