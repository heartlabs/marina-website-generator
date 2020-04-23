<#macro bodyLayout>
<html>
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<link rel="stylesheet" type="text/css" href="${resDir}general.css" />
	<link rel="stylesheet" type="text/css" href="${resDir}carousel.css" />
	
	<link rel="shortcut icon" type="image/png" href="${resDir}favicon.png"/>

	<title>Marina Orlich Photography</title>
	
	<link rel="preload" href="https://fonts.googleapis.com/css?family=Dancing+Script:400,700|Shadows+Into+Light" as="style">
	<link href="https://fonts.googleapis.com/css?family=Dancing+Script:400,700|Shadows+Into+Light" rel="stylesheet">
	
</head>
<body onscroll="onScrollBody()">
<div class=container id=top>

	<header>
		<a href="${rootDir}index.html" class=logo>
			<img src="${resDir}colibri_logo.svg" />
		</a>
	</header>
	<nav class=menu>
			<!-- "Hamburger menu" / "Bar icon" to toggle the navigation links -->
			<a href="javascript:void(0);" class="" onclick="myFunction()">
			<img src="${resDir}hamburger.svg" class="hamburger-icon icon"/>
			</a>
		<ul id=topnav>
		<#list pages as page>
			<#if page.hidden>
			<#else> 
				<#if page == currentPage>
				<li><span class='item current'>${page.title}</span></li>
				<#else>
				<li><a href="${rootDir}${page.baseName}.html" class='item'>${page.title}</a></li>
				</#if>
			</#if>
		</#list>
		</ul>
		
	</nav>
	
	<script>
		function adaptToScreenSize(){
			var viewportWidth =  Math.max(document.documentElement.clientWidth, window.innerWidth || 0);
			if (viewportWidth < 640){
				document.querySelector(".container").classList.add("small-screen")
				document.querySelector(".hamburger-icon").style = "display: block"
				document.querySelector("#topnav").style = "display: none"
			} else {
				document.querySelector(".container").classList.remove("small-screen")
				document.querySelector(".hamburger-icon").style = "display: none"
				document.querySelector("#topnav").style = "display: block"
			}
		}
		
		function onScrollBody(){
			let toplink = document.getElementById("toplink");
						
			if (document.body.scrollTop > 500){
				toplink.style.opacity=1
			} else {
				toplink.style.opacity=0
			} 
		}
		
		window.addEventListener('resize', adaptToScreenSize);
		adaptToScreenSize();
	</script>
	<main>
		<#if currentPage.title??>
		<h1 class=stroke><span>${currentPage.title}</span></h1>
		</#if>
		<#nested>
		<a href="#top" class="toplink hidden-small-screen" id=toplink>
			<img src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiA/PjwhRE9DVFlQRSBzdmcgIFBVQkxJQyAnLS8vVzNDLy9EVEQgU1ZHIDEuMS8vRU4nICAnaHR0cDovL3d3dy53My5vcmcvR3JhcGhpY3MvU1ZHLzEuMS9EVEQvc3ZnMTEuZHRkJz48c3ZnIGhlaWdodD0iNTEycHgiIGlkPSJMYXllcl8xIiBzdHlsZT0iZW5hYmxlLWJhY2tncm91bmQ6bmV3IDAgMCA1MTIgNTEyOyIgdmVyc2lvbj0iMS4xIiB2aWV3Qm94PSIwIDAgNTEyIDUxMiIgd2lkdGg9IjUxMnB4IiB4bWw6c3BhY2U9InByZXNlcnZlIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj48cG9seWdvbiBwb2ludHM9IjM5Ni42LDM1MiA0MTYsMzMxLjMgMjU2LDE2MCA5NiwzMzEuMyAxMTUuMywzNTIgMjU2LDIwMS41ICIvPjwvc3ZnPg==" 
			alt="Go back to top">
		</a>
	</main>
	<footer>
		<section>
			<h2>Contacts</h2>
			<a href="tel:+4367762511669" target="_blank">+43 677/625 11 669</a>
			<a href="mailto:marinakoroleva1204@gmail.com" target="_blank">marinakoroleva1204@gmail.com</a>
		</section>
		<section class=social-bar>
			<h2>Social Media</h2>
			<a href="https://www.instagram.com/marina.orlich" target="_blank"><img src=${resDir}instagram.svg class=socialmedia-icon /></a>
		</section>
	</footer>
</div>

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