// Snap Backup
// Gulp configuration and tasks

// Periodically check dependencies:
//    $ cd snapbackup
//    $ npm outdated
//    $ npm update

var gulp =        require('gulp');
var fileInclude = require('gulp-file-include');
var htmlHint =    require('gulp-htmlhint');
var jsHint =      require('gulp-jshint');
var rename =      require('gulp-rename');
var w3cJs =       require('gulp-w3cjs');
var del =         require('del');

var pkg = require('./package.json');
var releaseUrl = 'https://github.com/snap-backup/snapbackup/blob/master/releases/';
var installer = {
   mac: 'snap-backup-installer-' + pkg.version + '.dmg',
   win: 'snap-backup-installer-' + pkg.version + '.msi'
   };
var download = {
   mac:  releaseUrl + installer.mac +    '?raw=true',
   win:  releaseUrl + installer.win +    '?raw=true',
   java: releaseUrl + 'snapbackup.jar' + '?raw=true'
   };
var jsHintConfig = {
   strict:  'implied',
   undef:   true,
   unused:  true,
   jquery:  true,
   globals: { library: false, window: false }
   };
var context = {
   pkg:           pkg,
   pageTitle:     pkg.description,
   updated:       'August 5, 2017',
   jarSize:       '223 KB',
   installer:     installer,
   download:      download,
   propertiesUri: 'https://github.com/snap-backup/snapbackup/blob/master/src/resources/properties'
   };
var httpdocsFolder = 'website/httpdocs';
var htmlHintConfig = { 'attr-value-double-quotes': false };

function cleanWebsite() {
    return del(httpdocsFolder + '/**');
    }

function buildWebsite() {
   gulp.src('src/resources/snap-backup-user-guide.html')
      .pipe(w3cJs())
      .pipe(w3cJs.reporter())
      .pipe(gulp.dest(httpdocsFolder))
      .pipe(htmlHint(htmlHintConfig))
      .pipe(htmlHint.reporter());
   gulp.src('src/resources/graphics/application/language-*.png')
      .pipe(gulp.dest(httpdocsFolder + '/graphics'));
   gulp.src('src/resources/properties/SnapBackup*.properties')
      .pipe(rename({ extname: '.properties.txt' }))
      .pipe(gulp.dest(httpdocsFolder + '/translate'));
   gulp.src('website/static/**/*')
      .pipe(gulp.dest(httpdocsFolder));
   gulp.src('website/root/**/*.html')
      .pipe(fileInclude({ basepath: '@root', indent: true, context: context }))
      .pipe(w3cJs())
      .pipe(w3cJs.reporter())
      .pipe(htmlHint(htmlHintConfig))
      .pipe(htmlHint.reporter())
      .pipe(gulp.dest(httpdocsFolder));
   gulp.src('website/static/*.js')
      .pipe(jsHint(jsHintConfig))
      .pipe(jsHint.reporter());
   }

gulp.task('clean', cleanWebsite);
gulp.task('web',   ['clean'], buildWebsite);
