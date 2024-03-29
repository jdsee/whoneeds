export default {
  localStorage: false,
  cookie: {
    options: {
      maxAge: 604800
    }
  },
  strategies: {
    local: {
      token: {
        property: 'accessToken',
        maxAge: 1800
      },
      user: {
        property: false
      },
      endpoints: {
        login: { url: '/login', method: 'post', propertyName: 'accessToken' },
        logout: false,
        user: { url: '/users/me', method: 'get' }
      }
    }
  },
  redirect: {
    login: '/login',
    logout: '/',
    home: '/users/me'
  },
  plugins: ['~/plugins/auth.js']
}
