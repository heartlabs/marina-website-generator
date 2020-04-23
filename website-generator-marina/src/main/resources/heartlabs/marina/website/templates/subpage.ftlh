<#include "helpers/body-layout.ftlh">

<@bodyLayout>
	<script>
		const loadSimultaneously = 3;
		let indexLoaded = 0;
		let indexDontload = loadSimultaneously;
		
		
		function startedLoading(img){
			img.classList.add("loading")
			loadingImages.push(img);
			
			if (loadingImages.length > indexDontload){
				img.setAttribute("data-src", img.getAttribute("src"));
				img.src = "";
			} 
		}
		
		function finishedLoading(){
			if (loadingImages === undefined){
				// no image was registered yet - function called too early
				return;
			}
			
			for (i=indexLoaded; i<loadingImages.length;i++){
				let image = loadingImages[i];
				if (!image.complete){
					return;
				}
				
				indexLoaded = i;
				image.classList.remove("loading");
				
				if (loadingImages.length > indexDontload) {
					
					imageToLoad = loadingImages[indexDontload]
					const actualSrc = imageToLoad.getAttribute("data-src");
					imageToLoad.src = actualSrc;
					indexDontload = i + loadSimultaneously;
				}
			}
			
			// all images are loaded
			document.getElementById("spinner").style.display = "none";
		}
	</script>
	
	<#list currentPage.images>
		<div id="images">
		<#items as image>
		  <img src="${image}" onload="finishedLoading()">
		</#items> 
		</div>
	</#list>
	
	<script>
		let loadingImages = [];
		let images = document.querySelectorAll("#images > img")
		
		for (i = 0; i < images.length; i++) {
			startedLoading(images[i])
		}
		
		var div = document.createElement("div");
		div.id = "spinner";
		document.getElementById("images").appendChild(div);
		
		// If all images loaded faster than this script was even loaded we might already have finished and no onload will be fired anymore
		// No idea if this can happen but stay on the safe side
		finishedLoading();
	</script>
	
</@bodyLayout>
