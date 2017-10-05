
jQuery(document).ready(function () {
    console.log( "ready!" );
    setSelectValue(document.getElementById('localeSelect'),navigator.language.replace("-","_"));
    setSelectValue(document.getElementById('timeZone'),
                                Intl.DateTimeFormat().resolvedOptions().timeZone);
});

function setSelectValue(selectElement, value){     
    if(selectElement.selectedIndex !== 0){
        return;
    }
    var options = selectElement.options;
    for (var i = 0, optionsLength = options.length; i < optionsLength; i++) {    
        if (options[i].value === value) {            
            selectElement.selectedIndex = i;
            break;
        }
    }
}

