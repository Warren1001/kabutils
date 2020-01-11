package com.kabryxis.kabutils.spigot.version.custom.arrow.trailing;

import com.kabryxis.kabutils.spigot.concurrent.BukkitTaskManager;
import com.kabryxis.kabutils.spigot.plugin.particleapi.ParticleInfo;
import com.kabryxis.kabutils.spigot.version.custom.CustomEntity;
import com.kabryxis.kabutils.spigot.version.custom.CustomEntityRegistry;
import com.kabryxis.kabutils.spigot.version.custom.arrow.trailing.impl.TrailingArrowv1_13_R2;
import com.kabryxis.kabutils.spigot.version.custom.arrow.trailing.impl.TrailingArrowv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public interface TrailingArrow extends CustomEntity {

    Class<TrailingArrowv1_8_R3> v1_8_R3 = TrailingArrowv1_8_R3.class;
    Class<TrailingArrowv1_13_R2> v1_13_R2 = TrailingArrowv1_13_R2.class;
    Class<? extends TrailingArrow> IMPLEMENTATION_CLASS = WrapperFactory.getImplementationClass(TrailingArrow.class);

    static void register() {
        CustomEntityRegistry.registerEntity("Arrow", IMPLEMENTATION_CLASS);
    }

    static TrailingArrow spawn(Location loc, Vector velocity, int speed, BukkitTaskManager taskManager, ParticleInfo particleInfo) {
        return WrapperFactory.getSupplier(TrailingArrow.class, Location.class, Vector.class, int.class, BukkitTaskManager.class, ParticleInfo.class)
                .apply(loc, velocity, speed, taskManager, particleInfo);
    }

    static boolean is(Entity entity) {
        return WrapperFactory.isHandleInstance(entity, TrailingArrow.class);
    }

    static TrailingArrow cast(Entity entity) {
        return WrapperFactory.castHandle(entity, TrailingArrow.class);
    }

    @Override
    Arrow getBukkitEntity();

}
