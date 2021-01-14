<template>
  <header>
    <v-navigation-drawer v-model="drawer" app absolute temporary>
      <v-list nav dense>
        <v-list-item-group active-class="teal--text text--accent-9">
          <v-list-item
            v-for="item in menuItems"
            :key="item.title"
            :to="item.path"
          >
            <v-list-item-action>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-action>
            <v-list-item-content>{{ item.title }}</v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app>
      <span class="hidden-sm-and-up">
        <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      </span>
      <v-toolbar-title>
        <nuxt-link to="/" tag="span" style="cursor: pointer">
          <h3>
            {{ appTitle }}
          </h3>
        </nuxt-link>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-xs-only">
        <v-btn text v-for="item in menuItems" :key="item.title" :to="item.path">
          <v-icon left dark>{{ item.icon }}</v-icon>
          {{ item.title }}
        </v-btn>
      </v-toolbar-items>
    </v-app-bar>
  </header>
</template>

<script>
export default {
  name: "TheNavigation",
  data: () => ({
    appTitle: "whooneeds",
    drawer: false,
    menuItems: [
      { title: "Home", path: "/", icon: "mdi-home" },
      { title: "Sign In", path: "/login", icon: "mdi-account" },
      { title: "Sign Up", path: "/register", icon: "mdi-account-plus" }
    ]
  })
};
</script>
