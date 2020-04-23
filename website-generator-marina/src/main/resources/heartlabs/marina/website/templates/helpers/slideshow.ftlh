<#macro slideshow page>
	<#list page.images>
		<div class="slideshow-container">

      	<#items as image>
		  <div class="mySlides fade">
			<img src="${page.baseName}/${image}">
		  </div>
	  	</#items>
			<button class="prev" onClick="plusSlides(-1)"> < </button>
			<button class="next" onClick="plusSlides(1)"> > </button>
		</div>
	</#list>
	
	<script>
		var slideTimeout;
		var slideIndex = 1;
		showSlides(slideIndex);
	
		// Next/previous controls
		function plusSlides(n) {
		  showSlides(slideIndex += n, true);
		}
	
		// Thumbnail image controls
		function currentSlide(n) {
		  showSlides(slideIndex = n);
		}
	
		function showSlides(slide, userControl) {
		  var i;
		  var slides = document.getElementsByClassName("mySlides");
		  for (i = 0; i < slides.length; i++) {
			slides[i].style.display = "none"; 
		  }
		  
		  slideIndex = slide != undefined ? slide : slideIndex+1;
		  if (slideIndex < 0) {slideIndex = slides.length-1}
		  if (slideIndex >= slides.length) {slideIndex = 0} 
		  
		  if (!slides[slideIndex].firstElementChild.complete){
		  	// if slide image has not finished loading jump back to first
		  	slideIndex=0;
		  }
		  
		  slides[slideIndex].style.display = "block"; 
		  if (userControl)
		  	clearTimeout(slideTimeout);
		  else
		  	slideTimeout = setTimeout(showSlides, 6000);
		}
	</script>
</#macro>
