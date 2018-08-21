function select(){
    $(document).click(function(){
        $(".select_module_con ul").slideUp();
    });
    var module=$(".select_result");
    module.click(function(e){
        e.stopPropagation();
        let ul=$(this).next();
        ul.stop().slideToggle();
        ul.children().click(function(e){
            e.stopPropagation();
            $(this).parent().prev().children("span").text($(this).text());
            ul.stop().slideUp();
        })
    })
}
$(function(){
    select();
});


