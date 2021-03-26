<template>
  <div>
    <v-tabs v-model="tab" show-arrows grow>
      <v-tabs-slider />
      <v-tab>
        <NuxtLink to="/entry/login">
          <v-icon large>
            {{ login.icon }}
          </v-icon>
          <div class="caption py-1">
            {{ login.name }}
          </div>
        </NuxtLink>
      </v-tab>
      <v-tab>
        <NuxtLink to="/entry/register">
          <v-icon large>
            {{ register.icon }}
          </v-icon>
          <div class="caption py-1">
            {{ register.name }}
          </div>
        </NuxtLink>
      </v-tab>
      <v-tab-item>
        <NuxtChild />
      </v-tab-item>
    </v-tabs>
  </div>
</template>

<script>
export default {
  data: () => ({
    tab: 0,
    login: { name: 'Sign In', icon: 'mdi-account' },
    register: { name: 'Sign Up', icon: 'mdi-account-plus' }
  })
}
</script>
