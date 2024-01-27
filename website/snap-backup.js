// Snap Backup

const app = {
   setup() {
      const wedge = globalThis.document.createElement('div');
      wedge.classList.add('wedge');
      globalThis.document.querySelector('main').prepend(wedge);
      const params =    new URLSearchParams(globalThis.location.search);
      const macosMode = libX.browser.macOS() && params.get('view') !== 'all';
      dna.dom.toggleClass(globalThis.document.body, 'macos-mode', macosMode);
      },
   };

dna.dom.onReady(app.setup);
