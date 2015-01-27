module.exports = function(grunt) {
  require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

  var port = 8999;
  var target = "dirhtml";

  grunt.initConfig({
    clean: {
      build: ["build"],
    },

    exec: {
      sphinx: {
        cmd: 'sphinx-build -b ' + target + ' source build/' + target
      }
    },

    watch: {
      sphinx: {
        files: ['source/**/*.rst', 'spongedocs-theme/**/*'],
        tasks: ['exec:sphinx']
      },

      livereload: {
        files: ['build/' + target + '/**/*'],
        options: { livereload: true }
      }
    },

    open : {
      dev: {
        path: 'http://localhost:' + port
      }
    },

    connect: {
      server: {
        options: {
          port: port,
          base: 'build/' + target,
          livereload: true
        }
      }
    }

  });

  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-exec');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-open');
  grunt.loadNpmTasks('grunt-contrib-connect');

  grunt.registerTask('default', ['clean:build', 'exec:sphinx', 'connect', 'open', 'watch']);
}

