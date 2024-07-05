package cn.ksmcbrigade.mwb;

import cn.ksmcbrigade.mwb.module.AutoScroll;
import cn.ksmcbrigade.mwb.module.WheelModule;
import cn.ksmcbrigade.vmr.uitls.ModuleUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod(MouseWheelButton.MODID)
public class MouseWheelButton {

    public static final String MODID = "mwb";

    public static WheelModule module;

    public static AutoScroll auto = new AutoScroll();

    static {
        try {
            module = new WheelModule();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MouseWheelButton() {
        MinecraftForge.EVENT_BUS.register(this);
        ModuleUtils.add(module);
        ModuleUtils.add(auto);
    }
}
