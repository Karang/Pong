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
package fr.karang.pong;

import java.awt.Color;

import org.spout.api.Client;
import org.spout.api.Spout;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.gui.Screen;
import org.spout.api.gui.Widget;
import org.spout.api.gui.component.LabelComponent;
import org.spout.api.gui.component.TexturedRectComponent;
import org.spout.api.math.Rectangle;
import org.spout.api.plugin.CommonPlugin;
import org.spout.api.plugin.Platform;
import org.spout.api.render.Font;
import org.spout.api.render.RenderMaterial;

import fr.karang.pong.command.PaddleHandler;
import fr.karang.pong.component.AiPaddleComponent;
import fr.karang.pong.component.BallComponent;
import fr.karang.pong.component.ControlPaddleComponent;

public class PongPlugin extends CommonPlugin {
	private static PongPlugin instance;
	
	private RenderMaterial material;
	private Client client;
	private float ratio = 0.75f;
	
	// Objects
	private Widget ball;
	private Widget player1;
	private Widget player2;
	private LabelComponent score;
	
	public static PongPlugin getInstance() {
		return instance;
	}
	
	public Widget getBall() {
		return ball;
	}
	
	public Widget getPlayer(int player) {
		if (player==1)
			return player1;
		return player2;
	}
	
	public void resetScore() {
		score.setText(ChatStyle.GRAY + "0 | 0");
	}
	
	public void resetGame() {
		player1.get(TexturedRectComponent.class).setSprite(new Rectangle(-0.8f*ratio, -0.25f, 0.1f*ratio, 0.5f));
		player1.update();
		player2.get(TexturedRectComponent.class).setSprite(new Rectangle(0.7f*ratio, -0.25f, 0.1f*ratio, 0.5f));
		player2.update();
	}
	
	public void addScore(int player) {
		String[] playerScores = score.getText().substring(2).split(" ");
		int p1 = Integer.parseInt(playerScores[0]);
		int p2 = Integer.parseInt(playerScores[2]);
		if (player==1) {
			p1++;
		} else if (player==2) {
			p2++;
		}
		
		score.setText(ChatStyle.GRAY + "" + p1 + " | " + p2);
		resetGame();
		
		if (p1==3 || p2==3) {
			resetScore();
		}
	}
	
	public void onLoad() {
		client = (Client)Spout.getEngine();
		instance = this;
	}
	
	@Override
	public void onEnable() {
		Screen pongScreen = new Screen();
		material = (RenderMaterial) Spout.getFilesystem().getResource("material://Pong/resources/pong.smt");
		Font font = (Font) Spout.getFilesystem().getResource("font://Spout/resources/resources/fonts/ubuntu/Ubuntu-M.ttf");
		
		// Construct the ball
		ball = new Widget();
		TexturedRectComponent bRect = ball.add(TexturedRectComponent.class);
		bRect.setRenderMaterial(material);
		bRect.setSprite(new Rectangle(0, 0, 0.1f*ratio, 0.1f));
		bRect.setSource(new Rectangle(25f/32f, 0, 7f/32f, 7f/32f));
		bRect.setColor(Color.WHITE);
		ball.add(BallComponent.class);
		pongScreen.attachWidget(this, ball);
		
		// Construct the players
		player1 = new Widget();
		TexturedRectComponent p1Rect = player1.add(TexturedRectComponent.class);
		p1Rect.setRenderMaterial(material);
		p1Rect.setSprite(new Rectangle(-0.8f*ratio, -0.25f, 0.1f*ratio, 0.5f));
		p1Rect.setSource(new Rectangle(0, 0, 8f/32f, 1f));
		p1Rect.setColor(Color.BLUE);
		player1.add(ControlPaddleComponent.class);
		pongScreen.attachWidget(this, player1);
		
		player2 = new Widget();
		TexturedRectComponent p2Rect = player2.add(TexturedRectComponent.class);
		p2Rect.setRenderMaterial(material);
		p2Rect.setSprite(new Rectangle(0.7f*ratio, -0.25f, 0.1f*ratio, 0.5f));
		p2Rect.setSource(new Rectangle(0, 0, 8f/32f, 1f));
		p2Rect.setColor(Color.RED);
		player2.add(AiPaddleComponent.class);
		pongScreen.attachWidget(this, player2);
		
		// Construct the interface
		Widget scoreWidget = new Widget();
		score = scoreWidget.add(LabelComponent.class);
		score.setFont(font);
		score.setText(ChatStyle.GRAY + "0 | 0");
		pongScreen.attachWidget(this, scoreWidget);
		
		client.getScreenStack().openScreen(pongScreen);
		
		client.getInput().bind("KEY_UP", "+PongUp");
		client.getInput().bind("KEY_DOWN", "+PongDown");
		
		client.getRootCommand().addSubCommand(client, "+PongUp")
		.setExecutor(Platform.CLIENT, new PaddleHandler(player1, 1f));
		client.getRootCommand().addSubCommand(client, "+PongDown")
		.setExecutor(Platform.CLIENT, new PaddleHandler(player1, -1f));
		client.getRootCommand().addSubCommand(client, "-PongUp")
		.setExecutor(Platform.CLIENT, new PaddleHandler(player1, 0f));
		client.getRootCommand().addSubCommand(client, "-PongDown")
		.setExecutor(Platform.CLIENT, new PaddleHandler(player1, 0f));
	}

	@Override
	public void onDisable() {
		
	}

}
