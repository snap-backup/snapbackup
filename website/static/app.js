// Snap Backup

window.app = {
   hamburgerMenu: function() {
      $(window.document).on({ click: $.noop });  //workaround for sticky hover on mobile
      var menuItem = $('main').data().menu;  //use "data-menu" attribute to set current menu item
      $('nav.hamburger-menu li[data-menu=' + menuItem + ']').addClass('current');
      },
   setup: function() {
      var macVersion = library.browser.isMac() && library.browser.getUrlParams().view != 'all';
      $('.for-macs-only').toggle(macVersion);
      $('.for-non-macs').toggle(!macVersion);
      }
   };

$(app.setup);
