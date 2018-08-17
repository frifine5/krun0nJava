var dataViewValue=false;
function requireJs(path){
	if(path&&path.length>0){
		$.each(path,function(i,e){
			if("notify"==e){
			}
			if("dropdown"==e){
				$("body").append('<scr' + 'ipt type="text/javascript" charset="utf-8" src="platform/js/select2/select2.js"></scr' + 'ipt>');
			}
			if("dataView"==e){
				$("body").append('<scr' + 'ipt type="text/javascript" charset="utf-8" src="platform/js/datatable/jquery.dataTables.min.js"></scr' + 'ipt>');
				$("body").append('<scr' + 'ipt type="text/javascript" charset="utf-8" src="platform/js/datatable/dataTables.bootstrap.min.js"></scr' + 'ipt>');
				dataViewValue=true;
			}
			if("boxModel"==e){
				//初始化boxModel方法
				$("body").append('<scr' + 'ipt type="text/javascript" charset="utf-8" src="platform/js/bootbox/bootbox.js"></scr' + 'ipt>');
				bootbox.setDefaults({"locale": "zh_CN"});
				jQuery.boxModel=function(options){
					return bootbox.dialog(options);
				}
			}
			if("validatorTable"==e){
				$("body").append('<scr' + 'ipt type="text/javascript" charset="utf-8" src="platform/js/validator/jquery.validate.min.js"></scr' + 'ipt>');
				//手机号码验证
				  jQuery.validator.addMethod("isPhone", function(value, element) {
				      var length = value.length;
				      return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
				  }, "请正确填写您的手机号码。");
				  //ip地址判断
				  jQuery.validator.addMethod("ip", function(value, element) {    
				      return this.optional(element) || /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(value);    
				    }, "请填写正确的IP地址。");
				  // 电话号码验证
				  jQuery.validator.addMethod("isTel", function(value, element) {
				      var tel = /^(\d{3,4}-)?\d{7,8}$/g; // 区号－3、4位 号码－7、8位
				      return this.optional(element) || (tel.test(value));
				  }, "请正确填写您的电话号码。");
				  // 匹配密码，以字母开头，长度在6-12之间，必须包含数字和特殊字符。
				  jQuery.validator.addMethod("isPwd", function(value, element) {
				      var str = value;
				      if (str.length < 6 || str.length > 18)
				          return false;
				      if (!/^[a-zA-Z]/.test(str))
				          return false;
				      if (!/[0-9]/.test(str))
				          return false;
				      return this.optional(element) || /[^A-Za-z0-9]/.test(str);
				  }, "以字母开头，长度在6-12之间，必须包含数字和特殊字符。");
			}
		});
	}
}
/** 
 * 使用方法: 页面过渡
 * 开启:$.MaskUtil.mask(); 
 * 关闭:$.MaskUtil.unmask(); 
 *  
 * $.MaskUtil.mask('其它提示文字...'); 
 */  
