package all.jaxrs.jsonGenerator;


import org.glassfish.jersey.server.wadl.config.WadlGeneratorConfig;
import org.glassfish.jersey.server.wadl.config.WadlGeneratorDescription;
import org.glassfish.jersey.server.wadl.internal.generators.WadlGeneratorGrammarsSupport;

import java.util.List;

public class JsonGeneratorConfig extends WadlGeneratorConfig {

    @Override
    public List<WadlGeneratorDescription> configure() {
        return generator(WadlGeneratorGrammarsSupport.class)
                .prop("grammarsStream", "application-grammars.xml")
                .prop("overrideGrammars", true)
                .descriptions();
    }
}
