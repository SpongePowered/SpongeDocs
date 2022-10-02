import { deleteAsync } from 'del'
import gulp from 'gulp'
import pluginerror from 'plugin-error'
import browsersync from 'browser-sync'
import {spawn} from 'child_process'

const devServer = browsersync.create();

function shell(plugin, command, args) {
    return (done) =>
        spawn(command, args, {stdio: 'inherit'})
            .on('error', (err) => {
                done(new pluginerror(plugin, err))
            })
            .on('exit', (code) => {
                if (code == 0) {
                    // Process completed successfully
                    done()
                } else {
                    done(new pluginerror(plugin, `Process failed with exit code ${code}`));
                }
            })
}

function webserver(done) {
  devServer.init({
    watch: true,
    server: "./build/dev/html/"
  }, function () { this.server.on('close', done) })
};


function watch() {
  gulp.watch('./source/**', gulp.series('sphinx:dev'));
};

gulp.task('clean', () => deleteAsync(['build']));

gulp.task('sphinx', shell(
    'sphinx', 'sphinx-build', ['-W', '-d', 'build/doctrees', 'source', 'build/html']
));

gulp.task('sphinx:dev', shell(
    'sphinx', 'sphinx-build', ['source', 'build/dev/html']
));

gulp.task('build', gulp.series('clean', 'sphinx'));
gulp.task('build:dev', gulp.series('clean', 'sphinx:dev'));

gulp.task('default', gulp.series('build:dev', gulp.parallel(webserver, watch)));
