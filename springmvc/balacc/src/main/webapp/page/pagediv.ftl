
<div class="pull-left">

 <#if total gt 0 && pageSize gt 0>
     <#assign pe = (total/pageSize)?ceiling>
     <ul class="pagination">
         <li><a>第${pageNo}页/共${pe}页</a></li>
        <#if pageNo == 1 >
        <li class="disabled"><a class="disabled" >&laquo;</a></li>
        <#else>
        <li><a onclick="chgPage(1, ${pageSize})">&laquo;</a></li>
        </#if>
        <#list 1..pe as i>
            <#if i lt pageNo-2 || i gt pageNo+2 >
            <#else >
                <#if i == pageNo>
                <li class="active"><a class="disabled" onclick="chgPage(${i}, ${pageSize})">${i}</a></li>
                <#else>
                <li ><a  onclick="chgPage(${i}, ${pageSize})">${i}</a></li>
                </#if>
            </#if>

        </#list>
        <#if pageNo == pe >
        <li class="disabled" ><a class="disabled" >&raquo;</a></li>
        <#else>
        <li><a onclick="chgPage(${pe}, ${pageSize})">&raquo;</a></li>
        </#if>

     </ul>


 </#if>

</div>
