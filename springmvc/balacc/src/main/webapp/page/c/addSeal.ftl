<div id="addSeal">


    <div class="col-lg-10 addSeal" id="firPage">
        <div class="panel panel-default">
            <div class="panel-heading">
                <b>用章单位信息</b>
                <a class="pull-right" onclick="swapAnchor('#secPage')">下一步</a>
            </div>
            <div class="panel-body">

                <div class="col-lg-5">
                    <div class="col-lg-8 form-group has-success">
                        <label class="control-label font-sl" for="entname">*单位名称</label>
                        <input class="form-control" type="text" placeholder="请输入单位名称" id="entname">
                    </div>
                </div>

                <div class="col-lg-5">
                    <div class="col-lg-8 form-group has-success">
                        <label class="control-label font-sl" for="uniscid">*统一社会信用代码</label>
                        <input class="form-control" type="text" placeholder="请输入统一社会信用代码" id="uniscid">
                    </div>
                </div>

                <div class="col-lg-5">
                    <div class="col-lg-8 form-group has-success">
                        <label class="control-label font-sl" for="lgname">*法定代表人</label>
                        <input class="form-control" type="text" placeholder="请输入法定代表人" id="lgname">
                    </div>
                </div>

                <div class="col-lg-5">
                    <div class="col-lg-8 form-group has-success">
                        <label class="control-label font-sl" for="opscope">*经营范围</label>
                        <input class="form-control" type="text" placeholder="请输入经营范围" id="opscope">
                    </div>
                </div>

                <div class="col-lg-5">
                    <div class="col-lg-8 form-group has-success">
                        <label class="control-label font-sl" for="address">*单位/公司详细地址</label>
                        <input class="form-control" type="text" placeholder="请输入详细地址" id="address">
                    </div>
                </div>

                <div class="col-lg-5">
                    <div class="col-lg-12>">
                        <div class="col-lg-7 form-group has-success" style="display: -webkit-inline-box">
                            <label class="control-label font-sl" for="estdate">*成立日期&nbsp;:</label>
                            <div class="input-append date form_datetime">
                                <input size="16" type="text" id="estdate" value="" readonly>
                                <span class="add-on"><i class="fa fa-calendar"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-12>">
                        <div class="col-lg-7 form-group has-success" style="display: -webkit-inline-box">
                            <label class="control-label font-sl" for="apprdate">*核准日期&nbsp;:</label>
                            <div class="input-append date form_datetime">
                                <input size="16" type="text" id="apprdate" value="" readonly>
                                <span class="add-on"><i class="fa fa-calendar"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-12>">
                        <div class="col-lg-7 form-group has-success" style="display: -webkit-inline-box">
                            <label class="control-label font-sl" for="opfrom">经营期限自:</label>
                            <div class="input-append date form_datetime">
                                <input size="16" type="text" id="opfrom" value="" readonly>
                                <span class="add-on"><i class="fa fa-calendar"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-12>">
                        <div class="col-lg-7 form-group has-success" style="display: -webkit-inline-box">
                            <label class="control-label font-sl" for="opto">经营期限至:</label>
                            <div class="input-append date form_datetime">
                                <input size="16" type="text" id="opto" value="" readonly>
                                <span class="add-on"><i class="fa fa-calendar"></i></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-5">
                    <div class="col-lg-8 form-group has-success">
                        <label class="control-label font-sl" for="entel">单位电话</label>
                        <input class="form-control" type="text" placeholder="请输入单位电话号码" id="entel">
                    </div>
                </div>


                <div class="col-lg-12">
                    <div class="col-lg-5">
                        <div class="col-lg-4 form-group has-success">
                            <button class="form-control" value="重置"
                                    onclick="clearPageWhich(1);event.returnValue=false;">重置
                            </button>
                        </div>
                        <div class="col-lg-4 form-group has-success">
                            <button class="form-control" title="依单位名称和统一信用代码从法人库中查询获取企业信息"
                                    onclick="getEntrOnline()">查询获取
                            </button>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <b> ** 材料 **</b>
            </div>
            <div class="panel-body">
                <div class="col-lg-8">
                    <div class="col-lg-3 form-group has-success">
                        <label class="control-label font-sl" for="ccc">材料说明</label>
                    </div>
                    <div class="col-lg-8 form-group has-success">
                        <input class="form-control" type="text" id="ccc">
                    </div>
                </div>
                <div class="col-lg-8">
                    <div class="col-lg-3 form-group has-success">
                        <label class="control-label font-sl" for="uppic1">选择上传图片</label>
                    </div>
                    <div class="col-lg-5 input-group has-success">
                        <input class="form-control" type="file" id="uppic1">
                    </div>
                </div>
            </div>
            <div class="panel-footer">
                <a>&nbsp;&nbsp;</a>
                <a class="pull-right" onclick="swapAnchor('#secPage')">下一步</a>
            </div>

        </div>

    </div>


