<template>
  <v-footer padless dark min-height="72">
    <v-container>
      <v-row>
        <v-col cols="12" md="6">
          <div class="d-flex flex-wrap justify-md-start justify-center justify-md-none">
            <template v-for="(obj, index) in legal">
              <nuxt-link :to="obj.route">
                {{ obj.title }}
              </nuxt-link>
              <v-responsive v-if="index < legal.length - 1" :key="`divider-${obj}`" class="mx-4 shrink hidden-sm-and-down" max-height="24">
                <v-divider vertical />
              </v-responsive>
            </template>
            <v-btn :key="twitter.icon" :href="twitter.url" class="mx-4 shrink hidden-sm-and-down white--text" max-height="24" icon>
              <v-icon size="26px">
                {{ twitter.icon }}
              </v-icon>
            </v-btn>
          </div>
        </v-col>
        <v-col class="text-center text-md-right" cols="12" md="6">
          Copyright &copy;whoneeds
        </v-col>
      </v-row>
    </v-container>
  </v-footer>
</template>

<script>
export default {
  name: 'TheFooter',
  data: () => ({
    legal: [{
      title: 'Impressum',
      route: '/impressum'
    },
    {
      title: 'Privacy Policy',
      route: '/privacy'
    }
    ],
    twitter: { icon: 'mdi-twitter', url: 'https://twitter.com/whoneeds6' }
  })
}
</script>