//控制重复显示等待 
$.MaskUtil = (function(){  
    var $mask,$maskMsg;  
    var defMsg = '正在处理，请稍待。。。';  
    function init(){  
//        if(!$mask){  
//            $mask = $('<div class="modal-backdrop fade in"></div>').appendTo("body");  
//        }  
//        if(!$maskMsg){  
//            $maskMsg = $('<div class="bootbox modal fade in"  style="display: block;">'
//        			+'<div class="modal-dialog">'
//        			+'<div class="modal-content" style="border: 1px solid #00c1de;color:#00c1de;font-size:17px;">'
//        			+'	<div class="modal-body" >'
//        			+'		<div class="bootbox-body" ><i class="fa fa-spin fa-spinner"></i> '+defMsg+'</div>'
//        			+'	</div>'
//        			+'</div>'
//        			+'	</div>'
//        			+'</div>')  
//                .appendTo("body");
            
            
//            <div id="loading">
//            <div id="loading-center">
//            <div id="loading-center-absolute">
//            <div class="object"></div>
//            <div class="object"></div>
//            <div class="object"></div>
//            <div class="object"></div>
//            <div class="object"></div>
//            <div class="object"></div>
//            <div class="object"></div>
//            <div class="object"></div>
//            <div class="object"></div>
//            <div class="object"></div>
//            </div>
//            </div>
//             
//            </div>
    	$maskMsg = $('<div id="loading">'
                +'<div id="loading-center">'
                +'<div id="loading-center-absolute">'
                +'<div class="object"></div>'
                +'<div class="object"></div>'
                +'<div class="object"></div>'
                +'<div class="object"></div>'
                +'<div class="object"></div>'
                +'<div class="object"></div>'
                +'<div class="object"></div>'
                +'<div class="object"></div>'
                +'<div class="object"></div>'
                +'<div class="object"></div>'
                +'</div>'
                +'<div  id="example9" ></div>	'	  
                +'<script>'
                +'	  $("#example9").ProgressBarWars({porcentaje:90,color:"#00E5FF",alto:"2px",tiempo:3000});'
                +'</script>'
                +'</div>'
                 
                +'</div>')  
            .appendTo("body");
    	
//        }  
          
//	        $("#messageModel").css({width:"100%",height:$(document).height(),"z-index":"9999"});  
//	        var scrollTop = $(document.body).scrollTop();  
//	        $("#messageModel").css({  
//	            left:( $(document.body).outerWidth(true)) / 2-800  
//	            ,top:( ($(window).height() - 300) / 2 ) + scrollTop ,
//	            "min-width":"120px" ,
//	            "height":"60px",
//	            "line-height":'20px'
//	        });   
                  
    }  
    return {  
        mask:function(msg){
        		if(msg){
        			defMsg=msg;
        		}
        		init();  
//	            $mask.show(); 
	            $maskMsg.show();  
        }  
        ,unmask:function(){
//	            $mask.remove(); 
        	  $("#example9").ProgressBarWars();
	    	  setTimeout(function(){
	    		  $maskMsg.remove();
        	  },300);
        }
    }  
      
}());
//进度条插件
$.fn.extend({ProgressBarWars: function(options) {
	var ProgressBarWars=this;
	var theidProgressBarWars=$(ProgressBarWars).attr("id");
	var styleUnique = Date.now();
    var StringStyle="";
	
	defaults = {
	porcentaje:"100",
	tiempo:1000,
	color:"",
	estilo:"yoda",
	tamanio:"30%",
	alto:"6px"
	}
	var opciones = $.extend({}, defaults, options);
	if(opciones.color!=''){StringStyle="<style>.color"+styleUnique+"{ border-radius: 2px;display: block; width: 0%; box-shadow:0px 0px 10px 1px "+opciones.color+", 0 0 1px "+opciones.color+", 0 0 1px "+opciones.color+", 0 0 1px "+opciones.color+", 0 0 1px "+opciones.color+", 0 0 1px "+opciones.color+", 0 0 1px "+opciones.color+";background-color: #fff;}</style>";opciones.estilo="color"+styleUnique;}
	$(ProgressBarWars).before(StringStyle);
	if($(ProgressBarWars).find(".barControl").length==0){
		$(ProgressBarWars).append('<span class="barControl" style="width:'+opciones.tamanio+';"><div class="barContro_space"><span class="'+opciones.estilo+'" style="height: '+opciones.alto+';"  id="bar'+theidProgressBarWars+'"></span></div></span>');
	}
	if(!options){
		$("#bar"+theidProgressBarWars).stop(true);
		$("#bar"+theidProgressBarWars).css({width: "100%"});
		$("#bar"+$(this).attr("id")).stop(true);
		$("#bar"+$(this).attr("id")).css({width:"100%"});
	}else{
		$("#bar"+theidProgressBarWars).unbind('animate');
		$("#bar"+theidProgressBarWars).animate({width: opciones.porcentaje+"%"},opciones.tiempo);
		this.mover = function(ntamanio) {
			$("#bar"+$(this).attr("id")).unbind('animate');
			$("#bar"+$(this).attr("id")).animate({width:ntamanio+"%"},opciones.tiempo);
		};
	}
	
return this;			 
}
});

function includeScript(url, callback) { 
	var script = document.createElement("script"); 
	var doc = document.getElementsByTagName("script")[0];
	script.type = "text/javascript"; 
	script.src = url; 
	doc.parentNode.insertBefore(script, doc);
	if (script.readyState) { 
		//IE 
		script.onreadystatechange = function () { 
			if (script.readyState == "loaded" || script.readyState == "complete") {
				script.onreadystatechange = null; 
				callback(); 
			}
		};
	} else {
	//标准的DOM浏览器
		script.onload = function () {
			callback(); 
		 }; 
	}
	console.log("created succeed"); 
} 


