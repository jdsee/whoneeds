<template>
  <validation-provider
    v-slot="{ errors }"
    name="Password"
    rules="required|max:10"
  >
    <v-text-field
      v-model="password"
      tyep="password"
      :counter="10"
      :error-messages="errors"
      label="Password"
      required
    ></v-text-field>
  </validation-provider>
</template>


