
function hideAllPageButtons(){
 	 $('#all_param_pageButtons_table').hide();
}
function showllPageButtons(){
 	 $('#all_param_pageButtons_table').show();
}

function backReset(listSize,totalPage,currentPage,cpage,nextpage,previouspage,totalnum,mytype){
		//显示图片处理
		var flag=(listSize);
		if((flag==""||flag==0)){
			if(mytype==null||mytype==''){
			  $("#TableStyle").after(
					  '<table  id="nodata" width="100%" style="font-size: 12px; border:1px solid b5d6e6; color:red;" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF"><tr>'
			          		+'<td align="center">查询完毕,没有可显示的内容</td></tr></table>');
			}
			$("#nodata").show();
		}else{
			$("#nodata").hide();
		}
		if(flag==""||flag==0||totalnum==""||totalnum==0){
		  //隐藏翻页按钮
		   hideAllPageButtons();
		} else{
			//显示翻页按钮
			showllPageButtons();
			//显示设置
			$('#totalpage').text(totalPage);  
			$('#currentpage').text(currentPage);  
			if(totalnum==null) totalnum="0";
			$('#totalnum').text(totalnum);
			$("#pagesel").val(cpage);
			//隐藏域设置
			$('#cpage').val(cpage);  
			$('#nextpage').val(nextpage);  
			$('#previouspage').val(previouspage);
			$('#mytotalPage').val(totalPage);
			
			handlButtonByCurrentPage(currentPage,totalPage)
		}
}

function handlButtonByCurrentPage(currentPage,totalPage){
	var b1=$('#bt_firstPage');
	var b2=$('#bt_beforePage');
	var b3=$('#bt_nextPage');
	var b4=$('#bt_lastPage');
	enableButtons();
	
	if(totalPage==0||(totalPage==1&&currentPage==1)){
	    b1.attr("disabled",true); 
   		b2.attr("disabled",true);
   		b3.attr("disabled",true); 
   		b4.attr("disabled",true);
	}else  if(currentPage==1){
    	//首页
   		b1.attr("disabled",true); 
   		b2.attr("disabled",true);
   }else if(currentPage==totalPage){
        b3.attr("disabled",true); 
   		b4.attr("disabled",true);
   }
}

var buttons=new Array("bt_firstPage","bt_beforePage","bt_nextPage","bt_lastPage");
function enableButtons() {
	var len=buttons.length;
	for(i=0;i<len;i++){
		var button_name=buttons[i];
		var button=document.getElementById(button_name);
		if(button!=null){
			button.disabled=false;
		}
	}
}
function disableButtons() {
	var len=buttons.length;
	for(i=0;i<len;i++){
		var button_name=buttons[i];
		var button=document.getElementById(button_name);
		if(button!=null){
			button.disabled=true;
		}
	}
}



function loadPageQuery(pageflag){
		if(pageflag==1){
			$('#cpage').val("1");  
		}else
	if(pageflag==2){
			$('#cpage').val($('#previouspage').val());  
		}else
	if(pageflag==3){
			$('#cpage').val($('#nextpage').val());  
		}else
	if(pageflag==4){
			$('#cpage').val($('#mytotalPage').val());  
		}
	pageQuery();
}  

function pageQuery(){
	  query();
}  

function goToMyPage(){
	var userValue=($("#pagesel").val());
    if(userValue!=""&&userValue!=null){
         kanweigoPage(userValue);
    }
      
}
function kanweigoPage(num){
        var patrn=/^[0-9]*[1-9][0-9]*$/;
  	    if(!patrn.test(num) || parseInt(num)==0){
            alert("跳转页必须为正整数！");
               		return false;
        }else{   
            if(parseInt(num)<1){
               alert("跳转页不能小于1！");
               return false;
            } 
            if(parseInt(num)>$('#mytotalPage').val()){
               alert("跳转页不能大于"+$('#mytotalPage').val()+"！");
               return false;
            } 
            $('#cpage').val(num);  
            pageQuery();      
  	    }
}