(function( $ ){
    //通知插件
//	jQuery.notify = function(n,t,i,r,u,f) {
//		toastr.options.positionClass="toast-"+t;
//		toastr.options.extendedTimeOut=0;
//		toastr.options.timeOut=i;
//		toastr.options.closeButton=f;
////		toastr.options.iconClass=u+" toast-"+r;
//		toastr.options.iconClass="toast toast-"+r;
//		toastr.options.onclick=null;
//		toastr.custom(n);
//		return this;
//    }
//	$.extend($.notify,{
//		error:function(message) {
//			return $.notify(message, 'top-center', '5000', 'error', 'fa-bolt', true);
//		},
//		warning:function(message) {
//			return $.notify(message, 'top-center', '5000', 'warning', 'fa-warning', true);
//		},
//		info:function(message) {
//			return $.notify(message, 'top-center', '5000', 'success', 'fa-check', true);
//		}
//	});
	jQuery.message = function (){
		return this;
	}
	$.extend($.message,{
		error:function(message) {
			return new $.zui.Messager(message, {
				icon : 'frown',
				type : 'danger',
				close : true
			}).show();
		},
		warning:function(message) {
			 return new $.zui.Messager(message, {
				icon : 'exclamation-sign',
				type : 'warning',
				close : true
			}).show();
		},
		info:function(message) {
			return new $.zui.Messager(message, {
				icon : 'ok-sign',
				type : 'success',
				close : true
			}).show();
		}
	});	
	
 //下拉框插件		
  $.fn.dropdown = function (options) {
 	 var defaults = {
 			 hasNull:true,//是否有空的一列
			 allowClear: true,
			 minimumResultsForSearch: -1
 	 };
 	var settings = $.extend(defaults, options);
 	var $select = $(this);
 	if(options.width){
 		$select.css("width",options.width);
 	}
 	$select.select2(settings); // initialize Select2 and any events
 	if(settings.dataAjax){
 		var $option = $('<option selected>Loading...</option>');
     	$select.html("").append($option).trigger('change');
     	$._ajax({ 
     	  type: 'POST',
     	  url: settings.dataAjax.url,
     	  data:settings.dataAjax.data,
     	  dataType: 'json'
     	}).then(function (data) {
     		if( data.flag=="SUCCESS"){
     			$option.text("请选择...").val("");
         		if(!options.hasNull)$option.remove();
         		if(data.datas){
         			$.each(data.datas,function(i,edata){
         				var $optionAdd = $('<option ></option>');
         	        	$select.append($optionAdd).trigger('change');
         	        	$optionAdd.text(edata[settings.dataAjax.text]).val(edata[settings.dataAjax.val]); 
                   	  	$select.trigger('change');
         			});
         		}
     		}else{
     			$.message.error(data.datas);
     		}
     		
     	});
 	}
 	
 };
 
 //构建table插件
  $.fn.dataView=function(options){
	  if(options&&options.dataParams){
		  $.extend(options,{
			  "fnServerParams" : function(aoData) {
				  $.each(options.dataParams,function(i,e){
					  aoData.push({"name":i,"value":e}); 
				}); 
			}
		  }); 
	  }
	  if(options&&options.showColumns){
		  var aoColumns=[];
		  $.each(options.showColumns,function(i,e){
			  aoColumns.push({"mData" : e}); 
		  }); 
		  $.extend(options,{
			  "aoColumns" : aoColumns
		  }); 
	  }
	  return $(this).DataTable(options);
  }

  $.fn.validatorTable=function(options){
	  var defalts={
		  	errorElement : 'span',
		    errorClass : 'help-block',
		    //自定义错误消息放到哪里
		    errorPlacement : function(error, element) {
	    		element.next().remove();//删除显示图标
	    		
//	    		element.after('<span class="input-group-btn" style=""><a href="javascript:;" class="btn btn-block" style="background-color: #fff;"><i class="icon icon-remove"></i></a></span>');
	    		
		        element.after('<span class="icon icon-remove form-control-feedback" aria-hidden="true"></span>');
		        element.parent().append(error);//显示错误消息提示
		    },
		    //给未通过验证的元素进行处理
		    highlight : function(element) {
		    	//对隐藏元素不校验
		    	$(element).closest('.form-group').addClass('has-error has-feedback');
		    	$(".passwordclick").attr("disabled",false);  
		    },
		    //验证通过的处理
		    success : function(label) {
		    	//对隐藏元素不校验
		        var el=label.closest('.form-group').find("input");
		      //对隐藏元素不校验
		        for (var x = 0; x < el.length; x++) {
		        	if(options.ignore==":hidden"&&el.eq(x).attr("type")=="hidden"){
			    		
			    	}else{
			    		el.eq(x).next().remove();//与errorPlacement相似
			    		el.eq(x).after('<span class="icon icon-ok form-control-feedback" aria-hidden="true"></span>');
				        label.closest('.form-group').removeClass('has-error').addClass("has-feedback has-success");
				        label.remove();
			    	}
				}
		    },
		}
	  $.extend(defalts,options);
	  return $(this).validate(defalts);
  }
  
})(jQuery); 
//_ajax重写$.ajax
$(document).ready(function(){
	$.extend({
		_ajax:$.ajax
	});
	//最上层显示
	//备份jquery的ajax方法
	var _ajaxOld=$._ajax;
	//重写jquery的ajax方法
	$._ajax=function(opt){
	    //备份opt中error和success方法
	    var fn = {
	        error:function(XMLHttpRequest, textStatus, errorThrown){},
	        success:function(data, textStatus,XHR){}
	    }
	    if(opt.error){
	        fn.error=opt.error;
	    }
	    if(opt.success){
	        fn.success=opt.success;
	    }
	    //扩展增强处理
	    opt.type="post";
	    var _opt = $.extend(opt,{
	        error:function(XMLHttpRequest, textStatus, errorThrown){
	        	if(!opt.closeloading){
	        		$.MaskUtil.unmask(); 
	        	} 
	            //错误方法增强处理
	        	var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头,sessionstatus，
				var status=XMLHttpRequest.status;
	            if(sessionstatus=='timeout'){  
	            	$.message.error('连接超时,请稍后重试!');
	                     //如果超时就处理 ，指定要跳转的页面  
//	            var top = getTopWinow(); //获取当前页面的顶层窗口对象
//	            	top.$.messager.alert("登录超时",'登录超时, 请重新登录.',"info",function(){
//	            		//top.location.href="${ctp}/index"; //跳转到登陆页面
//	            		return;
//	               }); 
	            }else if(status =='200' && sessionstatus== null) {
					fn.error(XMLHttpRequest, textStatus, errorThrown);
				}else {
//	            	 easyUi.$.messager.alert("操作信息",'服务器无响应，请联系管理员!'); 
	            	 $.message.error('连接错误,请稍后重试!');
	 	             fn.error(XMLHttpRequest, textStatus, errorThrown);
		        } 
	           
	        },
	        success:function(data, textStatus,XHR){
	        	if(!opt.closeloading){
	        		$.MaskUtil.unmask(); 
	        	}
	        	var sessionstatus=XHR.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头,sessionstatus， 
	            if(sessionstatus=='timeout'){   
					return;
			     }else{
			    	//成功回调方法增强处理
		            setTimeout(function(){
		            	//处理data
		            	if(data.flag&&"ERROR"==data.flag.toUpperCase()){
		            		if(data.data.message){
				    			   $.message.error(data.data.message);
				    		   }else{
				    			   $.message.error(data.data);
				    		   }
		            		if(opt.ifContinue){
		            			fn.success(data.data, textStatus,XHR);
		            		}else{
		            			return;
		            		}
		            		
		            	}else if(data.flag&&"SUCCESS"==data.flag.toUpperCase()){
		            		fn.success(data.data, textStatus,XHR);
		            	}else{
		            		fn.success(data, textStatus,XHR);
		            	}
		            	
		            },1020);
				 }
	        },
	        beforeSend:function(XHR){
	        	if(!opt.closeloading){
	        		$.MaskUtil.mask();
	        	}
	        },
	        complete:function(XHR, TS){
	        	if(!opt.closeloading){
	        		$.MaskUtil.unmask(); 
	        	}
	            //请求完成后回调函数 (请求成功或失败之后均调用)。
	        	var sessionstatus=XHR.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头,sessionstatus， 
	            if(sessionstatus=='timeout'){   
	                     //如果超时就处理 ，指定要跳转的页面  
	            }else{
//	            	setTimeout(function(){
//		            	easyUi.messager.progress('close');
//		            },1000);
			    } 
	            
	        }
	    });
	   return _ajaxOld(_opt);
	};
	//_ajax重写
	 
	// function reloadTable(){
//	table.ajax.reload();
//}	
//function reflushTable(){
//	table.ajax.reload( null, false );
//}
//"fnServerParams" : function(aoData) {
//	aoData.push({"name":"user_nam","value":"111"});   
//},
	
//初始化table插件
	if(dataViewValue){
		$.extend( $.fn.dataTable.defaults, {
		"sDom": "Tflt<'row DTTTFooter col-sm-12 '<'col-sm-6'i><'col-sm-6'p>>",
		"searching":false,
		"lengthChange":false,
		"deferRender":true,
		"ordering":false,
		"iDisplayLength": 10,
		"paginationType":"bootstrap",//分页样式
		"language": {
		   "sProcessing": "处理中...",
		   "sLengthMenu": "显示 _MENU_ 项结果",
		   "sZeroRecords": "没有匹配结果",
		   "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
		   "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
		   "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
		   "sInfoPostFix": "",
		   "sSearch": "搜索:",
		   "sUrl": "",
		   "sEmptyTable": "表中数据为空",
		   "sLoadingRecords": "载入中...",
		   "sProcessing": "正在加载数据...",  
		   "sInfoThousands": ",",
		   "oPaginate": {
		       "sFirst": "首页",
		       "sPrevious": "上一页",
		       "sNext": "下一页",
		       "sLast": "末页"
		   },
		   "oAria": {
		       "sSortAscending": ": 以升序排列此列",
		       "sSortDescending": ": 以降序排列此列"
		   }
		},"footerCallback": function( tfoot, data, start, end, display ) {
			
		},"bProcessing" : true,
			"bServerSide" : true,
			"bFilter" : false,
			"bSort" : false,
			"fnDrawCallback" : function(table) { 
				if(!$("#retrunPage").html()){
					$("#simpledatatable_paginate").append(
				    		"<div class='pagination'  id='retrunPage' style='float:right;margin: 2px;'>" +
				    		"<div style='float:left;text-align: center;margin: 2px; margin-right: 10px;'>到第 <input type='text' id='changePage'" +
				    		" class='input-text' style='width:50px;height:27px;text-align:center;border: 1px solid #373d41;'> 页 </div>" +
				    		"<a class='pageReturn platformBtn1 mini' href='javascript:void(0);' " +
				    		"id='dataTable-btn' style='text-align:center;float:right;'>确认</a>" +
				    		"</div><div style='clear:both;'></div>");    
				    var $this=this;
				    $('#dataTable-btn').click(function(e) { 
				    	if (!$("#changePage").val()) {
				    		$.message.warning('请输入正确的页码!');
				            return;
				        }
				        var regex = /^\d+$/;
				        if (!regex.test($("#changePage").val())) {
				        	$.message.warning('请输入正确的页码!');
				        	return;
				        }
				        if($("#changePage").val() && $("#changePage").val() > 0) {    
				            var redirectpage = $("#changePage").val() - 1;    
				        } else {    
				            var redirectpage = 0;    
				        }    
				        $this.fnPageChange(redirectpage);    
				    }); 
				}
			       
			},"preDrawCallback": function( settings ) {
				
			},"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
			       "dataType" : "json",
			       "type" : "POST",
			       "url" : sSource,
			       "data" : aoData,
			       "beforeSend":function(){
			    	   	 $.MaskUtil.mask();
				         $("#serarch").attr("disabled",true);
				         $("#serarch").css("background","#ccc");
					},
			       "success" : function(data){
			    	   $.MaskUtil.unmask(); 
			    	   if(data.flag=="ERROR"){
			    		   if(data.data.message){
			    			   $.message.error(data.data.message);
			    		   }else{
			    			   $.message.error(data.data);
			    		   }
			    		   fnCallback({"iTotalRecords":0,"aaData":[],"iTotalDisplayRecords":0,"sEcho":"1"});
			    	   }else if(data.flag=="SUCCESS"){
			    		   fnCallback(data.data);
			    	   }else{
			    		   fnCallback(data); 
			    	   }
						setTimeout(function(){
							$("#serarch").attr("disabled",false);
							$("#serarch").css("background","");
						},500);
			        }
			       ,"error": function(){ 
			    	   $.MaskUtil.unmask(); 
						$("#serarch").attr("disabled",false);
						$("#serarch").css("background",""); 
						$.message.warning("数据查询超时!"); 
					}
			   });
			},
		});
