import vuetifyConfig from './config/vuetify'
import i18n from "./config/i18n";

export default {
  // Global page headers (https://go.nuxtjs.dev/config-head)
  head: {
    title: 'whoneeds-app',
    meta: [
      {charset: 'utf-8'},
      {name: 'viewport', content: 'width=device-width, initial-scale=1'},
      {hid: 'description', name: 'description', content: ''}
    ],
    link: [
      {rel: 'icon', type: 'image/x-icon', href: '/favicon.ico'}
    ]
  },

  // Global CSS (https://go.nuxtjs.dev/config-css)
  css: [],

  // Plugins to run before rendering page (https://go.nuxtjs.dev/config-plugins)
  plugins: [],

  // Auto import components (https://go.nuxtjs.dev/config-components)
  components: true,

  // Modules for dev and build (recommended) (https://go.nuxtjs.dev/config-modules)
  buildModules: [
    ['@nuxtjs/vuetify', vuetifyConfig],
    ['nuxt-i18n',
      {
        defaultLocale: 'de',
        seo: true,
        locales: [
          {
            code: 'de',
            name: 'Deutsch',
            iso: 'de-DE'
          },
          {
            code: 'en',
            name: 'English',
            iso: 'en-US'
          }
        ], vueI18n: i18n
      }]
  ],

  // Modules (https://go.nuxtjs.dev/config-modules)
  modules: [
    '@nuxtjs/axios'
  ],

  axios: {
    baseURL: 'https://localhost:9000/whoneeds/api'
  },

  // Build Configuration (https://go.nuxtjs.dev/config-build)
  build: {}
}
