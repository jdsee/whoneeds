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
