import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ExamplePlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        target.getTasks().create("PluginTaskHello", HelloWorldTask.class);
    }
}
