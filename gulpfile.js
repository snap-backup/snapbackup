// Snap Backup
// Gulp configuration and tasks

// Periodically check dependencies:
//    $ cd snapbackup
//    $ npm outdated
//    $ npm update

var gulp =        require('gulp');
var fileinclude = require('gulp-file-include');
var htmlhint =    require('gulp-htmlhint');
var rename =      require('gulp-rename');
var w3cjs =       require('gulp-w3cjs');
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
///////////////////
// Temporary
installer.win = installer.win.replace('6.1.0', '6.0');
download.win =   download.win.replace('6.1.0', '6.0');
///////////////////
var context = {
   pkg:        pkg,
   webRoot:    '..',
   pageTitle:  pkg.description,
   updated:    'October 16, 2016',
   jarSize:    '223 KB',
   installer:  installer,
   download:   download
   };
var httpdocsFolder = 'website/httpdocs';
var htmlHintConfig = { 'attr-value-double-quotes': false };

function cleanWebsite() {
    return del(httpdocsFolder + '/**');
    }

function buildWebsite() {
   gulp.src('src/resources/snap-backup-user-guide.html')
      .pipe(w3cjs())
      .pipe(w3cjs.reporter())
      .pipe(gulp.dest(httpdocsFolder))
      .pipe(htmlhint(htmlHintConfig))
      .pipe(htmlhint.reporter());
   gulp.src('src/resources/graphics/application/language-*.png')
      .pipe(gulp.dest(httpdocsFolder + '/graphics'));
   gulp.src('src/resources/properties/SnapBackup*.properties')
      .pipe(rename({ extname: '.properties.txt' }))
      .pipe(gulp.dest(httpdocsFolder + '/translate'));
   gulp.src('website/static/**/*')
      .pipe(gulp.dest(httpdocsFolder));
   gulp.src('website/root/**/*.html')
      .pipe(fileinclude({ basepath: '@root', indent: true, context: context }))
      .pipe(w3cjs())
      .pipe(w3cjs.reporter())
      .pipe(htmlhint(htmlHintConfig))
      .pipe(htmlhint.reporter())
      .pipe(gulp.dest(httpdocsFolder));
   }

gulp.task('clean', cleanWebsite);
gulp.task('web',   ['clean'], buildWebsite);
