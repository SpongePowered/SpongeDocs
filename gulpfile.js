'use strict';

const gulp = require('gulp');
const browsersync = require('browser-sync').create();
const del = require('del');
const spawn = require('child_process').spawn;

function shell(plugin, command, args) {
    return (done) =>
        spawn(command, args, {stdio: 'inherit'})
            .on('error', (err) => {
                done(new gutil.PluginError(plugin, err))
            })
            .on('exit', (code) => {
                if (code == 0) {
                    // Process completed successfully
                    done()
                } else {
                    done(new gutil.PluginError(plugin, `Process failed with exit code ${code}`));
                }
            })
}

function webserver(done) {
  browsersync.init({
    server: {
      baseDir: "./build/dev/html/"
    },
    livereload: true
  }, function () { this.server.on('close', done) })
};

function reload(done) {
  browsersync.reload();
  done();
}

function watch() {
  gulp.watch('./source/**', gulp.series('sphinx:dev', reload));
};

gulp.task('clean', () => del(['build']));

gulp.task('sphinx', shell(
    'sphinx', 'sphinx-build', ['-W', '-d', 'build/doctrees', 'source', 'build/html']
));

gulp.task('sphinx:dev', shell(
    'sphinx', 'sphinx-build', ['source', 'build/dev/html']
));

gulp.task('build', gulp.series('clean', 'sphinx'));
gulp.task('build:dev', gulp.series('clean', 'sphinx:dev'));

gulp.task('default', gulp.series('build:dev', gulp.parallel(webserver, watch)));
