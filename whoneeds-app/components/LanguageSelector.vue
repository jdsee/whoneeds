<template>
  <div class="lang-dropdown">
    <select v-model="$i18n.locale" v-on:change="onChange">
      <option
        v-for="locale in $i18n.locales"
        :key="locale.code"
        :value="locale.code"
      >{{ locale.name }}
      </option
      >
    </select>
  </div>
</template>

<script>
import * as https from "https";
import axios from "axios";

export default {
  name: "LanguageSelector",
  methods: {
    onChange: function (event) {
      const instance = axios.create({
        httpsAgent: new https.Agent({
          rejectUnauthorized: false
        })
      });

      let res = this.$axios.post('http://localhost:9000/whoneeds/api/settings', 'd').then((response) => {
        console.log(response);
      }, (error) => {
        console.log(error);
      });
    }
  }
}
</script>

<style scoped>

</style>
