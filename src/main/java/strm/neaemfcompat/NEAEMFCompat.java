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
        // Compat logic is handled directly in the mixin via EMFCompat.updateArmPause
        // This method remains as a hook point for future initialization if needed
    }
}
