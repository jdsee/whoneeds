<template>
  <v-img :height="$vuetify.breakpoint.mdAndUp ? 1150 : 850" src="https://cdn.vuetifyjs.com/images/parallax/material2.jpg" flat max-width="100%" tile>
    <v-container>
      <v-row v-if="title" align="center" class="ma-0 fill-height text-center" justify="center">
        <v-col cols="12">
          <Heading :title="title" space="2" weight="500" />
          <Divider color="primary" dense />
          <v-breadcrumbs :items="items" class="justify-center pa-0" divider=">" />
        </v-col>
        <v-carousel delimiter-icon="mdi-minus">
          <v-carousel-item v-for="(slide, i) in slides" :key="i">
            <v-sheet :color="slide.color" height="100%" tile>
              <v-row class="fill-height" align="center" justify="center">
                <v-container fill-height>
                  <v-layout align-center>
                    <v-flex>
                      <h3 class="display-3">
                        {{ slide.title }}
                      </h3>
                      <span class="subheading">{{ slide.text }}</span>
                    </v-flex>
                  </v-layout>
                </v-container>
              </v-row>
            </v-sheet>
          </v-carousel-item>
        </v-carousel>
        </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-img>
</template>

<script>
export default {
  auth: false,
  provide: {
    heading: {
      align: 'center'
    }
  },
  data: () => ({
    title: 'ABOUT',

    slides: [{
      color: '#1A2238',
      title: 'Basic intention',
      text: 'Distribute relief supplies better.'
    },
    {
      color: '#61082B',
      title: 'Make your donation count',
      text: 'We want donations to get to where they are needed.'
    },
    {
      color: '#1B5E20',
      title: 'How can you help?',
      text: 'Sign up now and donate the things you no longer need.'
    }
    ]
  }),
  head () {
    return {
      title: 'About our Idea'
    }
  },

  computed: {
    items () {
      return [{
        text: 'HOME',
        to: '/'
      },
      {
        text: this.title
      }
      ]
    }
  }
}
</script>
