export default function ({ store, app: { $axios }, _ }) {
  const IGNORED_PATHS = ['/auth/login', '/auth/logout', '/auth/refresh'];

  $axios.onRequest(config => {
    const access_token = store.state.auth.access_token
    if (access_token) {
      config.headers.Authorization = 'Bearer ' + access_token;
    }
    return config;
  });

  $axios.onError(error => {
    return new Promise(async (resolve, reject) => {
      if (IGNORED_PATHS.some(path => error.config.url.includes(path))) return;

      const statusCode = error.response ? error.response.status : -1;
      if (statusCode === 401 || statusCode === 422) {
        // Example API response: 
        // { 
        //   status: 'failed', 
        //   text_code: 'TOKEN_EXPIRED',
        //   message: 'The JWT token is expired',
        //   status_code: 401
        // }
        const { data: { text_code } = { text_code: null } } = error.response || {};
        const refreshToken = store.state.auth.refreshToken;

        if (text_code === 'TOKEN_EXPIRED' && refreshToken) {
          if (error.config.hasOwnProperty('retryAttempts')) {
            return logoutAndRedirect();
          }
        } else {
          const config = { retryAttempts: 1, ...error.config };
          try {
            await store.dispatch('auth/refresh');
            return resolve($axios(config));

          } catch (e) {
            return logoutAndRedirect();
          }
        }
      } else if (text_code === 'TOKEN_INVALID') {
        return logoutAndRedirect();
      }
      return reject(error);
    });
  })
}

async function logoutAndRedirect() {
  await store.dispatch('auth/logout');
  return redirect('/');
}