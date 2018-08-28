<#include "common.ftl" >
<html>
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
                         <li><a href="#"><i class="glyphicon glyphicon-log-out fa-fw"></i> 退出系统</a>
                         </li>
                     </ul>
                 <#else >
                     <a data-toggle="modal" data-target="#loginModal">
                        <i class="glyphicon glyphicon-log-in fa-fw"></i>
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
                    <a class="active-menu" onclick="changeInner('/page/home') " href="javascript:void(0)" ><i class="fa fa-dashboard"></i>首页</a>
                </li>

                <li>
                    <a onclick="changeInner('/page/main');conditionQuery() " href="javascript:void(0)" >
                        <i class="fa fa-desktop"></i> 印章管理</a>
                </li>

                <li>
                    <a onclick="changeInner('/test')" href="javascript:void(0)" >
                        <i class="fa fa-desktop"></i> 测试跳页</a>
                </li>

                <li>
                    <a onclick="changeInner('/seal'); doQuery()" href="javascript:void(0)" >
                        <i class="fa fa-desktop"></i> ftl的印章页</a>
                </li>
                <li>
                    <a onclick="changeInner('/page/district'); getDist(1)" href="javascript:void(0)" >
                        <i class="fa fa-desktop"></i> 统一区划代码</a>
                </li>

            </ul>

        </div>

    </nav>

    <!-- /. NAV SIDE  -->
    <div id="page-wrapper">
        <div id="page-inner">

        </div>
        <!-- /. PAGE INNER  -->
    </div>

    <!-- Modal -->
    <div class="modal fade" id="loginModal" tabindex="-1" role="document" aria-labelledby="loginModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title text-center" id="loginModalLabel">登录</h4>
                </div>
                <div class="modal-body">
                    <div class="panel-body">
                        <div class="col-lg-9">
                            <div class="col-lg-3 form-group has-success ">
                                <label class="control-label large" for="account">账号</label>
                            </div>
                            <div class="col-lg-8 form-group ">
                                <input type="text" class="form-control" id="account">
                            </div>
                            <div class="col-lg-3 form-group  has-success ">
                                <label class="control-label large" for="pwd">密码</label>
                            </div>
                            <div class="col-lg-8 form-group ">
                                <input type="password" class="form-control" id="pwd">
                            </div>
                            <div class="col-lg-3  form-group  has-success ">
                                <label class="control-label large" for="vCode">验证码</label>
                            </div>
                            <div class="col-lg-8 form-group ">
                                <input type="text" class="form-control" id="vCode">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-primary" value="登录" id="loginBtn"
                           onclick="doLogin();event.returnValue=false;">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>

    <!-- /. PAGE WRAPPER  -->
</div>


<script type="text/javascript">
    window.onload  = function(){
        changeInner('/page/home');
    }

</script>

</body>
