<template>
  <v-layout row wrap text-xs-center fill-height align-center>
    <v-flex xs8 sm6 offset-sm3 offset-xs2>
      <h2>Create new project</h2>
      <v-container fill-height>
        <v-row>
          <h3>Details</h3>
        </v-row>
        <validation-observer ref="observer" v-slot="{ invalid }">
          <v-form
            v-model="valid"
            lazy-validation
            @submit.prevent="submitRegistration"
          >
            <v-row>
              <v-col cols="12" sm="6" md="6">
                <validation-provider
                  v-slot="{ errors }"
                  name="Project Name"
                  rules="required"
                >
                  <v-text-field
                    ref="projectNameInput"
                    v-model="project.name"
                    label="Project Name"
                    maxlength="20"
                    :error-messages="errors"
                  />
                </validation-provider>
              </v-col>
              <v-col cols="12" sm="6" md="6">
                <v-tooltip top>
                  <template #activator="{ on, attrs }">
                    <div v-bind="attrs" v-on="on">
                      <v-select
                        v-model="project.categories"
                        :items="projectCategories"
                        label="Category"
                        small-chips
                        multiple
                      />
                    </div>
                  </template>
                  <span>What field is your project meant to support?</span>
                </v-tooltip>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" sm="6" md="6">
                <validation-provider
                  v-slot="{ errors }"
                  name="Description"
                  rules="required"
                >
                  <div class="full-width">
                    <v-textarea
                      v-model="project.description"
                      label="Description"
                      maxlength="100"
                      :error-messages="errors"
                      auto-grow
                    />
                  </div>
                </validation-provider>
              </v-col>
            </v-row>
            <v-row>
              <h3>Address</h3>
            </v-row>
            <v-row>
              <v-spacer />
              <v-col cols="12" sm="6" md="6">
                <validation-provider
                  v-slot="{ errors }"
                  name="Name"
                  rules="required"
                >
                  <v-text-field
                    v-model="project.address.name"
                    label="Name"
                    maxlength="100"
                    :error-messages="errors"
                  />
                </validation-provider>
              </v-col>
              <v-col cols="12" sm="6" md="6">
                <validation-provider
                  v-slot="{ errors }"
                  name="Street"
                  rules="required"
                >
                  <v-text-field
                    v-model="project.address.street"
                    label="Street"
                    maxlength="100"
                    :error-messages="errors"
                  />
                </validation-provider>
              </v-col>
              <v-col cols="12" sm="6" md="6">
                <validation-provider
                  v-slot="{ errors }"
                  name="Number"
                  rules="required|max-value:4"
                >
                  <v-text-field
                    v-model.number="project.address.nr"
                    label="Number"
                    maxlength="5"
                    :error-messages="errors"
                  />
                </validation-provider>
              </v-col>
              <v-col cols="12" sm="6" md="6">
                <validation-provider
                  v-slot="{ errors }"
                  name="Zip-code"
                  rules="required|digits:5"
                >
                  <v-text-field
                    v-model.number="project.address.zip"
                    label="Zip-code"
                    maxlength="5"
                    :error-messages="errors"
                  />
                </validation-provider>
              </v-col>
              <v-col cols="12" sm="6" md="6">
                <validation-provider
                  v-slot="{ errors }"
                  name="City"
                  rules="required"
                >
                  <v-text-field
                    v-model="project.address.city"
                    label="City"
                    maxlength="50"
                    :error-messages="errors"
                  />
                </validation-provider>
              </v-col>
              <v-col cols="12" sm="6" md="6">
                <validation-provider
                  v-slot="{ errors }"
                  name="State"
                  rules="required"
                >
                  <v-text-field
                    v-model="project.address.state"
                    label="State"
                    maxlength="50"
                    :error-messages="errors"
                  />
                </validation-provider>
              </v-col>
              <v-spacer />
            </v-row>
            <v-row>
              <v-flex class="text-xs-center" mt-5 mb-13>
                <v-btn data-test="cancelButton" text @click="resetForm">
                  Cancel
                </v-btn>
                <v-btn
                  :disabled="invalid"
                  class="mr-4"
                  color="primary"
                  text
                  type="submit"
                >
                  Create
                </v-btn>
              </v-flex>
            </v-row>
          </v-form>
        </validation-observer>
      </v-container>
    </v-flex>
  </v-layout>
</template>

<script>
import { ValidationObserver, ValidationProvider } from 'vee-validate'

export default {
  components: {
    ValidationProvider,
    ValidationObserver
  },
  auth: true,
  data: () => ({
    dialog: true,
    valid: true,
    project: {
      name: '',
      description: '',
      categories: [],
      members: [],
      address: {
        name: '',
        street: '',
        nr: null,
        city: '',
        state: '',
        zip: null
      }
    },
    projectCategories: [
      'humanitarian',
      'refugees',
      'homeless',
      'educational',
      'medical',
      'children',
      'food',
      'environmental',
      'other'
    ]
  }),
  head: { title: 'Create project' },
  mounted () {
    this.focusProjectNameInput()
  },
  methods: {
    async submitRegistration () {
      if (this.$refs.observer.validate()) {
        await this.$axios
          .post('/projects', this.project)
          .then((response) => {
            this.$router.push('/projects/' + response.headers.location.match(/\d+$/)[0])
          })
          .catch((error) => {
            if (error.response && error.response.status === 409) {
              this.$toast.error('This project name is already in use.')
            } else {
              this.$toast.error('Oops.. Something went wrong.')
            }
            this.focusProjectNameInput()
          })
      }
    },
    resetForm () {
      this.$refs.observer.reset()
      Object.keys(this.registration).map(k => (this.registration[k] = ''))
      this.passwordConfirmation = ''
    },
    focusProjectNameInput () {
      this.$refs.projectNameInput.focus()
    }
  }
}
</script>

<style scoped>
h2,
h3 {
  margin-top: 40px;
  margin-bottom: 10px;
}
h3 {
  color: rgb(47, 88, 107);
}
v-container {
  margin-right: 100px;
}
.full-width {
  width: 500px;
}
</style>
