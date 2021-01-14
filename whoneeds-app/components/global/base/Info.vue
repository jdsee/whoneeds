<template>
  <v-theme-provider :dark="dark">
    <div>
      <base-img
        :src="require('@/assets/logo_small.png')"
        contain
        max-width="128"
        min-height="78"
        width="100%"
      />

      <base-title
        size="body-1"
        space="4"
        title="Thanks for scrolling so far"
        weight="regular"
      />

      <base-body>
        Down here you can find our Privacy policy / Impressum.
        If you disagree with anything on the site, contact us.
        I beg you.
        Please contact us, before you sue us.
      </base-body>
    </div>
  </v-theme-provider>
</template>
