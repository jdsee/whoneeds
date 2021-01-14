<template>
  <v-app>
    <home-system-bar />

    <home-app-bar />

    <view />

    <home-footer />

    <home-settings />
  </v-app>
</template>

<script>
export default {
  name: "HomeLayout",

  components: {
    HomeAppBar: () => import("@/components/home/AppBar"),
    HomeFooter: () => import("@/components/home/Footer"),
    HomeSettings: () => import("@/components/home/Settings"),
    HomeSystemBar: () => import("@/components/home/SystemBar"),
    HomeView: () => import("@/components/home/View")
  }
};
</script>
