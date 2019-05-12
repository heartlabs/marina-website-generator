package heartlabs.marina.website.generator;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class WebsiteGenerator {
	public static void main(String[] args) {
		File templateFolder = new File("src/main/resources/heartlabs/marina/website/templates");
		File targetFolder = new File("output");
		File imageFolder = new File("images");
		targetFolder.mkdirs();
		
		System.out.println(templateFolder.getAbsolutePath());
		
		FreemarkerRenderer freemarkerRenderer = new FreemarkerRenderer(templateFolder);
		DataModel dataModel = new DataModel();
		List<Page> pages = new ArrayList<>();
		dataModel.pages = pages;
		
		sortedChildren(templateFolder)
			.forEach(templateFile -> { //
				
			String templateName = templateFile.getName();
			if (templateName.endsWith(".ftlh")) {
				String templateBaseName = templateName.replaceAll("\\.ftlh$", "");
				String targetFileName = templateBaseName + ".html";
				
				Page page = new Page();
				pages.add(page);
				
				page.templateName = templateName;
				page.targetFile = new File(targetFolder, targetFileName);
				page.images = new ArrayList<>();
				page.baseName = templateBaseName;
				page.title = Character.toUpperCase(templateBaseName.charAt(0)) + templateBaseName.substring(1);
				page.title = page.title.replaceAll("-", " ");
				
				String imageSubfolderPath = "images/" + templateBaseName + "/";
				File pageImageFolder = new File(imageFolder, templateBaseName);
				if (pageImageFolder.exists()) {
					sortedChildren(pageImageFolder) //
						.forEach(file -> {
							page.images.add(imageSubfolderPath + file.getName());
							
							File targetImageFolder = new File(targetFolder, imageSubfolderPath);
							targetImageFolder.mkdirs();
							
							Path imageTargetPath = new File(targetImageFolder, file.getName()).toPath();
							try {
								Files.copy(file.toPath(), imageTargetPath);
							} catch (IOException e) {
								throw new UncheckedIOException(e);
							}
						});
				}
			}
		});
		
		for (Page page: pages) {
			dataModel.page = page;
			freemarkerRenderer.writeFileFromTemplate(page.templateName, dataModel, page.targetFile);
		}
	}
	
	private static Stream<File> sortedChildren(File parent){
		return Arrays.stream(parent.listFiles()) // 
				.sorted(Comparator.comparing(File::getName));
	}
	
	public static class DataModel {
		private List<Page> pages = new ArrayList<>();
		private Page page;

		public List<Page> getPages() {
			return pages;
		}
		
		public Page getCurrentPage() {
			return page;
		}

	}
	
	public static class Page {
		public String templateName;
		public String baseName;
		public String title;
		public File targetFile;
		public List<String> images;
		public Object dataModel;
		
		public String getTemplateName() {
			return templateName;
		}
		public String getBaseName() {
			return baseName;
		}
		public String getTitle() {
			return title;
		}
		public File getTargetFile() {
			return targetFile;
		}
		public List<String> getImages() {
			return images;
		}
	}
}


