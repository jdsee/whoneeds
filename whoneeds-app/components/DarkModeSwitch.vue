<template>
  <v-container fluid>
    <v-container v-if="mode === 'dual-button'">
      <v-btn id="to-dark-switcher" icon @click="$vuetify.theme.dark = true">
        <v-icon
          :color="
            $vuetify.theme.dark ? 'yellow lighten-5' : 'blue-grey lighten-2'
          "
        >
          mdi-weather-night
        </v-icon>
      </v-btn>
      <v-btn id="to-light-switcher" icon @click="$vuetify.theme.dark = false">
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
        inset
        :label="description"
        :append-icon="activeIcon"
        color="yellow lighten-2"
        @click="toggleDarkMode"
      />
    </v-row>
    <h2 v-else>
      ="Single button is not implemented yet"
    </h2>
  </v-container>
</template>

<script>
export default {
  props: {
    mode: {
      type: String,
      default: 'dual-button',
      required: false,
      validator: value =>
        ['single-button', 'dual-button', 'switch'].includes(value)
    }
  },
  computed: {
    activeIcon () {
      return this.$vuetify.theme.dark
        ? 'mdi-weather-sunny'
        : 'mdi-weather-night'
    }
  },
  methods: {
    toggleDarkMode () {
      this.$vuetify.theme.dark = !this.$vuetify.theme.dark
    },
    description () {
      return `Switch to ${this.$vuetify.theme.dark ? 'Light' : 'Dark'} Mode`
    }
  }
}
</script>

<style scoped>
.v-row .v-icon {
  padding: 3re;
}
</style>
