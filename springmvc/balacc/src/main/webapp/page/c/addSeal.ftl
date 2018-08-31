<div id="addSeal">


    <div class="col-lg-10 addSeal" id="firPage">
        <div class="panel panel-default">
            <div class="panel-heading">
                用章单位信息
            </div>
            <div class="panel-body">

                <div class="col-lg-4">
                    <div class="col-lg-3 form-group has-success">
                        <label class="control-label font-sl" for="entname">*单位名称</label>
                    </div>
                    <div class="col-lg-8 form-group has-success">
                        <input class="form-control" type="text" placeholder="请输入单位名称" id="entname">
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="col-lg-3 form-group has-success">
                        <label class="control-label font-sl" for="uniscid">*统一社会信用代码</label>
                    </div>
                    <div class="col-lg-8 form-group has-success">
                        <input class="form-control" type="text" placeholder="请输入统一社会信用代码" id="uniscid">
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="col-lg-3 form-group has-success">
                        <label class="control-label font-sl" for="lgname">*法定代表人</label>
                    </div>
                    <div class="col-lg-8 form-group has-success">
                        <input class="form-control" type="text" placeholder="请输入法定代表人" id="lgname">
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="col-lg-3 form-group has-success">
                        <label class="control-label font-sl" for="opscope">*经营范围</label>
                    </div>

                    <div class="col-lg-8 form-group has-success">
                        <input class="form-control" type="text" placeholder="请输入经营范围" id="opscope">
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="col-lg-3 form-group has-success">
                        <label class="control-label font-sl" for="address">*单位/公司详细地址</label>
                    </div>

                    <div class="col-lg-8 form-group has-success">
                        <input class="form-control" type="text" placeholder="请输入详细地址" id="address">
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="col-lg-3 form-group has-success">
                        <label class="control-label font-sl" for="estdate">*成立日期</label>
                    </div>
                    <div class="col-lg-7 input-group has-success" style="display: -webkit-inline-box">
                        <div class="input-append date form_datetime">
                            <input size="16" type="text" value="" readonly>
                            <span class="add-on"><i class="fa fa-calendar"></i></span>
                        </div>

                    </div>
                </div>


            <#--
                    /**
                    * entname	主体名称
                    * uniscid	统一社会信用代码
                    * regno	注册号
                    * enttypeCn	企业类型(中文)
                    * regcap	注册资本
                    * regcapcurCn	注册资本币种
                    * estdate	成立日期
                    * apprdate	核准日期
                    * regstateCn	登记状态(中文)
                    * regorgCn	登记机关(中文)
                    * opfrom	经营期限自
                    * opto	经营期限至
                    * dom	住所
                    * name	法定代表人
                    * opscope	经营范围
                    */


              -->

            </div>

            <div class="panel-footer">
                <a href="#secPage"> 下一步</a><span> | </span>
                <a onclick="swapAnchor('#secPage')">func 下一步</a>
            </div>

        </div>

    </div>

    <div class="col-lg-10 addSeal" id="secPage">
        <div class="panel panel-default">
            <div class="panel-heading">第二页</div>
            <div class="panel-footer">
                <a onclick="swapAnchor('#firPage')"> func 上一步</a><span> | </span>
                <a href="#firPage"> 上一步</a>
                <a href="#thiPage"> 下一步</a>
            </div>
        </div>

    </div>

    <div class="col-lg-10 addSeal" id="thiPage">
        <div class="panel panel-default">
            <div class="panel-heading">第三页</div>
            <div class="panel-footer">
                <a href="#secPage"> 上一步</a>
                <a href="#firPage"> 跳第一页</a>
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


        $(".form_datetime").datetimepicker({
            format: 'yyyy-mm-dd',
            language: 'zh-CN',  //日期
            minView: 'month',
            minuteStep: 5,
            autoclose: true, //今日按钮
            clearBtn: true, //清除按钮
            todayBtn: true
        });

    </script>


