<template>
  <v-container fill-height text-xs-center>
    <v-row align="center">
      <h1 id="welcomeTag" class="title">{{ $t('imprint.telemedia_act', {paragraph: '§5 TMG'}) }}</h1>
      <h2>
        This application is meant to help charitable projects to collect all
        sorts of donations.
      </h2>
    </v-row>
  </v-container>
</template>

<script>
export default {}
</script>

<style>
.container {
  margin: 0 auto;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.title {
  font-family: "Quicksand", "Source Sans Pro", -apple-system, BlinkMacSystemFont,
  "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
  display: block;
  font-weight: 300;
  font-size: 100px;
  color: #35495e;
  letter-spacing: 1px;
}

.subtitle {
  font-weight: 300;
  font-size: 42px;
  color: #526488;
  word-spacing: 5px;
  padding-bottom: 15px;
}

.links {
  padding-top: 15px;
}
</style>
