<template>
  <v-container fluid>
    <v-container v-if="mode === 'dual-button'">
      <v-btn icon id="to-dark-switcher" @click="$vuetify.theme.dark = true">
        <v-icon
          :color="
            $vuetify.theme.dark ? 'yellow lighten-5' : 'blue-grey lighten-2'
          "
        >
          mdi-weather-night
        </v-icon>
      </v-btn>
      <v-btn icon id="to-light-switcher" @click="$vuetify.theme.dark = false">
        <v-icon
          :color="
            $vuetify.theme.dark ? 'blue-grey lighten-2' : 'yellow darken-1'
          "
        >
          mdi-weather-sunny
        </v-icon>
      </v-btn>
    </v-container>
    <v-row v-else-if="mode === 'switch'">
      <v-switch
        @click="toggleDarkMode"
        inset
        :label="`Switch to ${$vuetify.theme.dark ? 'Light' : 'Dark'} Mode`"
        :append-icon="activeIcon"
        color="yellow lighten-2"
      ></v-switch>
    </v-row>
    <h2 v-else>="Single button is not implemented yet"</h2>
  </v-container>
</template>

<script>
export default {
  props: {
    mode: {
      type: String,
      default: "dual-button",
      required: false,
      validator: value =>
        ["single-button", "dual-button", "switch"].indexOf(value) !== -1
    }
  },
  computed: {
    activeIcon() {
      return $vuetify.theme.dark ? "mdi-weather-sunny" : "mdi-weather-night";
    }
  },
  methods: {
    toggleDarkMode() {
      this.$vuetify.theme.dark = !this.$vuetify.theme.dark;
    }
  }
};
</script>

<style scoped>
.v-row .v-icon {
  padding: 3re;
}
</style>
