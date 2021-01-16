<template>
  <div class="lang-dropdown">
    <select v-model="$i18n.locale">
      <option v-key="lang in $i18n.locale"
              :key="lang.code"
              :value="lang.code">{{ lang.name }}
      </option>
    </select>
  </div>
</template>

<script>
export default {
  name: "LanguageSelector"
}
</script>

<style scoped>

</style>
