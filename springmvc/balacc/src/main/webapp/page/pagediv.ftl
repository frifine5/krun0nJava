<#include "common.ftl" >
<div>
    <#if total?c >0 >
    <ul class="pagination">

        <#if pageNo?c == 1 >
        <li class="disabled"><a >&laquo;</a></li>
        <#else>
        <li><a onclick="chgPage(1, ${pageSize})">&laquo;</a></li>
        </#if>
        <#list 1..pageNo?c as i>
            <#if i == ${pageNo}>
                <li class="disabled"><a  onclick="chgPage(i, ${pageSize})">i</a></li>
            </#if>
                <li ><a  onclick="chgPage(i, ${pageSize})">i</a></li>
        </#list>
        <li><a onclick="chgPage('+${total}/${pageSize}+', ${pageSize})">&raquo;</a></li>
    </ul>

     </#if>

    <ul class="pagination">
        <li><a href="#">&laquo;</a></li>
        <li class="active"><a href="#">1</a></li>
        <li class="disabled"><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
        <li><a href="#">&raquo;</a></li>
    </ul>

</div>
