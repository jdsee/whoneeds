import authConfig from './auth.config'
import vuetifyConfig from './vuetify.config'

export default {
  // Global page headers (https://go.nuxtjs.dev/config-head)
  head: {
    title: 'whoneeds-app',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/logo.png' }
    ]
  },

  // Global CSS (https://go.nuxtjs.dev/config-css)
  css: [
  ],

  // Plugins to run before rendering page (https://go.nuxtjs.dev/config-plugins)
  plugins: [
    '~/plugins/vee-validate.js'
  ],

  router: {
    middleware: ['auth']
  },

  // Auto import components (https://go.nuxtjs.dev/config-components)
  components: true,

  // Modules for dev and build (recommended) (https://go.nuxtjs.dev/config-modules)
  buildModules: [
    ['@nuxtjs/vuetify', vuetifyConfig]
  ],

  // Modules (https://go.nuxtjs.dev/config-modules)
  modules: [
    '@nuxtjs/axios',
    '@nuxtjs/auth-next',
    '@nuxtjs/toast'
  ],

  auth: authConfig,

  toast: {
    position: 'top-center',
    duration: 1700,
    keepOnHover: true
  },

  publicRuntimeConfig: {
    axios: {
      baseURL: 'http://localhost:9000/whoneeds/api'
    }
  },

  // Build Configuration (https://go.nuxtjs.dev/config-build)
  build: {
    transpile: ['vee-validate/dist/rules']
  }
}
