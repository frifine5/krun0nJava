<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../../common/top.jsp">
	<jsp:param value="3" name="name"/>
</jsp:include>	
	
	<style>
	table{table-layout: fixed;}
	td{word-break: break-all;word-wrap:break-word; width:60%;}
	
	</style>
	<div id="wrap" >
		<div class="outer">	
				
	<div id="titlebar">
		<div class="heading">
			<i class="icon-building"></i> 公开参数信息
		</div>
	</div>
<div class="main" style="margin-left: 0px;" >
<div class="row-table">
  <div class="">
    <div class="main">
      <fieldset>
        <legend>查询条件</legend>
        <div class="article-content">
        <div id="querybox" class="show" style="margin-top: 0px;
border-top-width: 0px;">

<div  style="margin-bottom: 0px;" target="hiddenwin" id="searchform" class="form-condensed">
	<table class="table table-condensed table-form " id="user-search" style=" margin: 0 auto">
		<tbody>
			<tr>
				<td class="w-60px">
					<table class="table active-disabled" style="border:0px;">
						<tbody>
							<tr id="searchbox4" class="">
								<td class="w-110px"><select name="field4" id="field4" class="form-control">
										<option value="realname">标识</option>
								</select></td>
								<td class="w-110px"><select name="operator4" id="operator4" class="form-control">
										<option value="include" selected="selected">公开参数唯一标识</option>
								</select></td>
								<td id="valueBox4"><input type="text" name="value4" id="value4" value="" class="form-control  searchInput">
								</td>
							</tr>
						</tbody>
					</table>
				</td>
				<td class="w-60px"><input type="hidden" name="module" id="module" value="user"> 
					<div class="btn-group">
						<button type="button" id="submitbtu" class="btn btn-primary" data-loading="稍候...">搜索</button>
						<button type="button" class="btn btn-default" onclick="resetForm()">重置</button>
						</div></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
        
        </div>
      </fieldset>
<style>
#actionbox a{font-weight:normal}
.col-side fieldset#actionbox{padding-right:5px;}
.col-side #actionbox #historyItem li span.item{white-space:nowrap}
.changes blockquote{font-family: monospace, serif;}
</style>
</div>
  </div>
  <div class="">
    <div class="main main-side" id="mainInfomation">
    	
    </div>
  </div>
</div>
</div>
<script type="text/javascript">
var reginput = /[~#^$@%&!*()<>:;{}【】￥@！%……（）~·^\'^\"]/gi;  //罗列版匹配特殊字符
$("#submitbtu").on('click',function(){
	$("#mainInfomation").html("没有查询到信息");
	if(!$("#value4").val()){
		new $.zui.Messager('查询条件不能为空!', {
			icon : 'exclamation-sign',
			type : 'warning',
			close : true
		}).show();
		return;
	}
	var textf = $("#value4").val().trim() ;
	if(reginput.test(textf)){
		new $.zui.Messager('不可输入非法字符!', {
			icon : 'exclamation-sign',
			type : 'warning',
			close : true
		}).show();
		$("#value4").val("");
		return ;
	}
	$._ajax({
		url : "${pageContext.request.contextPath }/pps/publicParaReq",
		data:{pubId:$("#value4").val()},
		success : function(data) {
			var infomation="";
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
			infomation+='<fieldset style="width: 50%;">'
				+'	<legend>公开参数信息</legend>';
			if(data&&data.length>0){
				$.each(data,function(i,datavalue){
					infomation+='	<table class="table table-data table-condensed table-borderless">'
						+'			<tbody>'
						+'				<tr>'
						+'					<th  class="strong">版本 version:</th>'
						+'					<td>'+datavalue.version+'</strong></td>'
						+'				</tr>'
						+'				<tr>'
						+'					<th class="strong" style="width: 50%;">地区 districtName:</th>'
						+'					<td class="product-normal"><strong>'+datavalue.districtName+'</strong></td>'
						+'				</tr>'
						+'				<tr>'
						+'					<th  class="strong">地区序列 districtSerial:</th>'
						+'					<td>'+datavalue.districtSerial+'</td>'
						+'				</tr>'
						+'				<tr>'
						+'					<th class="strong" >有效期 validity:</th>'
						+'					<td class="product-normal"><strong>'+datavalue.valid+'</strong></td>'
						+'				</tr>'
						+'				<tr>'
						+'					<th  class="strong">IBC算法  ibcAlgorithm:</th>'
						+'					<td>'+datavalue.oid+'</td>'
						+'				</tr>'

                        +'				<tr>'
                        +'					<th  class="strong">曲线标识 cid:</th>'
                        +'					<td>'+datavalue.cid+'</td>'
                        +'				</tr>'
                        +'				<tr>'
                        +'					<th  class="strong">双线性对类型运算标识 eid:</th>'
                        +'					<td>'+datavalue.eid+'</td>'
                        +'				</tr>'
                        +'				<tr>'
                        +'					<th  class="strong">模长 N:</th>'
                        +'					<td>'+datavalue.n+'</td>'
                        +'				</tr>'
                        +'				<tr>'
                        +'					<th  class="strong">扩域次数 K:</th>'
                        +'					<td>'+datavalue.k+'</td>'
                        +'				</tr>'
                        +'				<tr>'
                        +'					<th  class="strong">签名算法标识 hid_s:</th>'
                        +'					<td>'+datavalue.hidS+'</td>'
                        +'				</tr>'
                        +'				<tr>'
                        +'					<th  class="strong">非签名算法标识 hid_e:</th>'
                        +'					<td>'+datavalue.hidE+'</td>'
                        +'				</tr>'
                        +'				<tr>'
                        +'					<th  class="strong">G1域生成元 p1:</th>'
                        +'					<td>'+datavalue.p1+'</td>'
                        +'				</tr>'
                        +' 				<tr>'
                        +'					<th  class="strong">G2域生成元 p2:</th>'
                        +'					<td>'+datavalue.p2+'</td>'
                        +'				</tr>'
                        +'				<tr>'
                        +'					<th  class="strong">私钥生成中心标识 pkgID:</th>'
                        +'					<td>'+datavalue.pkgID+'</td>'
                        +'				</tr>'
						+'				<tr>'
						+'					<th  class="strong">签名主公钥 signMastPublicKey:</th>'
						+'					<td>'+datavalue.signMPK+'</td>'
						+'				</tr>'
						+'				<tr>'
						+'					<th  class="strong">加密主公钥 encMastPublicKey:</th>'
						+'					<td >'+datavalue.encMPK+'</td>'
						+'				</tr>'
						+'				<tr>'
						+'					<th  class="strong">标识类型项 ibcIdentityType:</th>'
						+'					<td>'+datavalue.districtOid+'</td>'
						+'				</tr>'
						// 这个是显示整的公开参数主公钥的ASN结构
					/* 	+'				<tr>'
						+'					<th  class="strong">公开参数数据  publicParameterData:</th>'
						+'					<td>'+datavalue.octParam+'</td>'
						+'				</tr>' */
						+'			</tbody>'
						+'		</table>';
				});	
			}else{
				infomation="没有查询到信息";
			}
			infomation+='	</fieldset>';
			$("#mainInfomation").html(infomation);
		}
	});
})
function resetForm(){
	$("#value4").val("");
}
</script>

			
		</div>
		<div id="divider"></div>
	</div>	
	
	

