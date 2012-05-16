jQuery(document).ready(function($) {
    $.extend($.expr[":"], {
        "matches": function(elem, i, match, array) {
            var query = (match[3] || "").toLowerCase();
            var text = $(elem).find('.filtered').text();
            var hit = (text || "").toLowerCase().indexOf(query);
            // console.log("hit=" + hit);
            return hit >= 0;
        }
    });
    
    bind();
    bindClearFilter();

    function bind() {
        $("#filter").keyup(function () {
            var filter = $(this).val();
            filterList(filter);
        });
    }

    function bindClearFilter() {
         $("#clearButton").click(function () {
             $("#filter").val('');
             filterList('');
         });
    }


    function filterList(filter) {
        $(".filterable:matches('" + filter + "')").each(function () {
            $(this).show();
        });
        $(".filterable").not(":matches('" + filter + "')").each(function () {
            $(this).hide();
        });
        
        if(filter.length == 0) {
            $("#clearButton").css("display", "none");
        } else {
            $("#clearButton").css("display", "block");
        }
    }

});