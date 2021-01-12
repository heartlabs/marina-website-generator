package heartlabs.marina.website.generator;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class WebsiteGenerator {
	private static final String THUMBNAIL_JPG = "thumbnail.jpg";
	private static List<String> pageNames = Arrays.asList("index", "weddings", "lovestory", "about me", "contacts");
	
	public static void main(String[] args) throws IOException {
		File templateFolder = new File("src/main/resources/heartlabs/marina/website/templates");
		File resourcesFolder = new File("src/main/resources/heartlabs/marina/website/resources");
		File targetFolder = new File("output");
		File sourceImageFolder = new File("images");
		targetFolder.mkdirs();
		
		System.out.println(templateFolder.getAbsolutePath());
		
		FreemarkerRenderer freemarkerRenderer = new FreemarkerRenderer(templateFolder);
		DataModel dataModel = new DataModel();
		List<Page> pages = new ArrayList<>();
		dataModel.pages = pages;
		
		pageNames.stream()
			.map(n -> new File(templateFolder, n + ".ftlh"))
			.filter(File::exists)
			.forEach(templateFile -> processRootTemplate(targetFolder, sourceImageFolder, pages, templateFile));
		
		for (File resFile : resourcesFolder.listFiles()) {
			Files.copy(resFile.toPath(), new File(targetFolder, resFile.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		
		for (Page page: pages) {
			System.out.println("Rendering page " + page.title);
			dataModel.page = page;
			freemarkerRenderer.writeFileFromTemplate(page.templateName, dataModel, page.targetFile);
		}
		
		System.out.println("Done.");
	}

	private static void processRootTemplate(File targetFolder, File sourceImageFolder, List<Page> pages, File templateFile) {
		String templateName = templateFile.getName();
		
		Page page = new Page(targetFolder, templateName);
		pages.add(page);

		String templateBaseName = page.baseName;
		
		System.out.println("Preparing page " + templateBaseName );
		if (templateName.equals("about me.ftlh") || templateName.equals("contacts.ftlh")) {
			page.about = true;
		}
		if (templateName.equals("index.ftlh")) {
			page.hidden = true;
			page.title = null;
			page.carousel = true;
		}
		
		File pageSourceImageFolder = new File(sourceImageFolder, templateBaseName);
		if (pageSourceImageFolder.exists()) {
			File pageTargetImageFolder = new File(targetFolder, page.baseName);
			pageTargetImageFolder.mkdirs();
			
			Arrays.stream(pageSourceImageFolder.listFiles()) // 
				.sorted(Comparator.comparing(File::getName))
				.forEach(file -> {
					
					if (file.isDirectory()) {
						Page subPage = handleSubPage(pageTargetImageFolder, file);
						pages.add(subPage);
						page.subPages.add(subPage);
					} else {
						handlePageImage(pageTargetImageFolder, page, file);
					}
				});
		}
	}

	private static Page handleSubPage(File parentTargetDir, File subPageSourceDir) {
		String subPageName = subPageSourceDir.getName();
		File[] images = subPageSourceDir.listFiles();
		
		if (subPageName.contains("_")) {
			subPageName = subPageName.split("_")[1];
		}
		
		File subPageTargetDir = new File(parentTargetDir, subPageName);
		File thumbnailTargetFile = new File(subPageTargetDir, THUMBNAIL_JPG);
		File thumbnailSourceFile = new File(subPageSourceDir, THUMBNAIL_JPG);
		
		if (!thumbnailSourceFile.exists()) {
			thumbnailSourceFile = Arrays.stream(images) //
				.sorted(Comparator.comparing(File::getName))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("No image found in folder " + subPageSourceDir.getAbsolutePath()));
		}
		
		subPageTargetDir.mkdirs();
		
		ThumbnailGenerator.createThumbnail(thumbnailSourceFile, thumbnailTargetFile);
		
		Page subPage = new Page(subPageTargetDir, subPageName, "subpage.ftlh");
		subPage.hidden = true;
		subPage.rootDirPath = "../../";
		
		Arrays.stream(images) //
			.sorted(Comparator.comparing(File::getName)) //
			.forEach(sourceImageFile -> handlePageImage(subPageTargetDir, subPage, sourceImageFile));
			
		return subPage;
	}
	
	private static void handlePageImage(File targetImageFolder, Page page, File sourceImageFile) {
		page.images.add(sourceImageFile.getName());
		
		Path imageTargetPath = new File(targetImageFolder, sourceImageFile.getName()).toPath();
		
		try {
			Files.copy(sourceImageFile.toPath(), imageTargetPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
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
		
		public String getRootDir() {
			return page.getRootDir();
		}
		public String getResDir() {
			return page.getRootDir();
		}
		
	}
}
