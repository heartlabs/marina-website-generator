<#include "helpers/body-layout.ftlh">
<#include "helpers/thumbnails.ftlh">

<@bodyLayout>
   	<#if currentPage.subPages?has_content>
   		<@thumbnails page=currentPage/>
   	</#if>
</@bodyLayout>