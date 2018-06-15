// Snap Backup
// Gulp configuration and tasks

// Periodically check dependencies:
//    $ cd snapbackup
//    $ npm outdated
//    $ npm update

const gulp =        require('gulp');
const fileInclude = require('gulp-file-include');
const htmlHint =    require('gulp-htmlhint');
const jsHint =      require('gulp-jshint');
const rename =      require('gulp-rename');
const w3cJs =       require('gulp-w3cjs');
const del =         require('del');
const mergeStream = require('merge-stream');

const pkg = require('./package.json');
const releaseUrl = 'https://github.com/snap-backup/snapbackup/blob/master/releases/';
const installer = {
   mac: 'snap-backup-installer-' + pkg.version + '.pkg',
   win: 'snap-backup-installer-' + pkg.version + '.msi'
   };
const download = {
   mac:  releaseUrl + installer.mac +    '?raw=true',
   win:  releaseUrl + installer.win +    '?raw=true',
   java: releaseUrl + 'snapbackup.jar' + '?raw=true'
   };
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
   updated:       pkg.extra.updated,
   jarSize:       pkg.extra.jarSize,
   installer:     installer,
   download:      download,
   propertiesUri: 'https://github.com/snap-backup/snapbackup/blob/master/src/resources/properties'
   };
const httpdocsFolder = 'website/httpdocs';
const htmlHintConfig = { 'attr-value-double-quotes': false };

function cleanWebsite() {
    return del(httpdocsFolder + '/**');
    }

function buildWebsite() {
   return mergeStream(
      gulp.src('src/resources/snap-backup-user-guide.html')
         .pipe(w3cJs())
         .pipe(w3cJs.reporter())
         .pipe(gulp.dest(httpdocsFolder))
         .pipe(htmlHint(htmlHintConfig))
         .pipe(htmlHint.reporter()),
      gulp.src('src/resources/graphics/application/language-*.png')
         .pipe(gulp.dest(httpdocsFolder + '/graphics')),
      gulp.src('src/resources/properties/SnapBackup*.properties')
         .pipe(rename({ extname: '.properties.txt' }))
         .pipe(gulp.dest(httpdocsFolder + '/translate')),
      gulp.src('website/static/**/*')
         .pipe(gulp.dest(httpdocsFolder)),
      gulp.src('website/root/**/*.html')
         .pipe(fileInclude({ basepath: '@root', indent: true, context: context }))
         .pipe(w3cJs())
         .pipe(w3cJs.reporter())
         .pipe(htmlHint(htmlHintConfig))
         .pipe(htmlHint.reporter())
         .pipe(gulp.dest(httpdocsFolder)),
      gulp.src('website/static/*.js')
         .pipe(jsHint(jsHintConfig))
         .pipe(jsHint.reporter())
      );
   }

gulp.task('clean', cleanWebsite);
gulp.task('web',   buildWebsite);
