<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../../common/top.jsp">
	<jsp:param value="4" name="name"/>
</jsp:include>	
	<div id="wrap" >
		<div class="outer">	
				
	<div id="titlebar">
		<div class="heading">
			<i class="icon-building"></i> IRL下载列表
		</div>
	</div>
<div class="main" style="margin-left: 0px;" >

<div class="row-table">
    <div class="main" >
     <fieldset style="margin-bottom: 0px;" >
        <legend><h3>吊销列表全量</h3></legend>
        <div class="main main-side" id="mainInfomation">
   		<div class="main" style="margin-left: 0px;">
		<table id="allData" class="table table-condensed table-hover table-striped tablesorter table-fixed">
			<thead>
				<tr class="colhead" >
					<th class="w-id"><div class="headerSortUp">
							序号
						</div></th>
					<th><div class="header">
							发布日期
						</div>
					</th>
					<th class="header">操作</th> 
				</tr>
			</thead>
			<tbody id="allList">
				<tr class="slectable-item active" ><td colspan="3">没有查询到数据</td></tr>
			</tbody>
			</table>
		</div>
   </div>
     </fieldset>
    
      <fieldset style="margin-bottom: 0px;" >
        <legend><h3>吊销列表增量</h3></legend>
         <div class="main main-side" id="mainInfomation">
   		<div class="main" style="margin-left: 0px;">
		<table id="formContent" class="table table-condensed table-hover table-striped tablesorter table-fixed">
			<thead>
				<tr class="colhead" >
					<th class="w-id"><div class="headerSortUp">
							序号
						</div></th>
					<th><div class="header">
							发布日期
						</div>
					</th>
					<th class="header">操作</th> 
				</tr>
			</thead>
			<tbody id="tableData">
				<tr class="slectable-item active" ><td colspan="3">没有查询到数据</td></tr>
			</tbody>
			</table>
		</div>
   </div>
        
      </fieldset>
</div>
  
</div>
</div>
<script type="text/javascript">
var reginput = /[~#^$@%&!*()<>:;{}【】￥@！%……（）~·^\'^\"]/gi;  //罗列版匹配特殊字符

$(document).ready(function(){
	$("#submitbtu").on('click',function(){
		$("#tableData").html("<tr><td colspan='3'>没有查询到数据</tr></td>");
		pgquery(1);
	});
	//增量查询
// 	pgquery(1);
	//
	//全量查询
	$._ajax({
		url : "${pageContext.request.contextPath }/irl/irlList",
		data:{type:1,startTime:"",endTime:""},
		success : function(data) {
			if(data.list&&data.list.length>0){
				var str = "" ;
				var userList=data.list;
				$.each(userList,function(i,value){
					var bg=i%2==0?'#c9cdd2':'#e7eaee';
					str=str+'<tr class="text-center" bgcolor="'+bg+'" >'
					+'	<td  >'+(i+1)+ '</td>'
					+'	<td  >'+value.pubTime+'  </td>'
					+'	<td  ><a class="btn-icon aaaaa" style="cursor: pointer; " href="javascript:;" '
					+'	onclick="downLoad(\''+value.pubTime+'\',\''+value.id+'\',\'1\')" '
					+'	title="下载"><i class="icon-common-edit icon- log-desc">下载</i></a></td>'
					+'	</tr>'
				});
			}else{
				str="<tr><td colspan='3'>没有查询到数据</tr></td>";
			}	
			$("#allList").html(str);
			pgquery(0);
			
		}
	});
});



function resetForm(){
	$("#value4").val("");
}
function pgquery(flag){
	$._ajax({
		url : "${pageContext.request.contextPath }/irl/irlList",
		data:{type:0,startTime:"",endTime:""},
		success : function(data) {
			var str=''
			if (data == null || data == '') {
				new $.zui.Messager('查询结果为空!', {
					icon : 'exclamation-sign',
					type : 'warning',
					close : true
				}).show();
				return;
			}
			new $.zui.Messager('查询成功!', {
				icon : 'ok-sign',
				type : 'success',
				close : true
			}).show();
				if(data.list&&data.list.length>0){
					var userList=data.list;
					$.each(userList,function(i,value){
						var bg=i%2==0?'#c9cdd2':'#e7eaee';
						str=str+'<tr class="text-center" bgcolor="'+bg+'" >'
						+'	<td  >'+(i+1)+'</td>'
						+'	<td  >'+value.pubTime+'  </td>'
						+'	<td  ><a class="btn-icon aaaaa" style="cursor: pointer; " href="javascript:;" '
						+'	onclick="downLoad(\''+value.pubTime+'\',\''+value.id+'\',\'0\')" ' 
						+'	title="下载"><i class="icon-common-edit icon- log-desc">下载</i></a></td>'
						+'	</tr>'
					});
				}else{
					str="<tr><td colspan='3'>没有查询到数据</tr></td>";
				}
				$("#tableData").html(str);
		}
	});
}

function downLoad(publishTime, id, type){
// 	publishTime = publishTime.replace(/ |:/g,"_");
	var data = "type="+type.trim()+"&publishTime="+publishTime.trim()+"&id="+id.trim();
	window.location.href="${pageContext.request.contextPath }/irl/irlDown?"+data;
}
</script>
<script language='javascript'>
$(function()
{
    $.fn.fixedDate = function()
    {
        return $(this).each(function()
        {
            var $this = $(this);
            if($this.offset().top + 200 > $(document.body).height())
            {
                $this.attr('data-picker-position', 'top-right');
            }

            if($this.val() == '0000-00-00')
            {
                $this.focus(function(){if($this.val() == '0000-00-00') $this.val('').datetimepicker('update');}).blur(function(){if($this.val() == '') $this.val('0000-00-00')});
            }
        });
    };

    var options = 
    {
        language: 'zh-cn',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        showMeridian: 1,
        format: 'yyyy-mm-dd hh:ii',
        startDate: '1970-1-1'
    }

    $('.form-datetime').fixedDate().datetimepicker(options);
    $('.form-date').fixedDate().datetimepicker($.extend(options, {minView: 2, format: 'yyyy-mm-dd'}));
    $('.form-time').fixedDate().datetimepicker($.extend(options, {startView: 1, minView: 0, maxView: 1, format: 'hh:ii'}));

    $('.datepicker-wrapper').click(function()
    {
        $(this).find('.form-date, .form-datetime, .form-time').datetimepicker('show').focus();
    });

    window.datepickerOptions = options;
});
</script>
			
	</div>
	<div id="divider">
	
	</div>
</div>	


