package com.kabryxis.kabutils.spigot.plugin.particleapi;

import com.kabryxis.kabutils.data.file.yaml.ConfigSection;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.inventivetalent.particle.ParticleEffect;

import java.util.Collection;

public class ParticleInfo {

	protected ParticleEffect effect;
	protected double offsetX, offsetY, offsetZ;
	protected double speed;
	protected int particleCount;
	protected int displayRadius;
	
	public ParticleInfo(ConfigSection section) {
		load(section);
	}

	public ParticleInfo(ParticleEffect effect) {
		this.effect = effect;
		particleCount = 1;
		displayRadius = 32;
	}
	
	public void load(ConfigSection section) {
		effect = section.getEnum("effect", ParticleEffect.class);
		offsetX = section.getDouble("offset.x");
		offsetY = section.getDouble("offset.y");
		offsetZ = section.getDouble("offset.z");
		speed = section.getDouble("speed");
		particleCount = section.getInt("particle-count", 1);
		displayRadius = section.getInt("display-radius", 32);
	}
	
	public void display(Location loc) {
		display(loc, loc.getWorld().getPlayers());
	}

	public void display(Location loc, Collection<Player> players) {
		effect.send(players, loc, offsetX, offsetY, offsetZ, speed, particleCount, displayRadius);
	}
	
}
