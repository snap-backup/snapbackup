// Snap Backup

const app = {
   hamburgerMenu() {
      const menuItem = $('main').data().menu;  //use "data-menu" attribute to set current menu item
      $('nav.hamburger-menu li[data-menu=' + menuItem + ']').addClass('current');
      },
   setup() {
      $('<div>').addClass('wedge').prependTo($('main'));
      const macVersion = library.browser.macOS() && dna.browser.getUrlParams().view !== 'all';
      $('.for-macs-only').toggle(macVersion);
      $('.for-non-macs').toggle(!macVersion);
      },
   };

$(app.setup);
