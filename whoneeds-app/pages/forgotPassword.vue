<template>
  <v-container>
    <v-row>
      <v-col class="d-flex flex-column mx-auto col-8 col-md-5 py-12">
        <v-card class="pa-6" elevation="6">
          <v-card-title>
            Reset Your Password
          </v-card-title>
          <v-card-subtitle>
            Enter your email and we will send you a password reset link
          </v-card-subtitle>
          <validation-observer ref="observer" v-slot="{ invalid }">
            <v-card-text class="pb-0">
              <v-form>
                <validation-provider
                  v-slot="{ errors }"
                  name="E-Mail address"
                  rules="required|email"
                >
                  <v-text-field
                    ref="emailInput"
                    v-model="email"
                    :error-messages="errors"
                    label="E-mail"
                    required
                    outlined
                  />
                </validation-provider>
              </v-form>
            </v-card-text>

            <v-card-actions class="pt-0 px-4">
              <v-btn white width="fit-content" :disabled="invalid" @click="submit">
                <v-icon class="mr-3">
                  {{ forgotIcon }}
                </v-icon>Send link
              </v-btn>
            </v-card-actions>
          </validation-observer>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { ValidationObserver, ValidationProvider } from 'vee-validate'

export default {
  auth: false,
  components: {
    ValidationProvider,
    ValidationObserver
  },
  data: () => ({
    forgotIcon: 'mdi-email-send-outline',
    email: ''
  }),
  mounted () {
    this.focusEmailInput()
  },
  methods: {
    submit () {
      this.$refs.observer.validate()
      this.transfer()
      this.focusEmailInput()
      this.clear()
    },
    focusEmailInput () {
      this.$refs.emailInput.focus()
    },
    clear () {
      this.email = ''
      this.$refs.observer.reset()
    },
    transfer () {
      this.$axios.post('/resetPassword', { emailRecipient: this.email })
        .then(() => {
          this.$toast.success('If you have an account, you have received an email with further instructions.')
            .goAway(10000)
        })
        .catch(() => {
          this.$toast.success('If you have an account, you have received an email with further instructions.')
            .goAway(10000)
        })
    }
  }
}
</script>
