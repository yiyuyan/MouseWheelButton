package cn.ksmcbrigade.mwb.mixin;

import cn.ksmcbrigade.mwb.MouseWheelButton;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.controls.KeyBindsList;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.util.List;

@Mixin(KeyBindsList.KeyEntry.class)
public abstract class ConMixin extends KeyBindsList.Entry {

    @Shadow @Final private Button resetButton;
    @Shadow @Final private Button changeButton;

    @Shadow protected abstract void refreshEntry();

    @Shadow @Final private KeyMapping key;

    @Unique
    public Button mouseWheelButton$mw;
    @Unique
    public boolean mouseWheelButton$init = false;

    @Inject(method = "<init>",at = @At("TAIL"))
    public void init(KeyBindsList this$0, KeyMapping p_193916_, Component p_193917_, CallbackInfo ci){
            this.mouseWheelButton$mw = Button.builder(Component.translatable("controls.mwb.mw"), (p_269616_) -> {
                    final JsonObject object = MouseWheelButton.module.getConfig().data;
                    object.addProperty(key.getKey().getName(),this.key.getKey().getValue());
                    try {
                        MouseWheelButton.module.getConfig().setData(object);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    this.refreshEntry();
            }).bounds(0, 0, 35, 20).createNarration((p_253313_) -> Component.translatable("narrator.controls.mwb.wheel", p_193917_)).build();
            this.mouseWheelButton$init = true;
            this.refreshEntry();
    }

    @Inject(method = "render",at = @At("TAIL"))
    public void render(GuiGraphics p_281805_, int p_281298_, int p_282357_, int p_281373_, int p_283433_, int p_281932_, int p_282224_, int p_282053_, boolean p_282605_, float p_281432_, CallbackInfo ci){
        this.mouseWheelButton$mw.setX(p_281373_ + 105 - 40);
        this.mouseWheelButton$mw.setY(p_282357_);
        this.mouseWheelButton$mw.render(p_281805_, p_282224_, p_282053_, p_281432_);
    }

    @Inject(method = {"children","narratables"},at = @At("RETURN"), cancellable = true)
    public void keyButtons(CallbackInfoReturnable<List<? extends GuiEventListener>> cir){
        cir.setReturnValue(ImmutableList.of(this.changeButton, this.resetButton,this.mouseWheelButton$mw));
    }

    @Inject(method = "refreshEntry",at = @At("HEAD"))
    public void refresh(CallbackInfo ci){
        if(mouseWheelButton$init) this.mouseWheelButton$mw.active = !MouseWheelButton.module.getConfig().data.has(this.key.getKey().getName());
    }
}
