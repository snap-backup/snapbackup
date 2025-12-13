// Snap Backup
// Mocha Specification Suite

// Imports
import { assertDeepStrictEqual } from 'assert-deep-strict-equal';
import { revWebAssets } from 'rev-web-assets';
import fs from 'fs';

////////////////////////////////////////////////////////////////////////////////
describe('The website root folder', () => {

   it('contains the correct files', () => {
      const actual = fs.readdirSync('website-target/3-prod').map(revWebAssets.stripHash).sort();
      const expected = [
         'about',
         'app',
         'app.min.js',
         'download',
         'faq',
         'graphics',
         'index.html',
         'screen',
         'snap-backup-user-guide.html',
         'style.css',
         'translate',
         'version',
         ];
      assertDeepStrictEqual(actual, expected);
      });

   });
