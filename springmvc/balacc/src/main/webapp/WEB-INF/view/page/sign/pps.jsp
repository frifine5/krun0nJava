<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../../common/top.jsp">
	<jsp:param value="2" name="name"/>
</jsp:include>	
	
	<div id="wrap" >
		<div class="outer">	
				<div id="titlebar">
	<div class="heading">
		<i class="icon-building"></i> pps服务信息
	</div>
</div>
<div class="main" style="margin-left: 0px;"  id="mainInfomation">
</div>

<script type="text/javascript">
	$(document).ready(function(){
		$("#mainInfomation").html("没有查询到信息");
		$._ajax({
			url : "${pageContext.request.contextPath }/pps/getPPSInfo",
			success : function(data) {
				var infomation="";
				if (data == null || data == '') {
					new $.zui.Messager('查询结果为空!', {
						icon : 'exclamation-sign',
						type : 'warning',
						close : true
					}).show();
					$("#mainInfomation").html("没有查询到信息");
					return;
				}
				new $.zui.Messager('查询成功!', {
					icon : 'ok-sign',
					type : 'success',
					close : true
				}).show();
				if(data&&data.length>0){
					$.each(data,function(i,datavalue){
						infomation+='<div class="col-side">'
							+'<div class="main main-side">'
							+'<fieldset>'
							+'	<legend>基本信息</legend>'
							+'	<table class="table table-data table-condensed table-borderless"'
							+'		style="width:600">'
							+'			<tbody>'
							+'				<tr>'
							+'					<th class="strong w-200px"'
							+'	style="width:220">KGS标识:</th>'
							+'					<td style="width:360" >'
							+'	<strong>'+datavalue.kgsid+'</strong></td>'
							+'				</tr>'
							+'				<tr>'
							+'					<th>PPS标识:</th>'
							+'					<td>'+datavalue.ppsId+'</td>'
							+'				</tr>'
							+'				<tr>'
							+'					<th>注册时间:</th>'
							+'					<td>'+datavalue.ppsPublishTime+'</td>'
							+'				</tr>'
							+'				<tr>'
							+'				<th>有效期:</th>'
							+'					<td>'+datavalue.ppsvalTime+'</td>'
							+'				</tr>'
							+'				<tr>'
							+'					<th>pps状态</th>'
							+'					<td>'+(datavalue.ppsState=="1"?"有效":"注销")+'</td>'
							+'					<td></td>'
							+'				</tr>'
							+'			</tbody>'
							+'		</table>'
							+'	</fieldset>'
							+'</div>'
							+'</div>';
					});
					
				}else{
					infomation="没有查询到信息";
				}
				$("#mainInfomation").html(infomation);
			}
		});
	})
</script>

			
		</div>
		<div id="divider"></div>
	</div>	
	
