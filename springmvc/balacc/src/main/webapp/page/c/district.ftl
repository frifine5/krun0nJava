<#include "../common.ftl" >


<div>
    <span id="province">
        <select title="省" data-size="5">
        </select>
    </span>
    <span id="city">
        <select title="市" data-size="5">
        </select>
    </span>
    <span class="form-group" id="county">
        <select title="区/县" data-size="5">
        </select>
    </span>

    <button onclick="getDist(1)">点一下</button>
    <button onclick="showDist()">看一下</button>
</div>



<script>
    function showDist() {
        var s = $("#province option:selected").text()+"-"+$("#city option:selected").text()+"-"+$("#county option:selected").text();
        alert(s);
    }
    function getDist(type) {
        var url = "/district?";
        var destDiv;
        var divStr = "";
        var rtType;
        if (1 == type) {
            url += "type=1";
            destDiv = $("#province");
            divStr += "<select title=\"省\"  data-size=\"5\">";
            rtType = 2;
        } else if (2 == type) {
            url += "type=2&dcode=" + $("#province option:selected").text();
            destDiv = $("#city");
            divStr += "<select  title=\"市\" data-size=\"5\">";
            rtType = 3;
        } else if (3 == type) {
            url += "type=3&dcode=" + $("#city option:selected").text();
            destDiv = $("#county");
            divStr += "<select   title=\"区/县\" data-size=\"5\">";
        }
        jQuery.ajax({
            async: false,
            url: url,
            cache: false,
            success: function (data) {
                if (data.code == 0 && data.list != null) {
                    var list = data.list;
                    var len = list.length;
                    if(rtType == 2 || rtType == 3){
                        for (var i = 0; i < len; i++) {
                            divStr += "<option onclick='getDist(" + rtType + ")' value='" + list[i].dCode + "'>" + list[i].dName + "</option>";
                        }
                    }else{
                        for (var i = 0; i < len; i++) {
                            divStr += "<option  value='" + list[i].dCode + "'>" + list[i].dName + "</option>";
                        }
                    }
                    divStr += "</select>";

                    destDiv.html(divStr);
                }

            },
            error: function () {

            }
        });


    }
</script>

