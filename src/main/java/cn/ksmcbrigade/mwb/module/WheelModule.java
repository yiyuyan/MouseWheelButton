package cn.ksmcbrigade.mwb.module;

import cn.ksmcbrigade.mwb.MouseWheelButton;
import cn.ksmcbrigade.vmr.module.Config;
import cn.ksmcbrigade.vmr.module.Module;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static cn.ksmcbrigade.vmr.module.Config.configDir;

@Mod.EventBusSubscriber(value = Dist.CLIENT,modid = MouseWheelButton.MODID)
public class WheelModule extends Module {
    public WheelModule() throws IOException {
        super("hack.name.mouse_wheel",false, KeyEvent.VK_F9,new Config(new File("MouseWheel"),new JsonObject()),false);
    }

    @Override
    public void enabled(Minecraft MC) throws IOException {
        File pathFile = new File(configDir,getConfig().file.getPath()+".json");
        this.getConfig().setData(JsonParser.parseString(Files.readString(pathFile.toPath())).getAsJsonObject());
    }

    public void onMouseScroll(){
        if(!this.enabled) return;
        for(String key:MouseWheelButton.module.getConfig().data.keySet()){
            KeyMapping.click(InputConstants.getKey(key));
        }
    }

    @SubscribeEvent
    public static void onMouse(InputEvent.MouseScrollingEvent event){
        if(!MouseWheelButton.module.enabled) return;
        for(String key:MouseWheelButton.module.getConfig().data.keySet()){
            KeyMapping.click(InputConstants.getKey(key));
        }
    }
}
