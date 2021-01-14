<template>
  <v-content>
  <v-tabs v-model="tab" centered>
    <v-tab>Login</v-tab>
  </v-tabs>
  <v-tabs-item v-model="tab">
    <v-tab-item>
      <LoginForm></LoginForm>
    </v-tab-item>
  </v-tabs-item>
</v-content>
</template>

<script>
export default {

}
</script>

<style>

</style>