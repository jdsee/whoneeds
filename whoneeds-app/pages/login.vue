<template>
  <v-layout row wrap text-xs-center fill-height align-center>
    <v-flex xs8 sm6 offset-sm3 offset-xs2>
      <h2>Sign In</h2>

      <validation-observer ref="observer" v-slot="{ invalid }">
        <v-form @submit.prevent="submit">
          <v-layout column>
            <v-flex>
              <validation-provider
                v-slot="{ errors }"
                name="emailAddress"
                rules="required"
              >
                <v-text-field
                  ref="emailInput"
                  v-model="emailAddress"
                  :error-messages="errors"
                  label="E-mail"
                  required
                />
              </validation-provider>
            </v-flex>
            <v-flex>
              <validation-provider
                v-slot="{ errors }"
                name="Password"
                :rules="`required|max:${maxPasswordLength}`"
              >
                <v-text-field
                  v-model="password"
                  type="password"
                  :counter="maxPasswordLength"
                  :error-messages="errors"
                  label="Password"
                  required
                />
              </validation-provider>
            </v-flex>
            <v-flex class="text-xs-center" mt-5>
              <v-btn class="mr-4" type="submit" :disabled="invalid">
                submit
              </v-btn>
              <v-btn @click="clear">
                clear
              </v-btn>
            </v-flex>
          </v-layout>
        </v-form>
      </validation-observer>
    </v-flex>
  </v-layout>
</template>

// email

<script>
import { ValidationObserver, ValidationProvider } from "vee-validate";
import { mapActions } from "vuex";

export default {
  components: {
    ValidationProvider,
    ValidationObserver
  },
  data: () => ({
    emailAddress: "",
    password: "",
    maxPasswordLength: 32
  }),
  mounted() {
    this.$refs.emailInput.focus();
  },
  methods: {
    submit() {
      this.$refs.observer.validate();
      this.login({
        emailAddress: this.emailAddress,
        password: this.password
      });
      this.clear();
    },
    clear() {
      this.emailAddress = "";
      this.password = "";
      this.$refs.observer.reset();
    },
    ...mapActions({ login: "auth/login" })
  }
};
</script>
