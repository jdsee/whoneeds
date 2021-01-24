<template>
  <v-img
    :min-height="minHeight"
    :src="require('@/assets/time-for-change.jpg')"
  >
    <v-container class="fill-height px-4 py-12">
      <v-responsive class="d-flex align-center" height="100%" max-width="700" width="100%">
        <h1 class="white--text text--lighten-1">
          This application is meant to help charitable projects to collect all
          sorts of donations.
        </h1>
        <v-btn elevation="6" rounded to="about" nuxt>
          Learn more about us
        </v-btn>
        <span class="white--text font-weight-bold ml-6 mr-4 my-4">or</span>
        <v-btn elevation="6" rounded to="/register" nuxt>
          Register now
        </v-btn>
      </v-responsive>
    </v-container>
  </v-img>
</template>

<script>
export default {
  auth: false,
  head () {
    return {
      title: 'WHONEEDS'
    }
  },
  computed: {
    minHeight () {
      const height = this.$vuetify.breakpoint.mdAndUp ? '100vh' : '50vh'
      return `calc(${height} - ${this.$vuetify.application.top}px)`
    }
  }
}
</script>
