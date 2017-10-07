//this syntax successfully reattaches the click event
//after the pagination container node gets rewritten by ajax
$(document).on("click", "[id|='nav'] a", function (e) {
    
    var dataPage = $(this).attr("data-page");
    //ckeditor doesnt render via ajax
    if (dataPage === 'entry') {
        return;
    }
    try {
        e.preventDefault();
        e.stopImmediatePropagation();
    } catch (err) {
        //do nothing
    }
    
    var disabledValue = $(this).attr("data-disabled");
    
    if (disabledValue === "true" || disabledValue === "disabled") {
        return false;
    }
    
    var myHref = $(this).attr("href");
    var label = $(this).text();
    label = $.trim(label);
    
    $(this).parent('li').siblings().removeClass("active");
    $(this).parent('li').addClass('active');
    
    var ajaxHref = myHref.substr(0, myHref.lastIndexOf("."));
    try {
        $.ajax({
            type: 'GET',
            url: ajaxHref + "Content.xhtml",
            async: true,
            success: function (response) {
                try {
                    var loader = $('#loader').html();
                    
                    $('#content').html(loader);
                    $('#content').html(response.toString());
                    
                    
                } catch (err) {                    
                    //do nothing
                }
            },
            error: function () {
                //do nothing
            }
        });
        $.ajax({
            type: 'GET',
            url: ajaxHref + "SideNav.xhtml",
            async: true,
            success: function (response) {
                try {
                    
                    $('#sideNav').empty();
                    $('#sideNav').html(response.toString());
                } catch (err) {
                    //do nothing
                }
            },
            error: function () {
                //do nothing
            }
        });
        
        window.history.pushState({url:myHref}, label, myHref);
        if (document.title !== label) {
            document.title = label;
        }
    } catch (err) {
        window.location.href = myHref;
    }
});


