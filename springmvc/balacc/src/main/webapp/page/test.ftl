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

<#if money??>
    i have money ${money} dollar
<#else >
    i am pool
</#if>

    <input type="text" id="test" name="test">
    <input type="button" value="提交" onclick="ons()">

<div id="part"></div>
<input type="button" value="333" onclick="adp()">
</body>
<script>

    function ons() {
        var url = "/test?";
        url += "test="+$("#test").val();
        jQuery.ajax({
            async:false,
            url: url,
            cache: false,
            success: function (data) {
                $("#page-inner").html(data);
            },
            error: function () {
                alert("fail");
            }
        });
    }

    function adp() {
        var str = "<a>show some message</a>";
        $("#part").html(str);
    }
</script>


</html>
