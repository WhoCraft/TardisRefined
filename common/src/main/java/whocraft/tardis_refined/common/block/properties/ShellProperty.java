package whocraft.tardis_refined.common.block.properties;

import com.google.common.collect.Lists;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ShellProperty extends EnumProperty<ShellTheme> {
    protected ShellProperty(String string, Collection<ShellTheme> collection) {
        super(string, ShellTheme.class, collection);
    }

    public static ShellProperty create(String string) {
        return create(string, (shell) -> true);
    }

    public static ShellProperty create(String string, Predicate<ShellTheme> predicate) {
        return create(string, Arrays.stream(ShellTheme.values()).filter(predicate).collect(Collectors.toList()));
    }

    public static ShellProperty create(String string, ShellTheme... themes) {
        return create(string, Lists.newArrayList(themes));
    }

    public static ShellProperty create(String string, Collection<ShellTheme> collection) {
        return new ShellProperty(string, collection);
    }

    @Override
    public Collection<ShellTheme> getPossibleValues() {
        return List.of(ShellTheme.values());
    }
}
