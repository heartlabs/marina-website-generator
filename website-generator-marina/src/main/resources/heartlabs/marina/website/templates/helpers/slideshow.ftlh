<#macro slideshow images>
	<#list images>
	 <!-- Slideshow container -->
		<div class="slideshow-container">

      	<#items as image>
		  <!-- Full-width images with number and caption text -->
		  <div class="mySlides fade">
			<div class="numbertext">${image?counter} / ${images?size}</div>
			<img src="${image}" style="width:100%">
			<div class="text"></div>
		  </div>
	  	</#items>
		  	
		</div>
	  </#list>
	<#list images>
		<!-- The dots/circles -->
		
		<div style="text-align:center" class=dots>
		<#items as image>
		  <span class="dot" onclick="currentSlide(${image?counter})"></span>
		</#items> 
		</div>
	</#list>
</#macro>
