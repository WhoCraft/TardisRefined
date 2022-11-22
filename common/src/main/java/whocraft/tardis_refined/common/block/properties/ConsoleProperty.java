package whocraft.tardis_refined.common.block.properties;

import com.google.common.collect.Lists;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ConsoleProperty extends EnumProperty<ConsoleTheme> {
    protected ConsoleProperty(String string, Collection<ConsoleTheme> collection) {
        super(string, ConsoleTheme.class, collection);
    }

    public static ConsoleProperty create(String string) {
        return create(string, (shell) -> {
            return true;
        });
    }

    public static ConsoleProperty create(String string, Predicate<ConsoleTheme> predicate) {
        return create(string, (Collection) Arrays.stream(ConsoleTheme.values()).filter(predicate).collect(Collectors.toList()));
    }

    public static ConsoleProperty create(String string, ConsoleTheme... themes) {
        return create(string, (Collection) Lists.newArrayList(themes));
    }

    public static ConsoleProperty create(String string, Collection<ConsoleTheme> collection) {
        return new ConsoleProperty(string, collection);
    }

    @Override
    public Collection<ConsoleTheme> getPossibleValues() {
        return List.of(ConsoleTheme.values());
    }
}
