<template>
  <div>
    <v-app-bar id="home-app-bar" elevation="1" height="80">
      <v-toolbar-title>
        <nuxt-link to="/" tag="span" style="cursor: pointer">
          <v-container>
            <v-row>
              <v-img
                :src="require('@/assets/logo.png')"
                class="mr-3 hidden-xs-only"
                contain
                max-width="60"
                width="100%"
              />
              <BaseHeading>
                {{ appTitle }}
              </BaseHeading>
            </v-row>
          </v-container>
        </nuxt-link>
      </v-toolbar-title>
      <v-spacer />
      <div>
        <v-tabs class="hidden-sm-and-down" optional>
          <v-tab
            v-for="item in activeMenuItems"
            :key="item.title"
            style="background-color=green;"
            :v-if="false"
            :to="item.path"
            :exact="item.path === 'Home'"
            :ripple="false"
            active-class="text--primary"
            class="font-weight-bold"
            min-width="96"
            text
            @click="item.logout ? logout() : () => {}"
          >
            <v-icon left dark>
              {{ item.icon }}
            </v-icon>
            {{ item.title }}
          </v-tab>
        </v-tabs>
      </div>
      <v-app-bar-nav-icon class="hidden-md-and-up" @click="drawer = !drawer" />
    </v-app-bar>
    <HomeDrawer v-model="drawer" :items="activeMenuItems" />
  </div>
</template>

<script>
import BaseHeading from '~/components/global/base/Heading'
import HomeDrawer from '~/components/global/NavDrawer'

export default {
  name: 'HomeAppBar',
  components: { HomeDrawer, BaseHeading },
  data: () => ({
    appTitle: 'whoneeds',
    drawer: null,
    menuItems: [
      {
        title: 'Home',
        path: '/',
        logout: false,
        icon: 'mdi-home',
        show: 'always'
      },
      {
        title: 'About',
        path: '/about',
        logout: false,
        icon: 'mdi-google-downasaur',
        show: 'always'
      },
      {
        title: 'Sign In',
        path: '/login',
        logout: false,
        icon: 'mdi-account',
        show: 'loggedOutOnly'
      },
      {
        title: 'Sign Up',
        path: '/users/new',
        logout: false,
        icon: 'mdi-account-plus',
        show: 'loggedOutOnly'
      },
      {
        title: 'Sign Out',
        path: '',
        logout: true,
        icon: 'mdi-bike',
        show: 'loggedInOnly'
      }
    ]
  }),
  computed: {
    activeMenuItems () {
      return this.menuItems.filter(
        item =>
          item.show === 'always' ||
          (item.show === 'loggedInOnly'
            ? this.$auth.loggedIn
            : !this.$auth.loggedIn)
      )
    }
  },
  methods: {
    async logout () {
      await this.$auth
        .logout()
        .then(() => this.$toast.success('You have been logged out, Ciao!'))
    }
  }
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

  .theme--light.v-tabs > .v-tabs-bar
    background-color: #F5F5F5

  .theme--dark.v-tabs > .v-tabs-bar
    background-color: #272727
</style>
