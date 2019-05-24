'use strict';

var gulp = require('gulp');
var connect = require('gulp-connect');
var shell = require('gulp-shell');
var del = require('del');

gulp.task('clean', del.bind(null, ['build']));

gulp.task('sphinx', shell.task('sphinx-build -W -d build/doctrees source build/html'));
gulp.task('sphinx:dev', shell.task('sphinx-build source build/dev/html'));

gulp.task('build', gulp.series('clean', 'sphinx'));
gulp.task('build:dev', gulp.series('clean', 'sphinx:dev'));

gulp.task('connect', function(done) {
  connect.server({
    root: 'build/dev/html',
    livereload: true
  });
  done();
});

// TODO: Don't stop watching if Sphinx or Sass throws an error
gulp.task('watch', () => {
  gulp.watch('./source/**', gulp.series('sphinx:dev'));
});

gulp.task('default', gulp.series('build:dev', gulp.parallel('connect', 'watch')));
