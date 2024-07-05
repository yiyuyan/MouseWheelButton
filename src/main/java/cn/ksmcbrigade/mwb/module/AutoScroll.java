package cn.ksmcbrigade.mwb.module;

import cn.ksmcbrigade.vmr.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.awt.event.KeyEvent;

public class AutoScroll extends Module {
    public AutoScroll() {
        super("hack.name.auto_scroll",false, KeyEvent.VK_F8);
    }

    @Override
    public void playerTick(Minecraft MC, @Nullable Player player) {
        if(player==null) return;
        MouseHandler handler = MC.mouseHandler;
        MC.mouseHandler.onScroll(MC.getWindow().getWindow(),handler.xpos(),handler.ypos());
        //MouseWheelButton.module.onMouseScroll();
    }
}
