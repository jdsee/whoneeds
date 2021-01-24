<template>
  <div id="app">
    <v-app>
      <AppBar />
      <v-main class="main">
        <Nuxt />
      </v-main>
      <TheFooter />
    </v-app>
  </div>
</template>

<script>
import TheFooter from '@/components/partials/TheFooter.vue'
import AppBar from '@/components/partials/AppBar.vue'

export default {
  components: {
    TheFooter,
    AppBar
  }
}
</script>

<style>
.main {
  min-height: 85%;
}

html {
  font-family: 'Source Sans Pro',
  -apple-system,
  BlinkMacSystemFont,
  'Segoe UI',
  Roboto,
  'Helvetica Neue',
  Arial,
  sans-serif;
  font-size: 16px;
  word-spacing: 1px;
  -ms-text-size-adjust: 100%;
  -webkit-text-size-adjust: 100%;
  -moz-osx-font-smoothing: grayscale;
  -webkit-font-smoothing: antialiased;
  box-sizing: border-box;
}

*,
*::before,
*::after {
  box-sizing: border-box;
  margin: 0;
}
</style>
