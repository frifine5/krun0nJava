<html>
<#include "common.ftl" >
<head>
    <title>
        首页
    </title>
</head>


<body>

<div id="wrapper">
    <nav class="navbar navbar-default top-navbar" role="navigation">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">xx系统</a>
        </div>
        <ul class="nav navbar-top-links navbar-right">

            <!-- 用户图标 -->

            <li class="dropdown">
                 <#if user??>
                     <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                         <i class="fa fa-user fa-fw"></i>
                         <span>欢迎你 ${user}</span>
                         <i class="fa fa-caret-down"></i>
                     </a>
                     <ul class="dropdown-menu dropdown-user">
                         <li><a href="#"><i class="fa fa-user fa-fw"></i>账户信息</a>
                         </li>
                         <li class="divider"></li>
                         <li><a href="#"><i class="fa fa-sign-out fa-fw"></i> 退出系统</a>
                         </li>
                     </ul>
                 <#else >
                     <a onclick="doLogin();event.returnValue=false;" href="javascript:void(0)">
                        <i class="fa fa-user fa-fw"></i>
                        <span>登录</span>
                    </a>

                 </#if>
            </li>

        </ul>

    </nav>
    <!--/. NAV TOP  -->
    <nav class="navbar-default navbar-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav" id="main-menu">

                <li>
                    <a class="active-menu" href="#"><i class="fa fa-dashboard"></i>首页</a>
                </li>

                <li>
                    <a onclick="changeInner('/page/c/main.ftl');event.returnValue=false;" href="javascript:void(0)">
                        <i class="fa fa-desktop"></i> 菜单一</a>
                </li>
                <li>
                    <a onclick="changeInner(<@s.url'/pages/log/main.html'/>)" href="javascript:void(0)"><i
                            class="fa fa-bar-chart-o"></i> 菜单二</a>
                </li>


            </ul>

        </div>

    </nav>
    <!-- /. NAV SIDE  -->
    <div id="page-wrapper">
        <div id="page-inner">

            <div class="row">
                <div class="col-md-12">
                    <h2 class="page-header">
                        用水说明
                        <small>Summary of how to use this machine.</small>
                    </h2>
                </div>
            </div>


            <!-- /. ROW  -->
            <div class="row col-xs-12">
                <div>&nbsp;</div>
                <div class="col-md-8 col-sm-12 col-xs-12">
                    <div class="panel panel-primary text-center no-boder bg-color-green">
                        <div class="panel-body">
                            <img id="middleImage" src="/images/priceList.png"/>
                        </div>
                        <div id="middleTitle" class="panel-footer back-footer-green">
                            水费计价规则
                        </div>
                    </div>
                </div>

                <div class="col-md-4 col-sm-12 col-xs-12">
                    <div><br/>
                        <button class="col-xs-9 btn btn-info" onclick="mainDo(1)">显示水质</button>
                        <br/><br/>
                        <button class="col-xs-9 btn btn-info" onclick="mainDo(2)">查询余额</button>
                        <br/><br/>
                        <button class="col-xs-9 btn btn-info" onclick="mainDo(3)">自助服务</button>
                        <br/><br/>
                        <button class="col-xs-9 btn btn-warning" onclick="mainDo(4)">挂失</button>
                        <br/><br/>
                    </div>

                </div>
            </div>


        </div>
        <!-- /. PAGE INNER  -->
    </div>
    <!-- /. PAGE WRAPPER  -->
</div>


<script type="text/javascript">

    /**
     * 跳转右侧页面
     */
    function changeInner(url) {
        $("#page-inner").load(url, function () {
            $("#page-inner").fadeIn(100);
        });
    }
    function doLogin() {
        alert('go to login');
    }
</script>

</body>
