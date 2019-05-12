<#macro bodyLayout>
<html>
<head>
	<meta charset="UTF-8" />

	<link rel="stylesheet" type="text/css" href="general.css" />
	<link rel="stylesheet" type="text/css" href="carousel.css" />

	<title>Marina Orlich Photography</title>
	
	<link rel="preload" href="https://fonts.googleapis.com/css?family=Dancing+Script:400,700|Shadows+Into+Light" as="style">
	<link href="https://fonts.googleapis.com/css?family=Dancing+Script:400,700|Shadows+Into+Light" rel="stylesheet">
<!--	
	<script src="https://cdn.jsdelivr.net/npm/@glidejs/glide" /> 
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@glidejs/glide/dist/css/glide.core.min.css" />
-->
	
</head>
<body>
<div class=container>

	<header>
		<a href="index.html">
		<div class=logo>
			<div>Marina</div>
			<div class=heavy>Orlich</div>
			<div class=light>Photography</div>
		</div>
		</a>
	</header>
	<nav class=menu>
			<!-- "Hamburger menu" / "Bar icon" to toggle the navigation links -->
			<a href="javascript:void(0);" class="" onclick="myFunction()">
			<img src="hamburger.svg" class="hamburger-icon icon"/>
			</a>
		<ul id=topnav>
		<#list pages as page>
			<#if page == currentPage>
			<li><span class='item current'>${page.title}</span></li>
			<#else>
			<li><a href="${page.baseName}.html" class='item'>${page.title}</a></li>
			</#if>
		</#list>
		</ul>
		
	</nav>
	
	<script>
		// var viewportWidth =  Math.max(document.documentElement.clientWidth, window.innerWidth || 0);
		if (screen.width < 600){
			document.querySelector(".container").classList.add("small-screen")
			document.querySelector(".hamburger-icon").style = "display: block"
			document.querySelector("#topnav").style = "display: none"
		}
	</script>
	<main>
		<h1>${currentPage.title}</h1>
	   <@slideshow images=currentPage.images/>
		<#nested>
	</main>
	<footer>
		<div id=socialmedia-bar>
			<a href="instagram"><img src=instagram.svg class=socialmedia-icon /> <div>follow me on Instagram</div></a>
		</div>
	</footer>
</div>

<#if currentPage.images?has_content>

<script>
	var slideIndex = 1;
	showSlides(slideIndex);

	// Next/previous controls
	function plusSlides(n) {
	  showSlides(slideIndex += n);
	}

	// Thumbnail image controls
	function currentSlide(n) {
	  showSlides(slideIndex = n);
	}

	function showSlides() {
	  var i;
	  var slides = document.getElementsByClassName("mySlides");
	  for (i = 0; i < slides.length; i++) {
		slides[i].style.display = "none"; 
	  }
	  slideIndex++;
	  if (slideIndex > slides.length) {slideIndex = 1} 
	  slides[slideIndex-1].style.display = "block"; 
	  setTimeout(showSlides, 6000); // Change image every 2 seconds
	}
</script>
</#if>
<script>
/* Toggle between showing and hiding the navigation menu links when the user clicks on the hamburger menu / bar icon */
function myFunction() {
  var x = document.getElementById("topnav");
  if (x.style.display === "block") {
    x.style.display = "none";
  } else {
    x.style.display = "block";
  }
}
</script>
</body>
</html>
</#macro>