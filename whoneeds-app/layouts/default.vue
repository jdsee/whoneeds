<template>
  <div id="app">
    <v-app>
      <TheNavigation />
      <v-main class="main">
        <Nuxt />
      </v-main>
      <TheFooter />
    </v-app>
  </div>
</template>

<script>
import TheFooter from "./partials/TheFooter.vue";
import TheNavigation from "./partials/TheNavigation.vue";
export default {
  components: { TheFooter, TheNavigation }
};
</script>

<style scoped>
.main {
  min-height: 85%;
}
</style>
