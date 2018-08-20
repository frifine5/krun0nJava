<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome!</title>
</head>
<body>
<#--注释部分-->
<#--下面使用插值-->
<h1>Welcome ${user}!</h1>
<p>We have these animals:</p>


<#if (score==10)>
abcdefg
</#if><br/>

${(team?split(","))[1]}<br/>

    <#list sexMap?keys as k>
        ${k}---${sexMap[k]}<br/>
    </#list>
</body>
</html>
