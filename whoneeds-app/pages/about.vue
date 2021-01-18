<template>
  <v-parallax
    height="300"
    src="https://cdn.vuetifyjs.com/images/parallax/material2.jpg"
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

</v-parallax>
</template>


<script>

  export default {
    head() {
      return {
        title: 'About our Idea',
      }
    },
    name: 'SectionHeroAlt',

    provide: {
      heading: { align: 'center' },
    },

    data: () => ({
      title: 'ABOUT',

    }),

    computed: {
      gradient () {

      },

      items () {
        return [
          { text: 'HOME', to: '/' },
          { text: this.title },
        ]
      },
    },
  }
</script>
