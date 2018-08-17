function createDivOverLay(url){
    var title="操作正在进行中，请耐心等待.....";
    var waitingDiv = document.createElement("DIV");
    waitingDiv.id="kanwei_2009_9_1_waiting";
	waitingDiv.style.position ="absolute";
	var cwidth=document.body.clientWidth;
	var cheght=document.body.clientHeight;
	
	waitingDiv.style.top = cheght/2-100; 
	waitingDiv.style.left =  cwidth/2-125; 
	waitingDiv.style.zIndex=9999;
	var path=url.substring(1);
	var mypath ="/"+path.substring(0,path.indexOf('/'))+'/images/a2_72.jpg';
	var str = '<table width="272" border="0" cellspacing="0" cellpadding="0" background="'+mypath+'">'+
	  '<tr>'+
	    '<td width="272" height="27" valign="top"><table width="200" border="0" align="center" cellpadding="0" cellspacing="0">'+
	      '<tr>'+
	        '<td height="8"></td>'+
	      '</tr>'+
	      '<tr>'+
	        '<td align="center" class="ddtext">'+title+'</td>'+
	      '</tr>'+
	    '</table></td>'+
	  '</tr>'+
	'</table>';
	$(waitingDiv).append(str);
	$('#kanwei_2009_9_1_waiting').bgiframe();
	document.body.appendChild(waitingDiv);
	$.cover.show();
}




function createDivOverLayWithNothing(title){
    if(title==null) title="操作正在进行中，请耐心等待.....";
    var waitingDiv = document.createElement("DIV");
    waitingDiv.id="kanwei_2009_9_1_waiting";
	waitingDiv.style.position ="absolute";
	var cwidth=document.body.clientWidth;
	var cheght=document.body.clientHeight;
	
	waitingDiv.style.top = cheght/2-100; 
	waitingDiv.style.left =  cwidth/2-125; 
	waitingDiv.style.zIndex=9999;
	 
	var str = '';
	
	$(waitingDiv).append(str);
	$(waitingDiv).bgiframe();
	document.body.appendChild(waitingDiv);
	$.cover.show();
}



function delDivOverLay(){
	$("#kanwei_2009_9_1_waiting").remove();
	$.cover.hide();
}


function createCommonSearchIcion(title){
    var orgnames="";
    orgnames+="<font color='red' >";
    orgnames+=title;
    orgnames+="</font>";
	return orgnames;

}


function before_query(){
	
	 isShowNoDataImg=true;
     deFaultPage();
     query();
     
}

function deFaultPage(){
	  $('#cpage').val(1);  
}

//修改函数之前判断选择一个，并且返回id
function befeoreUpdate(name){
     var selec="";
     var num=0;
   	 $("input[name='"+name+"']").each(function(i){
   	    if(this.checked==true) {
   	    	num++;
   	       selec=this.value;
   	    }
   	 });
    if(num>=2){
      //showMesg("msgid",'消息提示','请选择一个要操作的数据！',230,100);
      alert("请选择一个要操作的数据！");
      return ;
    }else if(num==0){
      alert("请选择一个要操作的数据！");
      //showMesg("msgid",'消息提示','请选择一个要操作的数据！',230,100);
      return ;
    }else{
      return selec;
    }
}

/**
 * 隐藏左边的树
 */
function hideleft(){
     $('#unitTreePanel').width(0);
     $("#treep").hide();
     $("#treep").attr("ishide","yes");
     //$("#change").attr("src","<para:base/>/gui/images/right.png");
	
	$('#change').hide();
	$('#change').attr("ishide","yes");
}

/**
 * 显示左边的树
 */
function showleft(){	
     $("#treep").show();
     $("#treep").attr("ishide","no");
     $('#unitTreePanel').width($('#treep').width());
     //$("#change").attr("src","<para:base/>/gui/images/left.png");;
	
	$('#change').show();
	$('#change').attr("ishide","no");
}

function getParentTableW(){
    var width=$("#leftTable950").width();
    return width;
}

function getParentTableH(){
    var height=$("#leftTable950").height();
     return height;
}



function popWindow(myurl,mytitle,mywidth,myheight,reloadOnClose) {
	pop = new Popup({ contentType:1,scrollType:'yes',isReloadOnClose:reloadOnClose,width:mywidth,height:myheight});
	pop.setContent("contentUrl",myurl);
	pop.setContent("title",mytitle);
	pop.build();
	pop.show();
	isOpenOrClose=false;
}

/**
 * 弹出窗口 myurl为访问页面，mytitle标题，mywidth myheight为弹出窗口的宽和高
 */
function popWindow(myurl,mytitle,mywidth,myheight,reloadOnClose) {
	pop = new Popup({ contentType:1,scrollType:'no',isReloadOnClose:reloadOnClose,width:mywidth,height:myheight});
	pop.setContent("contentUrl",myurl);
	pop.setContent("title",mytitle);
	pop.build();
	pop.show();
	isOpenOrClose=false;
}

/*
对一个字符串增加一个id

*/
function addStringId(allPara,id){
     
     var isChecked=allPara.indexOf(id);
	   if(isChecked != -1) return allPara;
	   
     var oldString = allPara.split(",");
     var newString = new Array();
     for(var i=0;i<oldString.length;i++){  
        if(oldString[i]!=""){
             newString.push(oldString[i]);//滤掉多余逗号
        }
     }
     newString.push(id);
     return newString.join(",");
     
}

