// Snap Backup ~ GPLv3
// Gulp configuration and tasks

// Imports
import concat      from 'gulp-concat';
import fileInclude from 'gulp-file-include';
import gulp        from 'gulp';
import mergeStream from 'merge-stream';
import rename      from 'gulp-rename';
import size        from 'gulp-size';
import zip         from 'gulp-zip';
import { readFileSync } from 'fs';

// Setup
const pkg = JSON.parse(readFileSync('package.json', 'utf-8'));
const releaseUrl = pkg.homepage + '/raw/main/releases/';
const installer = {
   mac:  'snap-backup-installer-v' + pkg.version + '.pkg',
   win:  'snap-backup-installer-v' + pkg.version + '.msi',
   java: 'snapbackup-v' +            pkg.version + '.jar',
   };
const download = {
   mac:  releaseUrl + installer.mac,
   win:  releaseUrl + installer.win,
   java: releaseUrl + installer.java,
   past: releaseUrl + 'archive',
   };
const context = {
   pkg:           pkg,
   pageTitle:     pkg.description,
   installer:     installer,
   download:      download,
   propertiesUri: pkg.homepage + '/blob/main/src/resources/properties',
   };
const websitesTargetFolder = 'websites-target';
const orgWebsite = {
   root:      websitesTargetFolder + '/www.snapbackup.org',
   translate: websitesTargetFolder + '/www.snapbackup.org/translate',
   graphics:  websitesTargetFolder + '/www.snapbackup.org/graphics',
   };

// Tasks
const task = {
   buildWebsites() {
      const processIndex = () =>
         gulp.src(`websites/web/index.html`)
            .pipe(fileInclude({ basepath: '@root', indent: true, context: context }))
            .pipe(size({ showFiles: true }))
            .pipe(gulp.dest(websitesTargetFolder));
      const processWeb = (topLevel) =>
         gulp.src(`websites/web/www.snapbackup.${topLevel}/**/*`)
            .pipe(fileInclude({ basepath: '@root', indent: true, context: context }))
            .pipe(size({ showFiles: true }))
            .pipe(gulp.dest(`${websitesTargetFolder}/www.snapbackup.${topLevel}`));
      const processUserGuide = () =>
         gulp.src('src/resources/snap-backup-user-guide.html')
            .pipe(size({ showFiles: true }))
            .pipe(gulp.dest(orgWebsite.root));
      const processProperties = () =>
         gulp.src('src/resources/properties/SnapBackup*.properties')
            .pipe(rename({ extname: '.properties.txt' }))
            .pipe(gulp.dest(orgWebsite.translate))
            .pipe(zip('SnapBackup.properties.zip'))
            .pipe(size({ showFiles: true }))
            .pipe(gulp.dest(orgWebsite.translate));
      const processDefaultProperties = () =>
         gulp.src('src/resources/properties/SnapBackup.properties')
            .pipe(rename('SnapBackup_en.properties.txt'))
            .pipe(size({ showFiles: true }))
            .pipe(gulp.dest(orgWebsite.translate));
      const processFlags = () =>
         gulp.src('src/resources/graphics/application/language-*.png')
            .pipe(size({ showFiles: true }))
            .pipe(gulp.dest(orgWebsite.graphics));
      const processGraphics = () =>
         gulp.src('websites/graphics/**/*')
            .pipe(size({ showFiles: true }))
            .pipe(gulp.dest(orgWebsite.graphics));
      const processCss = () =>
         gulp.src('websites/*.css')
            .pipe(concat('style.css'))
            .pipe(size({ showFiles: true }))
            .pipe(gulp.dest(orgWebsite.root));
      const processJs = () =>
         gulp.src('websites/*.js')
            .pipe(concat('app.js'))
            .pipe(size({ showFiles: true }))
            .pipe(gulp.dest(orgWebsite.root));
      return mergeStream(
         processIndex(),
         processWeb('com'),
         processWeb('net'),
         processWeb('org'),
         processUserGuide(),
         processProperties(),
         processDefaultProperties(),
         processFlags(),
         processGraphics(),
         processCss(),
         processJs());
      },
   };

// Gulp
gulp.task('web', task.buildWebsites);
