<template>
  <v-container fill-height>
    <v-row justify="center" align="center">
      <div v-if="!$fetchState.pending && project !== undefined">
        <h2>{{ project.name }}</h2>
        <v-divider />
        <div class="category-chips">
          <v-chip
            v-for="(category, index) in project.categories"
            :key="category"
            :color="getChipColor(index)"
            small
          >
            {{ category }}
          </v-chip>
        </div>
        <h4>Description</h4>
        <p>{{ project.description }}</p>
        <div v-if="project.members.length > 0">
          <h4>Members</h4>
          <ul>
            <li v-for="member in project.members" :key="member.id">
              {{ member.name }} ({{ member.email }})
            </li>
          </ul>
        </div>
        <p v-else>
          The project has no additional members yet.
        </p>

        <h4>Address</h4>
        <p>Name: {{ project.address.name }}</p>
        <p>Street: {{ project.address.street }} {{ project.address.nr }}</p>
        <p>Zip: {{ project.address.zip }}</p>
        <p>City: {{ project.address.city }}</p>
        <p>State: {{ project.address.state }}</p>
      </div>
      <div v-else>
        <h2>This project does not exist.</h2>
        <p>You can create on here</p>
        <v-btn color="primary" @click="$router.push('/projects/new')">
          Create new project
        </v-btn>
      </div>
    </v-row>
    <v-row v-if="project !== undefined">
      <v-dialog v-model="deleteDialog" max-width="290">
        <template #activator="{ on, attrs }">
          <v-btn color="error" text dark v-bind="attrs" v-on="on">
            Delete Project
          </v-btn>
        </template>
        <v-card>
          <v-card-title class="headline">
            Are you sure you want to delete this project?
          </v-card-title>
          <v-card-text>
            If you submit now the project "{{ project.name }}" will be
            permanently deleted.
          </v-card-text>
          <v-card-actions>
            <v-spacer />
            <v-btn color="secondary" text @click="deleteDialog = false">
              Cancel
            </v-btn>
            <v-btn color="error" text @click="deleteProject">
              Delete Project
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    project: undefined,
    chipColors: ['green', 'blue', 'purple', 'red', 'orange', 'yellow'],
    deleteDialog: false
  }),
  async fetch () {
    const id = this.$route.params.id
    await this.$axios
      .get(`/projects/${id}`)
      .then((response) => {
        this.project = response.data
      })
      .catch(() => {
        this.$toast.error('The given project id is unknown.')
      })
  },
  methods: {
    async deleteProject () {
      await this.$axios
        .delete(`/projects/${this.project.id}`)
        .then(() => {
          this.$toast.success('Project deleted')
          this.$router.push('/users/me')
        })
        .catch((error) => {
          if (error.response && error.response.status === 403) {
            this.$toast.error('You are not authorized to delete this project.')
          } else {
            this.$toast.error('Oops.. Something went wrong.')
          }
        })
        .finally(() => {
          this.deleteDialog = false
        })
    },
    getChipColor (i) {
      return this.chipColors[i % 7]
    }
  }
}
</script>

<style scoped>
.category-chips {
  margin-right: 10px;
  margin-top: 5px;
}
</style>
