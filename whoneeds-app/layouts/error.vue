<template>
  <v-container fill-height fluid>
    <v-row
      align="center"
      justify="center"
    >
      <v-col cols="12" sm="4">
        <div class="container">
          <v-img
            :height="$vuetify.breakpoint.mdAndUp ? 350 : 225"
            :src="require('@/assets/404Pulp.png')"
            flat
            max-width="100%"
          />
          <h1 v-if="error.statusCode === 404">
            Got lost?<v-icon>
              {{ icon }}
            </v-icon>
          </h1>
          <h1 v-else>
            An error occurred
          </h1>
          <v-divider :dark="true" />
          <v-btn rounded to="/" nuxt>
            Bring me back home
          </v-btn>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  layout: 'default',
  props: ['error'],

  data: () => ({
    icon: 'mdi-emoticon-sad-outline'
  }),
  head () {
    return {
      title: 'Oops! No Page Found'
    }
  }
}
</script>
