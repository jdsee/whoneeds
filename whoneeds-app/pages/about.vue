<template>
  <Img
    :height="$vuetify.breakpoint.mdAndUp ? 350 : 225"
    src="https://cdn.vuetifyjs.com/images/parallax/material2.jpg"
    color="#45516b"
    flat
    max-width="100%"
    tile
  >
  <v-row
        v-if="title"
        align="center"
        class="ma-0 fill-height text-center"
        justify="center"
      >
        <v-col cols="12">
          <Heading
            :title="title"
            space="2"
            weight="500"
          />
          <Divider
            color="primary"
            dense
          />
          <v-breadcrumbs
            :items="items"
            class="justify-center pa-0"
            divider=">"
          />
        </v-col>
      </v-row>

</Img>
</template>


<script>

  export default {
    head() {
      return {
        title: 'About our Idea',
      }
    },
    name: 'AboutHero',

    provide: {
      heading: { align: 'center' },
    },
    data: () => ({
      title: 'ABOUT',

    }),
    computed: {
      items () {
        return [
          { text: 'HOME', to: '/' },
          { text: this.title },
        ]
      },
    },
  }
</script>
