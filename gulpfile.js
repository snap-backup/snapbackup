// Snap Backup ~ GPLv3
// Gulp configuration and tasks

// Imports
const concat =        require('gulp-concat');
const del =           require('del');
const fileInclude =   require('gulp-file-include');
const gulp =          require('gulp');
const htmlHint =      require('gulp-htmlhint');
const htmlValidator = require('gulp-w3c-html-validator');
const jsHint =        require('gulp-jshint');
const mergeStream =   require('merge-stream');
const rename =        require('gulp-rename');
const size =          require('gulp-size');
const zip =           require('gulp-zip');

// Setup
const pkg = require('./package.json');
const releaseUrl = pkg.homepage + '/blob/master/releases/';
const installer = {
   mac:  'snap-backup-installer-' + pkg.version + '.pkg',
   win:  'snap-backup-installer-' + pkg.version + '.msi',
   java: 'snapbackup-' +            pkg.version + '.jar'
   };
const download = {
   mac:  releaseUrl + installer.mac +  '?raw=true',
   win:  releaseUrl + installer.win +  '?raw=true',
   java: releaseUrl + installer.java + '?raw=true',
   past: releaseUrl
   };
const htmlHintConfig = { 'attr-value-double-quotes': false };
const jsHintConfig = {
   strict:  'implied',
   undef:   true,
   unused:  true,
   browser: true,
   jquery:  true,
   globals: { dna: false, library: false }
   };
const context = {
   pkg:           pkg,
   pageTitle:     pkg.description,
   installer:     installer,
   download:      download,
   propertiesUri: pkg.homepage + '/blob/master/src/resources/properties'
   };
const websitesTargetFolder = 'websites-target';
const orgWebsite = {
   root:      websitesTargetFolder + '/www.snapbackup.org',
   translate: websitesTargetFolder + '/www.snapbackup.org/translate',
   graphics:  websitesTargetFolder + '/www.snapbackup.org/graphics'
   };

// Tasks
const task = {
   cleanWebsitesTarget: () => {
      return del(websitesTargetFolder + '/**/*');  //only delete folder contents so as to not kill webserver
      },
   buildWebsites: () => {
      const processWeb = (topLevel) =>
         gulp.src(`websites/web/www.snapbackup.${topLevel}/**/*`)
            .pipe(fileInclude({ basepath: '@root', indent: true, context: context }))
            .pipe(gulp.dest(`${websitesTargetFolder}/www.snapbackup.${topLevel}`));
      const processUserGuide = () =>
         gulp.src('src/resources/snap-backup-user-guide.html')
            .pipe(gulp.dest(orgWebsite.root));
      const processProperties = () =>
         gulp.src('src/resources/properties/SnapBackup*.properties')
            .pipe(rename({ extname: '.properties.txt' }))
            .pipe(gulp.dest(orgWebsite.translate))
            .pipe(zip('SnapBackup.properties.zip'))
            .pipe(gulp.dest(orgWebsite.translate));
      const processDefaultProperties = () =>
         gulp.src('src/resources/properties/SnapBackup.properties')
            .pipe(rename('SnapBackup_en.properties.txt'))
            .pipe(gulp.dest(orgWebsite.translate));
      const processFlags = () =>
         gulp.src('src/resources/graphics/application/language-*.png')
            .pipe(gulp.dest(orgWebsite.graphics));
      const processGraphics = () =>
         gulp.src('websites/graphics/**/*')
            .pipe(gulp.dest(orgWebsite.graphics));
      const processCss = () =>
         gulp.src('websites/*.css')
            .pipe(concat('style.css'))
            .pipe(gulp.dest(orgWebsite.root));
      const processJs = () =>
         gulp.src('websites/*.js')
            .pipe(concat('app.js'))
            .pipe(gulp.dest(orgWebsite.root));
      return mergeStream(
         processWeb('com'),
         processWeb('eu'),
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
   lintWebsites: () => {
      const analyzeHtml = () =>
         gulp.src(websitesTargetFolder + '/**/*.html')
            .pipe(htmlHint(htmlHintConfig))
            .pipe(htmlHint.reporter())
            .pipe(htmlValidator())
            .pipe(htmlValidator.reporter())
            .pipe(size({ showFiles: true }));
      const analyzeJs = () =>
         gulp.src('websites/**/*.js')
            .pipe(jsHint(jsHintConfig))
            .pipe(jsHint.reporter())
            .pipe(size({ showFiles: true }));
      return mergeStream(analyzeHtml(), analyzeJs());
      }
   };

// Gulp
gulp.task('clean', task.cleanWebsitesTarget);
gulp.task('web',   task.buildWebsites);
gulp.task('lint',  task.lintWebsites);
