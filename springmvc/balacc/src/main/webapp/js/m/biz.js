






/***********************************************  */
/* 锚点跳转 传入#值*/
function swapAnchor(loc) {
    window.location.href = loc;
}


function getEntrOnline() {
    var entname = $("#entname").val();
    var uniscid = $("#uniscid").val();
    var r = confirm("是否从法人库获取["+entname+","+uniscid+"]企业信息");
    if(r == true){
        var url = "/page/addSeal?";
        url += "entname=" + entname;
        url += "&uniscid=" + uniscid;
        jQuery.ajax({
            async: false,
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

}


function clearPageWhich(no) {
    switch (no){
        case 1:
            $("#firPage :input").val("");break;
        case 2:
            $("#secPage :input").val("");break;
        case 3:
            $("#thiPage :input").val("");break;
    }
}

function showdate(){
    var msg = "date1="+$("#estdate").val()+",date2="+$("#apprdate").val();

    alert(msg);
}
/***********************************************  */

function changeInner(url) {
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

function doLogin() {
    var url="/doLogin?";
    url += "account="+$("#account").val();
    url += "&pwd="+$("#pwd").val();
    url += "&vCode="+$("#vCode").val();
    window.location.href=url;
}



function doQuery() {
    var url = "/seal?";
    url += "code=" + $("#sealCode").val();
    url += "&name=" + $("#sealName").val();
    url += "&unit=" + $("#unitName").val();
    jQuery.ajax({
        async: false,
        url: url,
        cache: false,
        success: function (data) {
            $("#page-inner").html(data);
            uptCacheUrl(url);
        },
        error: function () {
            alert("fail");
        }
    });
}


function conditionQuery() {

    var fd = new FormData();
    fd.append("code", $("#sealCode").val());
    fd.append("name", $("#sealName").val());
    fd.append("unit", $("#unitName").val());

    var sealBodyDiv = $("#sealLBody");
    jQuery.ajax({
        async: false,
        type: "post",
        data: fd,
        cache: false,
        processData: false,
        contentType: false,
        url: "/seal/querySealList",
        success: function (data) {
            if (null == data) console.warn("空");
            else if (0 == data.code) {
                var divStr= "";
                var list = data.list;
                for (var i = 0; i < list.length; i++) {
                    divStr += "<tr>"
                        + "<td>" + (i + 1) + "</td>"
                        + "<td>" + list[i].code + "</td>"
                        + "<td>" + list[i].name + "</td>"
                        + "<td>" + list[i].type + "</td>"
                        + "<td>" + list[i].unit + "</td>"
                        + "<td>" + list[i].lp + "</td>"
                        + "<td>" + list[i].st + "</td>"
                        + "<td>" + list[i].pic + "</td>"
                        + "<td>" + " [详细信息][下载 ?] " + "</td>"
                        + "</td>";
                }
                sealBodyDiv.html(divStr);

            }

        },
        error: function () {
            alert("fail");
        }


    });

}


/* 清除模态框缓存 */
$('body').on('hidden.bs.modal', '.modal', function () {
    $(this).removeData('bs.modal');
});



/* 缓存的请求参数 同一时间只缓存一个 用于翻页 */
var cacheUrl;

function uptCacheUrl(url){
    cacheUrl = url;
}

function chgPage(pageNo, pageSize) {
    jQuery.ajax({
        async: false,
        url: cacheUrl+"&pageNo="+pageNo+"&pageSize="+pageSize,
        cache: false,
        success: function (data) {
            $("#page-inner").html(data);
        },
        error: function () {
            alert("fail");
        }
    });
}