<#-- ######################################################################## -->
    <script type="text/javascript">
        <!--
        var count = 1;

        function add() {
            var tbl = document.all.ci;
            var rows = tbl.rows.length;
            if(rows > 10){
                alert('超过10，不可增加');
                return;
            }
            var tr = tbl.insertRow(rows);

            var name = tr.insertCell(0);
            name.innerHTML = '<input type="text">';
            var tel = tr.insertCell(1);
            tel.innerHTML = '<input type="text">';
            var rdo = tr.insertCell(2);
            rdo.innerHTML = '<input type="text" >';
            var chk = tr.insertCell(3);
            chk.innerHTML = '<input type="text">';
            var del = tr.insertCell(4);
            del.innerHTML = '<input type="button" onclick="del(this)" value="删除">';
            count++;
        }

        function del(btn) {
            var tr = btn.parentElement.parentElement;
            var tbl = tr.parentElement;
            tbl.deleteRow(tr.rowIndex);
        }

        //-->
    </script>

    <div class="col-lg-10 addSeal" id="secPage">
        <div class="panel panel-default">
            <div class="panel-heading">
                <b>印章列表</b>
                <a class="pull-right" onclick="swapAnchor('#thiPage')"> 下一步</a>
            </div>
            <div class="panel-body">
                <div class="col-lg-12">
                    <button class="col-lg-2 form-control"  title="添加印章的申请记录" onclick="add()">新增</button>
                </div>
                <div>
                    <table class="table" style="width:100%" id="ci" name="ci">
                        <caption>customer information</caption>
                        <thead>
                        <tr>
                            <th>图章名称（上标）</th>
                            <th>图章编号（下标）</th>
                            <th>图章类型（中下）</th>
                            <th>图章申请使用年期</th>
                            <th> - 操作 -</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>

                </div>

            </div>
            <div class="panel-footer">
                <a  onclick="swapAnchor('#firPage')"> 上一步</a><span></span>
                <a class="pull-right" onclick="swapAnchor('#thiPage')"> 下一步</a>
            </div>
        </div>

    </div>

    <div class="col-lg-10 addSeal" id="thiPage">
        <div class="panel panel-default">
            <div class="panel-heading">
                <b>第三页</b>
                <a class="pull-right" href="#firPage"> 返回第一页</a><span></span>
            </div>

            <div class="panel-body">


            </div>

            <div class="panel-footer">
                <a  href="#secPage"> 上一步</a><span> | </span>
                <a  href="#firPage"> 返回第一页</a><span></span>
                <a class="pull-right" onclick="#"> 提交</a>
            </div>
        </div>
    </div>

</div>
    <style>
        .addSeal {
            width: 100%;
            height: 110%;
        }


    </style>



<script>
    /* 日期组件*/
    $(".form_datetime").datetimepicker({
        format: 'yyyy-mm-dd',
        language: 'zh-CN',  //日期
        minView: 'month',
        minuteStep: 5,
        autoclose: true,
        clearBtn: true,     //清除按钮
        todayBtn: true      //今日按钮
    });


</script>


