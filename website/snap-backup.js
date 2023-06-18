// Snap Backup

const app = {
   setup() {
      const wedge = globalThis.document.createElement('div');
      wedge.classList.add('wedge');
      globalThis.document.querySelector('main').prepend(wedge);
      const macosMode = libX.browser.macOS() && dna.browser.getUrlParams().view !== 'all';
      dna.dom.toggleClass(globalThis.document.body, 'macos-mode', macosMode);
      },
   };

dna.dom.onReady(app.setup);
