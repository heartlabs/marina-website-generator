package heartlabs.marina.website.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Page {
	public String templateName;
	public String baseName;
	public String title;
	public File targetFile;
	public List<Image> images;
	public List<Page> subPages;
	public boolean hidden;
	public boolean about;
	public boolean carousel;
	public boolean crawlable = true;
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
	
	public static class Image {
		String src;
		long size;
		
		public Image(String src, long size) {
			super();
			this.src = src;
			this.size = size;
		}

		public long getSize() {
			return size;
		}
		
		public String getSrc() {
			return src;
		}
		
		
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
	public List<Image> getImages() {
		return images;
	}
	
	public void addImage(String src, long size) {
		images.add(new Image(src, size));
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
	public boolean isCrawlable() {
		return crawlable;
	}
}