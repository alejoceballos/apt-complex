module.exports = function(grunt) { 'use strict';

    grunt.initConfig({
        /**
         * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
         */
        bower: {
            install: {
                options: {
                    targetDir: './bower_components'
                }
            }
        },

        /**
         * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
         */
        concat: {
            vendorJs: {
                src: [
                    './bower_components/jquery/jquery.js',
                    './bower_components/angular/angular.js',
                    './bower_components/bootstrap/dist/js/bootstrap.js',

                    './bower_components/angular-route/angular-route.js',
                    './bower_components/angular-bootstrap/ui-bootstrap.js',
                    './bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
                    './bower_components/angular-dragdrop/src/angular-dragdrop.js',
                    './bower_components/ng-flow/src/angular-flow.js',
                    './bower_components/angular-resource/angular-resource.js',

                    './bower_components/angular-animate/angular-animate.js',
                    './bower_components/angular-growl-v2/angular-growl.js',

                    './bower_components/moment/moment.js',
                    './bower_components/angular-moment/angular-moment.js',

                    './bower_components/moment/locale/pt-br.js',
                    './bower_components/angular-bootstrap-datetimepicker/datetimepicker.js',

                    './bower_components/angular-translate/angular-translate.js',

                    './bower_components/nsPopover/nsPopover.js'
                ],
                dest: './src/main/resources/public/javascript/vendor.js'
            },
            vendorCss: {
                src: [
                    './bower_components/bootstrap/dist/css/bootstrap.css',
                    './bower_components/font-awesome/font-awesome.css',
                    './bower_components/angular-growl-v2/angular-growl.css',
                    './bower_components/angular-bootstrap-datetimepicker/datetimepicker.css'
                ],
                dest: './src/main/resources/public/stylesheets/vendor.css'
            }
        },

        /**
         * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
         */
        copy: {
            bootstrapFonts: {
                cwd: './bower_components/bootstrap/dist/fonts',
                src: [
                    'glyphicons-halflings-regular.ttf',
                    'glyphicons-halflings-regular.woff',
                    'glyphicons-halflings-regular.woff2'
                ],
                dest: './src/main/resources/public/fonts',
                expand: true
            },
            bootstrapCss: {
                cwd: './bower_components/bootstrap/dist/css',
                src: [
                    'bootstrap.css.map',
                    'bootstrap-theme.css.map'
                ],
                dest: './src/main/resources/public/stylesheets',
                expand: true
            },
            awesomeFonts: {
                cwd: './bower_components/font-awesome',
                src: [
                    'fontawesome-webfont.ttf',
                    'fontawesome-webfont.woff'
                ],
                dest: './src/main/resources/public/fonts',
                expand: true
            }
        }
    });

    /**
     *
     */
    grunt.loadNpmTasks('grunt-bower-task');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-copy');

    /**
     *
     */
    grunt.registerTask('default', [ 'bower', 'concat', 'copy' ]);

    /**
     *
     */
    grunt.registerTask('build', [ 'bower', 'concat', 'copy' ]);

};