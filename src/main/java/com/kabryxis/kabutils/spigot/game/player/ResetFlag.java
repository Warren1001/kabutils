package com.kabryxis.kabutils.spigot.game.player;

import org.bukkit.entity.Player;

import java.util.function.Consumer;

public enum ResetFlag {
	
	HEALTH(GamePlayer::resetMaxHealth),
	INVENTORY(GamePlayer::clearInventory),
	EXPERIENCE(player -> {
		player.setLevel(0);
		player.setExp(0.0F);
	}),
	EFFECTS(GamePlayer::clearActivePotionEffects),
	VISIBILITY(player -> {
		Player p = player.getPlayer();
		p.getServer().getOnlinePlayers().forEach(bukkitPlayer -> bukkitPlayer.showPlayer(p));
	}),
	GAMEMODE(player -> player.setGameMode(player.getPlayer().getServer().getDefaultGameMode())),
	FLIGHT(player -> {
		player.setAllowFlight(false);
		player.setFlying(false);
	});
	
	private final Consumer<? super GamePlayer> action;
	
	ResetFlag(Consumer<? super GamePlayer> action) {
		this.action = action;
	}
	
	public void reset(GamePlayer gamePlayer) {
		action.accept(gamePlayer);
	}
	
}
