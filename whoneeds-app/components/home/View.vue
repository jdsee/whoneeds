<template>
  <v-main>
    <v-fade-transition mode="out-in">
      <nuxt />
    </v-fade-transition>
  </v-main>
</template>

<script>
export default {
  name: "BaseView"
};
</script>