const
    gulp = require('gulp'),
    gutil = require('gulp-util'),
    del = require('del'),
    spawn = require('child_process').spawn,

    webserver = require('gulp-webserver');

gulp.task('clean', () => del(['build']));

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

gulp.task('sphinx', shell(
    'sphinx', 'sphinx-build', ['-W', '-d', 'build/doctrees', 'source', 'build/html']
));

gulp.task('sphinx:dev', shell(
    'sphinx', 'sphinx-build', ['source', 'build/dev/html']
));

gulp.task('build', ['clean', 'sphinx']);

// TODO: Don't stop watching if Sphinx or Sass throws an error
gulp.task('watch', ['clean', 'sphinx:dev'], () =>
    gulp.watch('./source/**', ['sphinx:dev'])
);

gulp.task('webserver', ['watch'], () =>
    gulp.src('build/dev/html')
        .pipe(webserver({
            livereload: true,
            enable: true,
            open: true
        }))
);

gulp.task('default', ['webserver']);
