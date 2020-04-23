<#include "helpers/body-layout.ftlh">
<#include "helpers/slideshow.ftlh">

<@bodyLayout>
	<#if currentPage.images?has_content>
   		<@slideshow page=currentPage/>
   	</#if>
   	<section id=intro>
   		<h2 class=stroke><span>Welcome</span></h1>
	   	<div id="thumbnails">
	   	<#list pages as page>
	   		<#if page.subPages?has_content>
	   			<#assign subPage = page.subPages[0]>
				<a href="${page.baseName}.html" class="thumbnail card"> <span class=title>${page.title}</span> <img src="${page.baseName}/${subPage.baseName}/thumbnail.jpg"></a>
			</#if>
		</#list>
	   	</div>
   	</section>
</@bodyLayout>