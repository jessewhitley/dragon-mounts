/*
** 2016 March 08
**
** The author disclaims copyright to this source code. In place of
** a legal notice, here is a blessing:
**    May you do good and not evil.
**    May you find forgiveness for yourself and forgive others.
**    May you share freely, never taking more than you give.
 */
package info.ata4.minecraft.dragon.server.entity.breeds;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.util.IStringSerializable;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public enum EnumDragonBreed implements IStringSerializable {
    
    AIR(0, DragonBreedAir.class),
    END(1, DragonBreedEnd.class),
    FIRE(2, DragonBreedFire.class),
    FOREST(3, DragonBreedForest.class),
    GHOST(4, DragonBreedGhost.class),
    ICE(5, DragonBreedIce.class),
    NETHER(6, DragonBreedNether.class),
    WATER(7, DragonBreedWater.class),
    GOLDEN(8, DragonBreedGold.class);
    
    public static final EnumDragonBreed DEFAULT = END;
    
    // create static bimap between enums and meta data for faster and easier
    // lookups
    public static final BiMap<EnumDragonBreed, Integer> META_MAPPING =
        ImmutableBiMap.copyOf(Arrays.asList(values()).stream()
            .collect(Collectors.toMap(Function.identity(), EnumDragonBreed::getMeta)));
    
    private final DragonBreed breed;
    
    // this field is used for block metadata and is technically the same as
    // ordinal(), but it is saved separately to make sure the values stay
    // constant after adding more breeds in unexpected orders
    private final int meta;
    
    private EnumDragonBreed(int meta, Class<? extends DragonBreed> factory) {
        try {
            breed = factory.getDeclaredConstructor(EnumDragonBreed.class).newInstance(this);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalArgumentException ex) {
            throw new RuntimeException("Incompatible breed factory " + factory, ex);
        } catch (SecurityException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        
        this.meta = meta;
    }
    
    public DragonBreed getBreed() {
        return breed;
    }
    
    public int getMeta() {
        return meta;
    }

    @Override
    public String getName() {
        return name().toLowerCase();
    }
    
}
