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

import org.spout.api.component.components.WidgetComponent;
import org.spout.api.gui.component.TexturedRectComponent;
import org.spout.api.math.Rectangle;

import fr.karang.pong.PongPlugin;

public class AiPaddleComponent extends WidgetComponent {
	
	private PongPlugin plugin;
	private TexturedRectComponent ball;
	private TexturedRectComponent paddle;
	
	private float dy = 0;
	private float speed = 0.0005f;
	
	@Override
	public void onAttached() {
		plugin = PongPlugin.getInstance();
		ball = plugin.getBall().get(TexturedRectComponent.class);
		paddle = getOwner().get(TexturedRectComponent.class);
	}
	
	@Override
	public void onTick(float dt) {
		float w = paddle.getSprite().getWidth();
		float h = paddle.getSprite().getHeight();
		float x = paddle.getSprite().getX();
		float y = paddle.getSprite().getY();
		
		if (y+h/2-ball.getSprite().getY()>0.05f) {
			dy = -1f;
		} else if (y+h/2-ball.getSprite().getY()<0.05f) {
			dy = 1f;
		} else {
			dy = 0f;
		}
		
		y += dy * speed * dt;
		
		if (y>1f-h) {
			y = 1f-h;
		}
		if (y<-1f) {
			y = -1f;
		}
		
		paddle.setSprite(new Rectangle(x, y, w, h));
		getOwner().update();
	}
}
