<template>
<div>
  <v-app-bar id="home-app-bar" app color="white" elevation="1" height="80">
    <v-toolbar-title>
      <nuxt-link to="/" tag="span" style="cursor: pointer">
        <v-container>
          <v-row>
            <v-img :src="require('@/assets/logo.png')" class="mr-3 hidden-xs-only" contain max-width="60" width="100%" />
            <Heading>
              {{ appTitle }}
            </Heading>
          </v-row>
        </v-container>
      </nuxt-link>
    </v-toolbar-title>
    <v-spacer />
    <div>
      <v-tabs class="hidden-sm-and-down" optional>
        <v-tab v-for="item in menuItems" :key="item.title" :to="item.path" :exact="item.path === 'Home'" :ripple="false" active-class="text--primary" class="font-weight-bold" min-width="96" text>
          <v-icon left dark>
            {{ item.icon }}
          </v-icon>
          {{ item.title }}
        </v-tab>
      </v-tabs>
    </div>
    <v-app-bar-nav-icon class="hidden-md-and-up" @click="drawer = !drawer" />
  </v-app-bar>
  <NavDrawer v-model="drawer" :items="menuItems" />
</div>
</template>

<script>
export default {
  name: 'HomeAppBar',

  data: () => ({
    appTitle: 'whoneeds',
    drawer: null,
    menuItems: [{
        title: 'Home',
        path: '/',
        icon: 'mdi-home'
      },
      {
        title: 'About',
        path: '/about',
        icon: 'mdi-google-downasaur'
      },
      {
        title: 'Sign In',
        path: '/login',
        icon: 'mdi-account'
      },
      {
        title: 'Sign Up',
        path: '/register',
        icon: 'mdi-account-plus'
      }
    ],
  }),
}
</script>

<style lang="sass">
  #home-app-bar
    .v-tabs-slider
      max-width: 24px
      margin: 0 auto

    .v-tab
      &::before
        display: none
</style>