/*
传入一个字符串和一个id，删除字符串中的id
*/
function subStringSplit(allPara,id){
    
	alert("allPara:" +allPara);
	alert("id:" +id);
     var isChecked=allPara.indexOf(id);
    
	   if(isChecked == -1) return allPara;
	 //alert(isChecked);  
     var oldString = allPara.split(",");
     var newString = new Array();
     for(var i=0;i<oldString.length;i++){
        if(oldString[i]!=id&&oldString[i]!=""){
            
             newString.push(oldString[i]);
        }
     }
     
     //alert(newString.join(","));
     return newString.join(",");
     
}

function orgNameAddDel(allParam,data){
    var name=data.title;  
	  if(data.checked==false){//del   
		    allParam=subStringSplit(allParam,name) ;
	  }else{//add
			 allParam =  addStringId(allParam,name)
	  }
	 
	  return allParam;
}

/**
 * divId:显示选择节点的divid
 * data:选中的数据
 * type:删除时使用，见delLabel方法
 * treeDivId;显示树的divid
 * myUsedTreeObj:使用的树的ID
 */
function refreshDiv(divId,data,type,treeDivId,myUsedTreeObj){
        var temp=data;
		var showname = '';
		if(myUsedTreeObj != null){
			cur_used_tree_obj = myUsedTreeObj; 
       		var temp_tree_selected_id = $(data).attr("divid");
       		var temp_tree_selected_obj = myUsedTreeObj.getChildById(temp_tree_selected_id);
       		showname = getCnPath(temp_tree_selected_obj,0);
       	}
       	if(showname == null || showname == '' || showname == 'undefined'){
       		showname = data.title;	
       	}
      if(showname == null || showname == '' || showname == 'undefined'){
      
      return;
      }
	    var div=$("#"+divId);
	    if(temp.checked==true){
	      var Str="<label name='label'  title='点击可删除'  onMouseOver='setBackGround(this,1)' onMouseOut='setBackGround(this,2)' id='"+temp.id+"' style='cursor:pointer;'><input type='checkbox'  num='"+type+"' treeDivId='"+treeDivId+"'treeDivIdnum='"+type+"'divID='"+divId+"' org_id='"+temp.id+"' onClick='delLabel(this)' name='org_name'  checked=true />"+showname+"&nbsp;&nbsp;&nbsp;<br></label>";
	       div.append(Str);
	    }else{
	       $("#"+divId).each(function(index){
	          $("#"+temp.id,this).remove();
	       });
	    }
}

/**
 * 得到selected_obj及其父路径
 */
function getCnPath(selected_obj,num){
	num++;
	if(selected_obj == null){
		return '';	
	}else{
		var text = selected_obj.text.substring(0,selected_obj.text.indexOf("#"));
		if(num==1){
			text = '<font color=red>'+ text + '</font>';
		}
		if(selected_obj.parentNode != cur_used_tree_obj.id){
			var ptext = getCnPath(selected_obj.parentNode,num);
			if(ptext!=null&&ptext!=''&&ptext!='undefined'){
				return  ptext + '->' + text;
			}
			return  text;
		}else{
			if(text!=null&&text!=''&&text!='undefined'){
				return text;
			}else{
				return '';
			}
		}
	}
}

function orgIdAddDel(allParam,data){
    
	  var id=data.id; 
	  if(id.length>=33){
	      id=id.substring(0,id.length-1);
	  }
	  if(data.checked==false){//del   
		    allParam=subStringSplit(allParam,id) ;
	  }else{//add
			allParam =  addStringId(allParam,id)
	  }
	 
	  return allParam;

}

function refreshDivById(divId,id){
    $("#"+divId).each(function(index){
         if(id!=""){
          $("#"+id,this).remove();
         }
         
});  
} 

function refreshDivWhenUpdate(divId,id,name,type,treeDivId,tail){
    var div=$("#"+divId);
    var num=0;
    $("#"+id,div.get().reverse()[0]).each(function(index){
      num++;
    });
    if(num>0) return ;
    var Str="<label name='label'   title='点击可删除' onMouseOver='setBackGround(this,1)' onMouseOut='setBackGround(this,2)'  id='"+id+tail+"'><input type='checkbox' tail='"+tail+"' num='"+type+"' treeDivId='"+treeDivId+"'treeDivIdnum='"+type+"'divID='"+divId+"' org_id='"+id+"' onClick='delLabel(this)' name='org_name' checked=true />"+name+"<br></label>";
    div.append(Str);
    alert(div);
} 

function delLabel(obj){
	var tail=$(obj).attr("tail");
	var divID=$(obj).attr("divID");
	var org_id=$(obj).attr("org_id");
	var treeDivId=$(obj).attr("treeDivId");
	var org_id=$(obj).attr("org_id");
	
    if(tail==null) tail="";
    $("#"+divID).each(function(index){
	          $("#"+org_id+tail,this).remove();//删除div中显示的
	          $("#"+treeDivId).each(function(index){
	          
	               $("#"+org_id+tail,this).each(function(index){
                           this.checked=false;//删除树中选中的 
                           $("input[groupPath*='"+$(this).attr("groupPath")+"']").each(function(index){//可用子节点
                              this.disabled=false;
                           });
	               }); 
	                
	           });
	          //删除隐藏域中用于选中的记录.针对于checkbox
	          $("input[name='check_checkedStr']").each(function(index){	  
	                this.value=subStringSplit(this.value,org_id);
			  });  
	       });
	 
	if($(obj).attr("num")=="1"){
	      orgChooseList2 =  subStringSplit(orgChooseList2,org_id);
	}
	 
	if($(obj).attr("num")=="2"){
		  menuChooseList2=subStringSplit(menuChooseList2,org_id);
	}
	if($(obj).attr("num")=="3"){
		removeId(org_id);
	}
	//删除隐藏域disabled记录
	$("#isSubCheckDisabled").each(function(index){
	    this.value=subStringSplit(this.value,org_id);
	});
	
}


