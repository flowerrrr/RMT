/* Use fade in / out to show / hide veil. */
var RMT = {
    fadeInMs: 500,
    fadeOutMs: 300,
};

if (typeof Wicket != 'undefined'){
    Wicket.show = function(e) {
        var e = Wicket.$(e);
        if (e != null) {
            // e.style.display = "";
            $(e).fadeIn(RMT.fadeInMs);
        }
    };

    Wicket.hide = function(e) {
        var e = Wicket.$(e);
        if (e != null) {
            // e.style.display = "none";
            $(e).fadeOut(RMT.fadeOutMs);
        }
    };
}


