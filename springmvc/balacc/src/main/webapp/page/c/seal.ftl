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
                <table>
                    <tr>
                        <td>
                            <div class="form-group has-success">
                                <label class="control-label font-md" for="sealCode">印章编码</label>
                            </div>
                        </td>
                        <td>
                            <div class="form-group has-success">
                                <input type="text" class="form-control" id="sealCode">
                            </div>
                        </td>
                        <td>
                            <div class="form-group has-success">
                                <label class="control-label font-md" for="sealName">印章名称</label>
                            </div>
                        </td>
                        <td>
                            <div class="form-group has-success">
                                <input type="text" class="form-control" id="sealName">
                            </div>
                        </td>
                        <td>
                            <div class="form-group has-success">
                                <label class="control-label font-md" for="unitName">单位名称</label>
                            </div>
                        </td>
                        <td>
                            <div class="form-group has-success">
                                <input type="text" class="form-control" id="unitName">
                            </div>
                        </td>

                    </tr>

                    <!-- 功能-->
                    <tr>
                        <td>
                            <div class="form-group has-success">
                                <input type="button" class="form-control" value="查询"
                                       onclick="doQuery();event.returnValue=false;">
                            </div>
                        </td>
                    </tr>
                </table>
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
                    <table class="table table-striped table-bordered table-hover" id="sealTbl">
                        <thead>
                        <tr>
                            <th width="2%">序号</th>
                            <th width="5%">印章编号</th>
                            <th width="5%">印章名称</th>
                            <th width="5%">印章类型</th>
                            <th width="5%">单位名称</th>
                            <th width="5%">法定代表人</th>
                            <th width="6%">印章启用时间</th>
                            <th width="5%">印章图片</th>
                            <th width="8%">操作</th>
                        </tr>
                        </thead>
                        <#if list??>
                        <tbody>
                        <#assign index = 0>
                        <#list list as seal>
                        <tr>
                            <#assign index = index+1>
                            <td>${index}</td>
                            <td>${seal.code}</td>
                            <td>${seal.name}</td>
                            <td>${seal.type}</td>
                            <td>${seal.unit}</td>
                            <td>${seal.lp}</td>
                            <td>${seal.st?string('yyyy-MM-dd hh:mm:ss')}</td>
                            <td>${seal.pic}</td>
                            <td>[详细信息][下载 ?]</td>
                        </tr>
                        </#list>
                        </tbody>

                        <#else >
                        </#if>
                    </table>
                    <div>
                        <#include "../pagediv.ftl">

                    </div>
                </div>

            </div>
        </div>
    </div>
</div>



</body>
</html>