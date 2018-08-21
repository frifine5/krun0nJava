<#include "common.ftl" >
<html>
<head>
    <title>
        首页
    </title>
</head>


<body>
<h1>
    首页的试炼
</h1>
<div>
    bingo!
   <#-- <img src = "<@s.url '/image/logo.png'/>"/>-->
</div>

<div class="select_contain">
    <div class="select_item clearfix left">
        <div class="select_tit left">选择节目:</div>
        <div class="select_module_con left">
            <div class="select_result">
                <span>选择节目</span>
                <div class="triangle"></div>
            </div>
            <ul>
                <li>节目1</li>
                <li>节目2</li>
                <li>节目3</li>
            </ul>
        </div>
    </div>
    <div class="select_item clearfix left">
        <div class="select_tit left">选择嘉宾:</div>
        <div class="select_module_con left">
            <div class="select_result">
                <span>选择嘉宾</span>
                <div class="triangle"></div>
            </div>
            <ul>
                <li>嘉宾1</li>
                <li>嘉宾2</li>
                <li>嘉宾3</li>
            </ul>
        </div>
    </div>
</div>

</body>
