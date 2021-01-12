package heartlabs.marina.website.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Page {
	public String templateName;
	public String baseName;
	public String title;
	public File targetFile;
	public List<String> images;
	public List<Page> subPages;
	public boolean hidden;
	public boolean about;
	public boolean carousel;
	public String rootDirPath = "";
	
	public Page(File targetFolder, String templateName) {
		this(targetFolder, templateName.replaceAll("\\.ftlh$", ""), templateName);
	}
	
	public Page(File targetFolder, String baseName, String templateName) {
		String targetFileName = baseName + ".html";
		
		this.templateName = templateName;
		this.targetFile = new File(targetFolder, targetFileName);
		this.subPages = new ArrayList<>();
		this.images = new ArrayList<>();
		this.baseName = baseName;
		this.title = Character.toUpperCase(baseName.charAt(0)) + baseName.substring(1);
		this.title = this.title.replaceAll("-", " ");
	}

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
	public List<Page> getSubPages() {
		return subPages;
	}
	public boolean isHidden() {
		return hidden;
	}
	public boolean isCarousel() {
		return carousel;
	}
	public boolean isAbout() {
		return about;
	}
	public String getRootDir() {
		return rootDirPath;
	}
}