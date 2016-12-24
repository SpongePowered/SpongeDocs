var gulp = require('gulp');

var webserver = require('gulp-webserver');
var shell = require('gulp-shell');

var del = require('del');


var target = 'dirhtml';

gulp.task('clean', function (cb) {
  del([
    'build'
  ], cb);
});

gulp.task('sphinx', shell.task(
  'sphinx-build -b ' + target + ' source build/' + target
));

gulp.task('webserver', ['sphinx'], function () {
  gulp.src('build/' + target)
    .pipe(webserver({
      livereload: true,
      enable: true,
      open: true
    }));
});

gulp.task('watch', function () {
  gulp.watch(['source/**/*'], ['sphinx']);
});

gulp.task('default', ['clean', 'sphinx', 'webserver', 'watch']);
