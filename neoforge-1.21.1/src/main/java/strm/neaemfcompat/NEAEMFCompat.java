package strm.neaemfcompat;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(NEAEMFCompat.MOD_ID)
public class NEAEMFCompat {

    public static final String MOD_ID = "nea_emf_compat";

    public NEAEMFCompat(IEventBus modEventBus) {
        modEventBus.addListener(this::onClientSetup);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
    }

}