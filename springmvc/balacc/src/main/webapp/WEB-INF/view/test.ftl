<#include "common.ftl" >
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
动物名 - 价格<br/>
<#list animals>
    <#items as animal>
        ${animal.name} - ${animal.price}<br/>
    </#items>
<#else>
    there is no animal.
</#list>

<#if (score >=10)>
score is bigger than ten.
</#if><br/>

${(team?split(","))[1]}<br/>

    <#list sexMap?keys as k>
        ${k}---${sexMap[k]}<br/>
    </#list>
<hr/>
<button onclick="qDate('2018-08-17')">点一下</button>

<div id="show"></div>

</body>
<script>
    function qDate(date) {
        alert(date);
        $.ajax({
            type: "POST",
            data: {
                "date": date
            },
            url: "/test/getDb",
            dataType: "json",
            success: function (data) {
                // window.location.reload();
                alert(data);
                var dataStr = "";
                for (var i = 0; i < data.length; i++) {
                    dataStr += data[i] + "<br/>";
                }
                $("#show").html(dataStr);

            }

        })

    }


</script>
</html>
