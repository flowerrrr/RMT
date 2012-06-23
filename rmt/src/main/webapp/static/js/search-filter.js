/* ====================================================================
 * Script to filter dom.elements based in wildcard expressions.
 * - add input field with id="filter"
 * - mark list-items are table-rows with class="filterable"
 * - mark content to be matched with class="filtered"
 *
 * e.g.
 * <table>
 *     <tr class="filtered">
 *         <td class="filtered">First name / last name</td>
 *         <td>[Some other text that should not be matched]</td>
 *     </tr>
 * </table>
 * ==================================================================== */

jQuery(document).ready(function($) {
    $.extend($.expr[":"], {
        "matches": function(elem, i, match, array) {
            var query = (match[3] || "");
            query = query.replace('*', '.*');  // support simple '*' wildcard.
            var regexp = new RegExp(query, 'i');
            var text = ($(elem).find('.filtered').text() || "");
            var hit = regexp.test(text);
            // console.log("hit=" + hit);
            return hit;
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