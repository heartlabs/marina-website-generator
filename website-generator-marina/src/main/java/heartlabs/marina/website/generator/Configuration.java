package heartlabs.marina.website.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Configuration {
	public static final Map<String, Consumer<Page>> pageConfig = new HashMap<>();
	
	static {
		pageConfig.put("index", p -> {
			p.hidden = true;
			p.title = null;
			p.carousel = true;
		});
		pageConfig.put("weddings", null);
		pageConfig.put("lovestory", null);
		
		pageConfig.put("about me", p -> p.about = true);
		pageConfig.put("contacts", p -> p.about = true);
	}
	
	public static void configure(Page page) {
		
		Consumer<Page> configurer = Configuration.pageConfig.get(page.baseName);
		
		if (configurer != null)
			configurer.accept(page);
	}
}
