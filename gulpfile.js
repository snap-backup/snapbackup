// Snap Backup
// Gulp configuration and tasks

// Imports
const gulp =             require('gulp');
const concat =           require('gulp-concat');
const fileInclude =      require('gulp-file-include');
const htmlHint =         require('gulp-htmlhint');
const jsHint =           require('gulp-jshint');
const rename =           require('gulp-rename');
const w3cHtmlValidator = require('gulp-w3cjs');
const zip =              require('gulp-zip');
const del =              require('del');
const mergeStream =      require('merge-stream');

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
   cleanWebsitesTarget: function() {
      return del(websitesTargetFolder + '/**/*');  //only delete folder contents so as to not kill webserver
      },
   buildWebsites: function() {
      function processWeb(topLevel) {
         return gulp.src(`websites/web/www.snapbackup.${topLevel}/**/*`)
            .pipe(fileInclude({ basepath: '@root', indent: true, context: context }))
            .pipe(gulp.dest(`${websitesTargetFolder}/www.snapbackup.${topLevel}`));
         }
      return mergeStream(
         processWeb('com'),
         processWeb('eu'),
         processWeb('net'),
         processWeb('org'),
         gulp.src('src/resources/snap-backup-user-guide.html')
            .pipe(gulp.dest(orgWebsite.root)),
         gulp.src('src/resources/properties/SnapBackup*.properties')
            .pipe(rename({ extname: '.properties.txt' }))
            .pipe(gulp.dest(orgWebsite.translate))
            .pipe(zip('SnapBackup.properties.zip'))
            .pipe(gulp.dest(orgWebsite.translate)),
         gulp.src('src/resources/properties/SnapBackup.properties')
            .pipe(rename('SnapBackup_en.properties.txt'))
            .pipe(gulp.dest(orgWebsite.translate)),
         gulp.src('src/resources/graphics/application/language-*.png')
            .pipe(gulp.dest(orgWebsite.graphics)),
         gulp.src('websites/graphics/**/*')
            .pipe(gulp.dest(orgWebsite.graphics)),
         gulp.src('websites/*.css')
            .pipe(concat('style.css'))
            .pipe(gulp.dest(orgWebsite.root)),
         gulp.src('websites/*.js')
            .pipe(concat('app.js'))
            .pipe(gulp.dest(orgWebsite.root))
         );
      },
   lintWebsites: function() {
      return mergeStream(
         gulp.src(websitesTargetFolder + '/**/*.html')
            .pipe(w3cHtmlValidator())
            .pipe(w3cHtmlValidator.reporter())
            .pipe(htmlHint(htmlHintConfig))
            .pipe(htmlHint.reporter()),
         gulp.src('websites/**/*.js')
            .pipe(jsHint(jsHintConfig))
            .pipe(jsHint.reporter())
         );
      }
   };

// Gulp
gulp.task('clean', task.cleanWebsitesTarget);
gulp.task('web',   task.buildWebsites);
gulp.task('lint',  task.lintWebsites);
