package whocraft.tardis_refined.client.screen.selections;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.ScreenHelper;
import whocraft.tardis_refined.constants.ModMessages;

import java.awt.*;

public class SelectionScreen extends Screen {

    private SelectionScreenRun onSubmit;
    private SelectionScreenRun onCancel;
    private final Component title;

    private Button selectButton;
    private Button cancelButton;
    private ObjectSelectionList list;

    public int noiseX, noiseY, age;
    public double noiseAlpha;

    public static final ResourceLocation BUTTON_LOCATION = new ResourceLocation(TardisRefined.MODID, "textures/ui/save.png");
    private static final ResourceLocation BCK_LOCATION = new ResourceLocation(TardisRefined.MODID, "textures/ui/back.png");


    public SelectionScreen(Component title) {
        super(Component.translatable(ModMessages.UI_SHELL_SELECTION));
        this.title = title;
    }

    public void setEvents(SelectionScreenRun onSubmit, SelectionScreenRun onCancel) {
        this.onSubmit = onSubmit;
        this.onCancel = onCancel;
    }

    @Override
    protected void init() {
        super.init();

        if (onSubmit != null) {
            this.selectButton = this.addRenderableWidget(new ImageButton(width / 2 + 90, (height) / 2 + 35, 20, 18, 0, 0, 19, BUTTON_LOCATION, 20, 37, (arg) -> {
                this.onSubmit.onPress();
            }));
        }

        list = createSelectionList();

        this.addRenderableWidget(list);

    }


    public void addSubmitButton(int x, int y) {
        if (onSubmit != null) {
            this.selectButton = this.addRenderableWidget(new ImageButton(x, y, 20, 18, 0, 0, 19, BUTTON_LOCATION, 20, 37, (arg) -> {
                this.onSubmit.onPress();
            }));
        }
    }

    public void addCancelButton(int x, int y) {
        if (onCancel != null) {
            this.cancelButton = this.addRenderableWidget(new ImageButton(x, y, 20, 18, 0, 0, 19, BCK_LOCATION, 20, 37, (arg) -> {
                this.onCancel.onPress();
            }));
        }
    }

    public ObjectSelectionList createSelectionList() {
        return null;
    }

    public Component getSelectedDisplayName() {
        return null;
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        super.render(poseStack, i, j, f);
        ScreenHelper.renderWidthScaledText(title.getString(), poseStack, Minecraft.getInstance().font, width / 2, height / 2 - 100, Color.LIGHT_GRAY.getRGB(), 300, true);
    }

    @Override
    public void tick() {
        RandomSource random = Minecraft.getInstance().level.random;
        super.tick();
        this.age++;
        this.noiseX = random.nextInt(736);
        this.noiseY = random.nextInt(414);
        if (this.age % 3 == 0)
            this.noiseAlpha = random.nextDouble();
    }

    public interface SelectionScreenRun {
        void onPress();
    }
}
