package com.kabryxis.kabutils.spigot.plugin.particleapi;

import com.kabryxis.kabutils.data.file.yaml.ConfigSection;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.Collection;

public class ParticleInfo {

	protected Particle particle;
	protected double offsetX, offsetY, offsetZ;
	protected double speed;
	protected int particleCount;
	protected int displayRadius;
	
	public ParticleInfo(ConfigSection section) {
		load(section);
	}

	public ParticleInfo(Particle particle) {
		this.particle = particle;
		particleCount = 1;
		displayRadius = 32;
	}
	
	public void load(ConfigSection section) {
		particle = section.getEnum("particle", Particle.class);
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
		players.forEach(player -> player.spawnParticle(particle, loc, particleCount, offsetX, offsetY, offsetZ, speed));
	}
	
}
