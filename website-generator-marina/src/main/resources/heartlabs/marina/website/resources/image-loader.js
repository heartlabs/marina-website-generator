let loadSimultaneously = 1;
let indexLoaded = 0;
let indexDontload = loadSimultaneously;
let loadingImages = [];

function startedLoading(img){
	img.classList.add("loading")
	img.addEventListener("load", finishedLoading);
	loadingImages.push(img);
	
	console.log("started loading", img);
	
	if (loadingImages.length > indexDontload){
		console.log("manage load");
		img.setAttribute("data-src", img.getAttribute("src"));
		img.setAttribute("data-started-loading", performance.now());
		img.src = "";		
	} 
}

function finishedLoading(){
	if (loadingImages.length == 0){
		console.warn("Unexpected call of 'finishedLoading()'");
		// no image was registered yet - function called too early
		return;
	}
	console.log("Finished loading of an image");
	for (i=indexLoaded; i<loadingImages.length;i++){
		let image = loadingImages[i];
		if (!image.complete 
			|| image.src == "" // firefox
			|| image.src.endsWith("/") || image.src.endsWith(".html")  // chromium
			){
			return;
		}
		
		// Sometimes an image has the complete property set despite not being loaded. (bug?)
		// This is a double check				
		let m = new Image();
		m.src = image.src;
		if (m.height == 0){
			let performanceEntries = performance.getEntriesByName(image.src);
			console.warn("Browser lied about complete loading of image", performanceEntries);
			return;
		}
		
		console.log("Image with index " + i + " finished loading", {image});
		
		indexLoaded = i;
		image.classList.remove("loading");
		
		if (loadingImages.length > indexDontload) {
			const imageToLoad = loadingImages[indexDontload];
			console.log("next image with index "+ indexDontload + "/" + loadingImages.length + " is ", imageToLoad, loadingImages);
			const actualSrc = imageToLoad.getAttribute("data-src");
			imageToLoad.src = actualSrc;
			imageToLoad.setAttribute("data-started-loading", performance.now());
		
			indexDontload = i + loadSimultaneously;
		}
	}
	
	// all images are loaded
	if (typeof(loadingFinished) === "function")
		loadingFinished();
	
}

/*

// Currently not working...
function measure(img){
	let started = img.getAttribute("data-started-loading");
	let time = started ? performance.now() - started : 0;
	let size = Number(img.getAttribute("data-size"));
	let speed = size / time;
	console.log("finished loading after " + time + "ms", img, size, speed + "bytes / ms");
	
	let performanceEntries = performance.getEntriesByName(img.src);
	console.log(performanceEntries);
	
	let times = [];
	times.push(time);
	
	for (p of performanceEntries){
		times.push(p.duration);
	}
	
	console.log(times);
	
	let longestDuration = Math.max(...times);
	
	if (longestDuration > 0){
		// We aim at a loading time of one second
		if (false && longestDuration < 800){
			let factor = 1000/longestDuration;
			loadSimultaneously = Math.floor(factor*loadSimultaneously);
			if (loadSimultaneously > 15){
				loadSimultaneously = 15;
			}
			console.log("Increased number of parallel loading images to " + loadSimultaneously + " because image loaded after " + longestDuration + "ms");
		}
		else if (longestDuration > 1500) {
			console.log("Image loaded unexpectedly slowly");
		}
		
	} 
}

function imageIsCached(url) {
   var img = new Image();
   img.src = url;
   return img.height != 0;
}
*/