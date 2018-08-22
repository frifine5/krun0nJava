<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>seal_apply_main</title>


</head>
<body>


<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="col-lg-9">
                    <h4>查询条件</h4>
                    <form role="form" action="javascript: conditionQuery()">
                        <div class="col-lg-3 form-group has-success">
                            <label class="control-label" for="inputSuccess">关键字</label>
                            <input type="text" class="form-control" id="inputSuccess">
                        </div>
                        <div class="col-lg-3 form-group has-warning">
                            <label class="control-label" for="inputWarning">模糊条件</label>
                            <input type="text" class="form-control" id="inputWarning">
                        </div>
                        <div class="col-lg-6  form-group has-error">
                            <label class="control-label" for="inputError">时间</label>
                            <input type="text" class="form-control" id="inputError">
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
                            <th>Rendering engine</th>
                            <th>Browser</th>
                            <th>Platform(s)</th>
                            <th>Engine version</th>
                            <th>CSS grade</th>
                        </tr>
                        </thead>
                        <tbody>


                        <tr class="gradeA">
                            <td>Gecko</td>
                            <td>Firefox 1.0</td>
                            <td>Win 98+ / OSX.2+</td>
                            <td class="center">1.7</td>
                            <td class="center">A</td>
                        </tr>
                        <tr class="gradeA">
                            <td>Gecko</td>
                            <td>Firefox 1.5</td>
                            <td>Win 98+ / OSX.2+</td>
                            <td class="center">1.8</td>
                            <td class="center">A</td>
                        </tr>

                        <tr class="gradeC">
                            <td>Tasman</td>
                            <td>Internet Explorer 5.1</td>
                            <td>Mac OS 7.6-9</td>
                            <td class="center">1</td>
                            <td class="center">C</td>
                        </tr>


                        <tr class="gradeU">
                            <td>Other browsers</td>
                            <td>All others</td>
                            <td>-</td>
                            <td class="center">-</td>
                            <td class="center">U</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
        <!--End Advanced Tables -->
    </div>
</div>


<script >

    function conditionQuery(){
        var data = "";
        $.ajax({
            type: "post",
            data: data,
            dataType: "json",
            url: "/query/getApplyList",
            timeout: 10,
            success: function (data) {
                var m = "";
                if(data.code == 0 && data.data.size >0 ){
                    for (var i = 0; i <data.data.size; i++) {

                    }
                }
            },
            error:function () {
                alert("fail");
            }


        });

    }


</script>



</body>
</html>