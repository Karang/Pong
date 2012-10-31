/*
 * This file is part of Pong.
 *
 * Copyright (c) 2011-2012, Karang <http://www.spout.org/>
 * Pong is licensed under the SpoutDev License Version 1.
 *
 * Pong is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * Pong is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package fr.karang.pong.component;

import java.util.Random;

import org.spout.api.component.components.WidgetComponent;
import org.spout.api.gui.component.TexturedRectComponent;
import org.spout.api.math.Rectangle;

public class BallComponent extends WidgetComponent {
	private Random rand = new Random();
	
	private TexturedRectComponent rect;
	private float w;
	private float h;
	
	private float speed = 0.001f;
	private float dx = rand.nextFloat();
	private float dy = rand.nextFloat();
	
	@Override
	public void onAttached() {
		rect = getOwner().get(TexturedRectComponent.class);
		w = rect.getSprite().getWidth();
		h = rect.getSprite().getHeight();
	}
	
	@Override
	public void onTick(float dt) {
		float x = rect.getSprite().getX();
		float y = rect.getSprite().getY();
		
		float norm = (float) Math.sqrt(dx*dx+dy*dy);
		x += dx * speed * dt / norm; 
		y += dy * speed * dt / norm;
		
		if (x>1f-w) { // RIGHT
			x = 1f-w;
			dx = -rand.nextFloat();
		}
		if (x<-1f) { // LEFT
			x = -1f;
			dx = rand.nextFloat();
		}
		if (y>1f-h) { // UP
			y = 1f-h;
			dy = -rand.nextFloat();
		}
		if (y<-1f) { // DOWN
			y = -1f;
			dy = rand.nextFloat();
		}
				
		rect.setSprite(new Rectangle(x, y, w, h));
	}
}
