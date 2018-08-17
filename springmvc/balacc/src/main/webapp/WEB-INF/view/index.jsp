<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="common/top.jsp">
	<jsp:param value="1" name="name"/>
</jsp:include>
<style>
	td{
		word-break: break-all; 
		word-wrap:break-word;
	}

</style>
	<div id="wrap" >
		<div class="outer">
		<div id="titlebar">
			<div class="heading">
				<i class="icon-building"></i> 标识信息
			</div>
		</div>
		 <div class="main">
	      <fieldset style="margin-bottom: 0px;">
	        <legend>查询条件</legend>
				<div  style="margin-bottom: 0px;" id="searchform" class="form-condensed">
					<table class="table table-condensed table-form " id="user-search" style="margin: 0 auto" >
						<tbody>
							<tr>
								<td class="w-60px" >
									<table class="table active-disabled" style="border:0px;">
										<tbody>
											<tr id="searchbox4" class="">
												<td class="w-120px"><select name="field4" id="field4"
													class="form-control">
														<option value="realname">密钥标识</option>
												</select></td>
												<td class="w-150px"><select name="operator4"
													id="operator4" class="form-control">
														<option value="0" selected="selected">当前标识信息</option>
														<option value="1" >最新标识信息</option>
														<option value="2" >所有历史标识信息</option>
												</select></td>
												<td id="valueBox4"><input type="text" name="value4"
													id="value4" value="" class="form-control  ">
												</td>
											</tr>
										</tbody>
									</table>
								</td>
								<td class="w-100px"><input type="hidden" name="module"
									id="module" value="user"> 
									<div class="btn-group">
										<button type="button" id="submit" class="btn btn-primary"
											data-loading="稍候...">搜索</button>
										<button type="button" class="btn btn-default"
											onclick="resetForm()">重置</button>
										</div></td>
								</tr>
							</tbody>
						</table>
					</div>
		</fieldset>
	</div>
	<div class="main" style="margin-left: 0px;">
		<table id="formContent"
			class="table table-condensed table-hover table-striped tablesorter table-fixed">
			<thead>
				<tr class="colhead" >
					<th class="w-id"><div class="headerSortUp">
							序号
						</div></th>
					<th><div class="header">
							密钥标识
						</div></th>
					<th><div class="header">
							密钥状态
						</div></th>
					<th style="width: 400px;"><div class="header " >
							有效期限
						</div></th>
					<th><div class="header">
							发布时间
						</div></th>
					
					<th class="header">旧标识</th> 
				</tr>
			</thead>
			<tbody id="tableData">
				<tr class="slectable-item active" data-id="1506651366867001"><td colspan="6">没有查询到数据</td></tr>
			</tbody>
			
			</table>
		</div>
		</div>
		<div id="divider"></div>
</div>	
<script type="text/javascript">
function pgquery(flag){
	// 首先过滤特殊字符 
	var textf = $("#value4").val().trim() ;
	if(!textf){
		new $.zui.Messager('不能输入为空!', {
			icon : 'exclamation-sign',
			type : 'warning',
			close : true
		}).show();
		return ;
	}
	//  /^\w+$/			//	/^[0-9a-zA-Z :-\u4E00-\u9FA5()（）]+$/
	if(!/^\w+$/.test(textf)){
		new $.zui.Messager('不可输入非法字符!', {
			icon : 'exclamation-sign',
			type : 'warning',
			close : true
		}).show();
		$("#value4").val("");
		return ;
	}
	
	ajaxData({"cpg":flag,"userid":$("#value4").val().trim(),"type":$("#operator4").val().trim()},function(data){
		var str=''
		if(data.list&&data.list.length>0){
			var userList=data.list;
			$.each(userList,function(i,value){
				var bg=i%2==0?'#c9cdd2':'#e7eaee';
				str=str+'<tr class="text-center" bgcolor="'+bg+'" >'
				+'	<td  >'+(i+1)+'</td>'
				+'	<td  >'+value.userid+' </td>'
				+'	<td  >'+(value.status=="1"?"有效":value.status=="2"?"注销":value.status=="3"?"冻结":value.status)+'  </td>'
				+'	<td  >'+value.validity+'   </td>'
				+'	<td  >'+value.sfTime+'  </td>' // value.sfTime or value.pubTime
				+'  <td  > '+(value.oldUserid?value.oldUserid:'')+'  </td>'
				+'	</tr>'
			});
		}else{
			str="<tr><td colspan='6'>没有查询到数据</tr></td>";
		}
		
		$("#tableData").html(str);
	});
}
function ajaxData(data,callback){
	var url= "${pageContext.request.contextPath }/pps/ibcUserInfoQueryList";
	$._ajax({
		url: url,
		data: {"cpg":data.cpg,"userid":data.userid,"type":data.type},
		success: function(data){
			if(data == null || data == ''){
				new $.zui.Messager('查询结果为空!',{
					icon:'exclamation-sign',
					type:'warning',
					close:true
				}).show();
				return;
			}
			new $.zui.Messager('查询成功!',{
				icon:'ok-sign',
				type:'success',
				close:true
			}).show();
			callback(data);
		}
	});
}

function resetForm(){
	$("#value4").val("");
}
$(document).ready(function(){
	$("#submit").on("click",function(){
		pgquery(1);
		$("#tableData").html("<tr><td colspan='6'>没有查询到数据</tr></td>");
	});
	
});


</script>	
	
</body>
</html>
