//this syntax successfully reattaches the click event
//after the pagination container node gets rewritten by ajax
$(document).on("click", "[id|='nav'] a", function (e) {

    var page = $(this).attr("data-page");
    //ckeditor doesnt render via ajax
    if (page == 'entry') {
        return;
    }
    try {
        e.preventDefault();
        e.stopImmediatePropagation();
    } catch (err) {
        //console(err.toString());
    }
    //console.log($(this));
    var disabledValue = $(this).attr("data-disabled");
    //console.log(disabledValue);
    if (disabledValue === "true" || disabledValue === "disabled") {
        return false;
    }
    var page = $(this).attr("data-page");
    var myHref = $(this).attr("href");
    var label = $(this).text();
    label = $.trim(label);
    
    $(this).parent('li').siblings().removeClass("active");
    $(this).parent('li').addClass('active');
    //alert(myHref);
    var ajaxHref = myHref.substr(0, myHref.lastIndexOf("."));
    try {
        $.ajax({
            type: 'GET',
            url: ajaxHref + "Content.xhtml",
            success: function (response) {
                try {
                    //alert(response.toString());
                    $('#content').empty();
                    $('#content').html(response.toString());
                } catch (err) {                    
                    //console.log(err.toString());
                }
            },
            error: function () {
                //alert(e.toString());
            }
        });
        $.ajax({
            type: 'GET',
            url: ajaxHref + "SideNav.xhtml",
            success: function (response) {
                try {
                    //alert(response.toString());
                    $('#sideNav').empty();
                    $('#sideNav').html(response.toString());
                } catch (err) {
                    //console.log(err.toString());
                }
            },
            error: function () {
                //alert(e.toString());
            }
        });
        console.log(label);
        window.history.pushState({url:myHref}, label, myHref);
        if (document.title != label) {
            document.title = label;
        }
    } catch (err) {
        window.location.href = myHref;
    }
});


