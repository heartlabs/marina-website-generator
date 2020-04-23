<#macro thumbnails page>
	<#list page.subPages>
		<div id="thumbnails">
		<#items as subPage>
		  <a href="${page.baseName}/${subPage.baseName}/${subPage.baseName}.html" class="thumbnail highlight-content"> <img src="${page.baseName}/${subPage.baseName}/thumbnail.jpg"> <span class=description>${subPage.title}</span></a>
		</#items> 
		</div>
	</#list>
</#macro>
