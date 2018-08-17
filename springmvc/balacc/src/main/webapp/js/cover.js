$.extend({   
   cover:{   
       show:function(){  
           $.cover.hide();   
           var str="<div id='cover' style='position:absolute;background-color: #FFFFFF;"  
		   str+="width:"+$(document).width()+"px;";   
		   str+="height:"+$(document).height()+"px;"  
		   str+="opacity:0.5;filter:alpha(opacity=0);"  
		   str+="top:0;left:0;z-index=10'></div>";   
       	$(document.body).append(str);
       	$('#cover').bgiframe();
	   },   
	   hide:function(){   
	       $("#cover").remove();   
	   }   
	}   
   }) 