//添加首页和末页
 $.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings ){
     return {
        "iStart":         oSettings._iDisplayStart,
        "iEnd":           oSettings.fnDisplayEnd(),
        "iLength":        oSettings._iDisplayLength,
        "iTotal":         oSettings.fnRecordsTotal(),
        "iFilteredTotal": oSettings.fnRecordsDisplay(),
        "iPage":          oSettings._iDisplayLength === -1 ?
            0 : Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
        "iTotalPages":    oSettings._iDisplayLength === -1 ?
            0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
     };
 }
  
 /* Boostrap style pagination control */
 $.extend( $.fn.dataTableExt.oPagination, {
     "bootstrap": {
        "fnInit": function( oSettings, nPaging, fnDraw ) {
            var oLang = oSettings.oLanguage.oPaginate;
            var fnClickHandler = function ( e ) {
                e.preventDefault();
                if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {
                    fnDraw( oSettings );
                }
            };
            $(nPaging).addClass('pagination').append(
                '<ul class="pagination">' +
                   '<li class="sFirst disabled"><a href="#"> ' + oLang.sFirst + '</a></li>' + //添加首页显示
                   '<li class="prev disabled"><a href="#"> ' + oLang.sPrevious + '</a></li>' + // '<li class="prev disabled"><a href="#">&larr; '+oLang.sPrevious+'</a></li>'+
                   '<li class="next disabled"><a href="#">' + oLang.sNext + '</a></li>' + // '<li class="next disabled"><a href="#">'+oLang.sNext+' &rarr; </a></li>'+
                   '<li class="sLast disabled"><a href="#"> ' + oLang.sLast + '</a></li>' + //添加尾页显示
                '</ul>'
            );
            var els = $('a', nPaging);
            $(els[0]).bind('click.DT', { action: "first" }, fnClickHandler); //绑定首页事件
            $(els[1]).bind('click.DT', { action: "previous" }, fnClickHandler);
            $(els[2]).bind('click.DT', { action: "next" }, fnClickHandler);
            $(els[3]).bind('click.DT', { action: "last" }, fnClickHandler);//绑定尾页事件
        },
  
        "fnUpdate": function ( oSettings, fnDraw ) {
            var iListLength = 5;
            var oPaging = oSettings.oInstance.fnPagingInfo();
            var an = oSettings.aanFeatures.p;
            var i, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);
  
            if ( oPaging.iTotalPages < iListLength) {
                iStart = 1;
                iEnd = oPaging.iTotalPages;
            }
            else if ( oPaging.iPage <= iHalf ) {
                iStart = 1;
                iEnd = iListLength;
            } else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
                iStart = oPaging.iTotalPages - iListLength + 1;
                iEnd = oPaging.iTotalPages;
            } else {
                iStart = oPaging.iPage - iHalf + 1;
                iEnd = iStart + iListLength - 1;
            }
  
            for ( i=0, iLen=an.length ; i<iLen ; i++ ) {
                // Remove the middle elements
                $('li:gt(1)', an[i]).filter(':lt(-2)').remove(); // 此处修改 $('li:gt(0)', an[i]).filter(':not(:last)').remove();
  
                // Add the new list items and their event handlers
                for ( j=iStart ; j<=iEnd ; j++ ) {
                    sClass = (j==oPaging.iPage+1) ? 'class="active"' : '';
                    $('<li '+sClass+'><a href="#">'+j+'</a></li>')
                        .insertBefore($('li:eq(-2)', an[i])[0])//此处修改 .insertBefore($('li:last', an[i])[0])
                        .bind('click', function (e) {
                            e.preventDefault();
                            oSettings._iDisplayStart = (parseInt($('a', this).text(),10)-1) * oPaging.iLength;
                            fnDraw( oSettings );
                        } );
                }
  
                // Add / remove disabled classes from the static elements
                if ( oPaging.iPage === 0 ) {
                    $('li:lt(2)', an[i]).addClass('disabled'); //此处修改 $('li:first', an[i]).addClass('disabled');
                } else {
                    $('li:lt(2)', an[i]).removeClass('disabled'); //此处修改 $('li:first', an[i]).removeClass('disabled');
                }
  
                if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
                    $('li:gt(-3)', an[i]).addClass('disabled'); //此处修改 $('li:last', an[i]).addClass('disabled');
                } else {
                    $('li:gt(-3)', an[i]).removeClass('disabled'); //此处修改 $('li:last', an[i]).removeClass('disabled');
                }
            }
        }
     }
 } );
		
}
//初始化table插件结束	
	

	
});