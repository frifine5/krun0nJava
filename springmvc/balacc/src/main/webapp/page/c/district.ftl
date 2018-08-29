<#include "../common.ftl" >


<div>
    <span id="province">
        <select title="省" data-size="5" name="省">
            <option value="" disabled selected>-请选择-</option>
        </select>
    </span>
    <span id="city">
        <select title="市" data-size="5" name="市">
            <option value="" disabled selected>-请选择-</option>
        </select>
    </span>
    <span id="county">
        <select title="区/县" data-size="5"  name="县">
            <option value="" disabled selected>-请选择-</option>
        </select>
    </span>

    <button onclick="getDist(1)">点一下</button>
    <button onclick="showDist()">看一下</button>
</div>



<script>
    function showDist() {
        var s1 = $("#province option:selected").text()+"-"+$("#city option:selected").text()+"-"+$("#county option:selected").text();
        alert(s1);
        var s2 = $("#province option:selected").val()+"-"+$("#city option:selected").val()+"-"+$("#county option:selected").val();
        alert(s2);
    }
    function getDist(type) {
        var url = "/district?";
        var destDiv;
        var divStr = "";
        var rtType;
        if (1 == type) {
            url += "type=1";
            destDiv = $("#province");
            divStr += "<select title=\"省\"  data-size=\"5\" name=\"省\">";
            divStr += "<option value=\"\" disabled selected>-请选择-</option>";
            rtType = 2;
            resetCity();
            resetCounty();
        } else if (2 == type) {
            url += "type=2&dcode=" + $("#province option:selected").val();
            destDiv = $("#city");
            divStr += "<select  title=\"市\" data-size=\"5\" name=\"市\">";
            divStr += "<option value=\"\" disabled selected>-请选择-</option>";
            rtType = 3;
            resetCounty();
        } else if (3 == type) {
            url += "type=3&dcode=" + $("#city option:selected").val();
            destDiv = $("#county");
            divStr += "<select   title=\"区/县\" data-size=\"5\"  name=\"县\" >";
            divStr += "<option value=\"\" disabled selected>-请选择-</option>";
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

    function resetCity(){
        var cityStr = "<select title=\"市\" data-size=\"5\" name=\"市\">\n" +
                "            <option value=\"\" disabled selected>-请选择-</option>\n" +
                "        </select>";
        $("#city").html(cityStr);
    }

    function resetCounty() {
        var countyStr = "<select title=\"区/县\" data-size=\"5\"  name=\"县\">\n" +
                "            <option value=\"\" disabled selected>-请选择-</option>\n" +
                "        </select>";
        $("#county").html(countyStr);
    }
</script>

