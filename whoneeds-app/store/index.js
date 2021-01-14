export const actions = {
  async nuxtServerInit({ dispatch, _, state }) {
    const { accessToken, refreshToken } = state.auth;

    if (accessToken && refreshToken) {
      try {
        await dispatch('auth/refresh');
      } catch (e) {
        await dispatch('auth/logout');
      }
    }
  }
}