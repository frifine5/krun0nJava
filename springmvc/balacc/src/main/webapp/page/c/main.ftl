<#include "../common.ftl" >
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>main page</title>

</head>
<body>

<div class="row ">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="col-lg-9">
                    <form role="form" action="javascript: conditionQuery()">
                        <div class="col-lg-3 form-group has-success">
                            <label class="control-label" for="sealCode">印章编码</label>
                            <input type="text" class="form-control" id="sealCode">
                        </div>
                        <div class="col-lg-3 form-group has-warning">
                            <label class="control-label" for="sealName">印章名称</label>
                            <input type="text" class="form-control" id="sealName">
                        </div>
                        <div class="col-lg-3  form-group has-error">
                            <label class="control-label" for="unitName">单位名称</label>
                            <input type="text" class="form-control" id="unitName">
                        </div>
                        <div class="col-lg-2 form-group pull-right">
                            <input type="submit" class="form-control" id="submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-12">
        <!-- Advanced Tables -->
        <div class="panel panel-default">
            <div class="panel-heading">
                列表
            </div>
            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>印章编码</th>
                            <th>印章名称</th>
                            <th>印章类型</th>
                            <th>单位名称</th>
                            <th>法定代表人</th>
                            <th>印章启用时间</th>
                            <th>印章图片</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <#if sealList??>
                            <tbody>
                            <#list sealList as seal>
                                <td>[]</td>
                                <td>${seal.code}</td>
                                <td>${seal.name}</td>
                                <td>${seal.type}</td>
                                <td>${seal.unit}</td>
                                <td>${seal.lp}</td>
                                <td>${seal.optr}</td>
                                <td>${seal.st}</td>
                                <td>${seal.pic}</td>
                                <td>[详细信息][下载?]</td>
                            </#list>
                            </tbody>
                        <#else >
                            <p>无结果</p>
                        </#if>

                    </table>
                </div>

            </div>
        </div>
        <!--End Advanced Tables -->
    </div>
</div>


<script>

    function conditionQuery() {

        var data = {
            "code": $("#sealCode"),
            "name": $("#sealName"),
            "unit": $("#unitName")
        };
        jQuery.ajax({
            type: "post",
            data: data,
            dataType: "json",
            url: "/seal/querySealList",
            timeout: 10,
            success: function (data) {
                alert(data);
            },
            error: function () {
                alert("fail");
            }


        });

    }


</script>


</body>
</html>