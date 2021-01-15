export const AUTH_MUTATIONS = {
  SET_USER: 'SET_USER',
  SET_PAYLOAD: 'SET_PAYLOAD',
  LOGOUT: 'LOGOUT',
};

export const state = () => ({
  accessToken: null,
  refreshToken: null,
  id: null,
  emailAddress: null
});

export const mutations = {
  [AUTH_MUTATIONS.SET_USER](state, { id, emailAddress }) {
    state.id = id;
    state.emailAddress = emailAddress;
  },
  [AUTH_MUTATIONS.SET_PAYLOAD](state, { accessToken, refreshToken }) {
    state.accessToken = accessToken;
    if (refreshToken) {
      state.refreshToken = refreshToken;
    }
  },
  [AUTH_MUTATIONS.LOGOUT](state) {
    state.id = null;
    state.emailAddress = null;
    state.accessToken = null;
    state.refreshToken = null;
  }
};

export const actions = {
  async login({ commit }, { emailAddress, password }) {
    // const { data: { data: { user, payload } } } = await this.$axios.post(
    const data = await this.$axios.post(
      '/login',
      { emailAddress, password }
    );

    console.log(data)

    // commit(AUTH_MUTATIONS.SET_USER, user);
    const { accessToken, refreshToken } = data
    commit(AUTH_MUTATIONS.SET_PAYLOAD, { accessToken, refreshToken });
  },
  async register({ commit }, { emailAddress, password }) {
    const { data: { data: { user, payload } } } = await this.$axios.post(
      '/register',
      { emailAddress, password }
    );
    commit(AUTH_MUTATIONS.SET_USER, user);
    commit(AUTH_MUTATIONS.SET_PAYLOAD, payload);
  },
  async refresh({ commit, state }) {
    const { refreshToken } = state;
    const { data: { data: { payload } } } = await this.$axios.post(
      '/refresh',
      { refreshToken }
    );
    commit(AUTH_MUTATIONS.SET_PAYLOAD, payload);
  },
  logout({ commit }) {
    commit(AUTH_MUTATIONS.LOGOUT);
  }
};

export const getters = {
  isAuthenticated: state => state.accessToken && state.accessToken !== ''
};
