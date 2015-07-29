// Snap Backup

var app = {};

app.start = {
   go: function() {
      var macVersion = library.browser.isMac() &&
         library.browser.getUrlParams().view != 'all';
      $('.for-macs-only').toggle(macVersion)
      $('.for-non-macs').toggle(!macVersion)
      $('.perfect').attr('action', '/feedback/feedback.php');
      }
   };

$(app.start.go);
