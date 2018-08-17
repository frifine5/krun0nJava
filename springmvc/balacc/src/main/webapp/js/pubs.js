   function loadQuery(divname,url){
	 //disableButtons();
	 //createDivOverLay(url);
	  $.ajax({
		   type: "POST",
		   url: url,
		   data: null,
		   success: function(data){
		     $("#"+divname).html(data);
	   	     addOther();
		   },
		   error:function(data){
		     $("#"+divname).html(data.responseText);
	   	     addOther();
		   }
		});	
   }
   
   
   function addOther(){
	   //	delDivOverLay();
		addZebra();
   }

   
   function loadQueryCallback(divname,url,mycallback){
	   disableButtons(); 
	   createDivOverLay(url); 	  
	   $.ajax({
		   type: "POST",
		   url: url,
		   data: null,
		   success: function(data){
		     $("#"+divname).html(data);
	   	    delDivOverLay();
		   	addZebra();
			mycallback();
		   },
		   error:function(data){
		     $("#"+divname).html(data.responseText);
	   	    delDivOverLay();
		   	addZebra();
			mycallback();
		   }
		}); 
   }
