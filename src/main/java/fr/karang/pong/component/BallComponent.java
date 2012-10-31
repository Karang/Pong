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

import fr.karang.pong.PongPlugin;

public class BallComponent extends WidgetComponent {
	private Random rand = new Random();
	private float ratio = 0.75f;
	
	private PongPlugin plugin;
	private TexturedRectComponent rect;
	private float w;
	private float h;
	
	private float speed = 0.001f;
	private float dx;
	private float dy;
	
	@Override
	public void onAttached() {
		plugin = PongPlugin.getInstance();
		rect = getOwner().get(TexturedRectComponent.class);
		w = rect.getSprite().getWidth();
		h = rect.getSprite().getHeight();
		reset();
	}
	
	public void reset() {
		double angle = rand.nextInt(121)-60;
		dx = (float) Math.cos(Math.toRadians(angle));
		dy = (float) Math.sin(Math.toRadians(angle));
	}
	
	@Override
	public void onTick(float dt) {
		float x = rect.getSprite().getX();
		float y = rect.getSprite().getY();
		
		x += dx * speed * dt; 
		y += dy * speed * dt;
		
		if (x>0.7f*ratio-w && x<0.8*ratio-w) { // AI
			Rectangle paddle = plugin.getPlayer(2).get(TexturedRectComponent.class).getSprite();
			if (y>paddle.getY()-h && y<paddle.getY()+paddle.getHeight()) {
				x = 0.7f*ratio-w;
				dx = -dx;
			}
		}
		
		if (x>-0.8f*ratio && x<-0.7*ratio) { // Player
			Rectangle paddle = plugin.getPlayer(1).get(TexturedRectComponent.class).getSprite();
			if (y>paddle.getY()-h && y<paddle.getY()+paddle.getHeight()) {
				x = -0.7f*ratio;
				dx = -dx;
			}
		}
		
		if (x>1f-w) { // RIGHT
			plugin.addScore(1);
			x = 0;
			y = 0;
			reset();
		}
		if (x<-1f) { // LEFT
			plugin.addScore(2);
			x = 0;
			y = 0;
			reset();
		}
		if (y>1f-h) { // UP
			y = 1f-h;
			dy = -dy;
		}
		if (y<-1f) { // DOWN
			y = -1f;
			dy = -dy;
		}
		
		rect.setSprite(new Rectangle(x, y, w, h));
		getOwner().update();
	}
}
