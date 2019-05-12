package heartlabs.marina.website.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.io.Writer;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerRenderer {
	private final Configuration freemarkerConfig = new Configuration(Configuration.VERSION_2_3_28);

	public FreemarkerRenderer(File templateFolder) {
		ClassTemplateLoader classTemplateLoader = new ClassTemplateLoader(FreemarkerRenderer.class, "");

		if (templateFolder.isDirectory()) {
			FileTemplateLoader fileTemplateLoader;

			try {
				fileTemplateLoader = new FileTemplateLoader(templateFolder);
			} catch (IOException e) {
				throw new UncheckedIOException("Could not set Freemarker template folder", e);
			}

			MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(new TemplateLoader[] { fileTemplateLoader, classTemplateLoader });
			freemarkerConfig.setTemplateLoader(multiTemplateLoader);
		} else {
			freemarkerConfig.setTemplateLoader(classTemplateLoader);
		}
		// Got these settings from the tutorial:
		// https://freemarker.apache.org/docs/pgui_quickstart_createconfiguration.html
		freemarkerConfig.setDefaultEncoding("UTF-8");
		freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		freemarkerConfig.setLogTemplateExceptions(false);
		freemarkerConfig.setWrapUncheckedExceptions(true);
	}

	public void writeFileFromTemplate(String templateName, Object dataModel, File target) {
		try (FileOutputStream fos = new FileOutputStream(target); Writer writer = new OutputStreamWriter(fos, "UTF-8")) {
			Template temp = freemarkerConfig.getTemplate(templateName);

			temp.process(dataModel, writer);
		} catch (TemplateException | IOException e) {
			throw new RuntimeException("Could not write file using freemarker template", e);
		}
	}
}
