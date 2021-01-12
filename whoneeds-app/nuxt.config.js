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
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
    ]
  },

  // Global CSS (https://go.nuxtjs.dev/config-css)
  css: [
  ],

  // Plugins to run before rendering page (https://go.nuxtjs.dev/config-plugins)
  plugins: [
    '~/plugins/local-storage',
  ],

  // Auto import components (https://go.nuxtjs.dev/config-components)
  components: true,

  // Modules for dev and build (recommended) (https://go.nuxtjs.dev/config-modules)
  buildModules: [
    ['@nuxtjs/vuetify', {
      theme: {
        dark: false,
        themes: {
          // dark: {
          //   primary: colors.deepPurple.lighten3,
          //   accent: colors.deepPurple.accent3,
          //   secondary: colors.amber.darken3,
          //   info: colors.teal.lighten1,
          //   warning: colors.amber.base,
          //   error: colors.deepOrange.accent4,
          //   success: colors.green.accent4
          // } <-- define colors before using this
        }
      }
    }]
  ],

  // Modules (https://go.nuxtjs.dev/config-modules)
  modules: [
  ],

  // Build Configuration (https://go.nuxtjs.dev/config-build)
  build: {
  }
}
