function addZebra(){
    $("#TableStyle tr").mouseover(function(){  
            $(this).addClass("over");
     }).mouseout(function(){
            $(this).removeClass("over");
     }).each(function(){
     	var thisTr = this;
     	$("td",thisTr).each(function(i,n){
			if(i!=0){
				$(this).click(function(){
					$("input[name='ids']",thisTr).each(function(){
						$(this).click();		
					})
				})
			}     		
     	})
     })
    $("#TableStyle tr:even").addClass("alt");
}