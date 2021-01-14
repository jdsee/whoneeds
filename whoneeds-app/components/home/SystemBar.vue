<template>
  <v-system-bar
    v-if="model"
    app
    color="#262626"
    class="px-4"
    dark
    height="58"
  >
    <v-spacer />

    <v-btn
      class="text-capitalize subtitle-2 mr-6"
      color="success"
      href="https://twitter.com/whoneeds6"
      min-width="112"
    >
      Visit us on Twitter
    </v-btn>

    <v-divider
      class="ml-6 mr-3"
      vertical
    />

    <v-btn
      class="text-capitalize grey--text mr-n2 mr-sm-n1"
      small
      text
      @click="model = false"
    >
      <v-icon
        color="white"
        :left="$vuetify.breakpoint.smAndUp"
      >
        mdi-close
      </v-icon>

      <span class="hidden-xs-only">
        No thanks
      </span>
    </v-btn>
  </v-system-bar>
</template>

<script>
  export default {
    name: 'HomeSystemBar',

    data: () => ({
      model: true,
    }),
  }
</script>
