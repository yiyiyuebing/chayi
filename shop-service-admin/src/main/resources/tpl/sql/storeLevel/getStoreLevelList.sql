SELECT * FROM store_level
WHERE 1=1
<#if statue??> and statue = ${statue} </#if>
order by sort desc;
