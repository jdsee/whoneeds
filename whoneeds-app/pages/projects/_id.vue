<template>
  <v-container fill-height class="content">
    <v-overlay :value="$fetchState.pending || project == null">
      <v-progress-circular indeterminate size="64" />
    </v-overlay>

    <v-row justify="center" align="center">
      <div v-if="project !== null">
        <v-col class="boxed-heading">
          <h1>{{ project.name }}</h1>
        </v-col>
        <v-col>
          <v-chip
            v-for="(category, index) in project.categories"
            :key="category"
            class="single-chip"
            :color="getChipColor(index)"
            small
          >
            {{ category }}
          </v-chip>
        </v-col>
        <h4>Description</h4>
        <p>{{ project.description }}</p>
        <h4>Members</h4>
        <div v-if="project.members.length > 0">
          <ul>
            <li v-for="member in project.members" :key="member.id">
              {{ member.name }} ({{ member.email }})
            </li>
          </ul>
        </div>
        <p v-else>
          This project has no additional members yet.
        </p>

        <h4>Address</h4>
        Name: {{ project.address.name }}
        <br>
        Street: {{ project.address.street }} {{ project.address.nr }}
        <br>
        Zip: {{ project.address.zip }}
        <br>
        City: {{ project.address.city }}
        <br>
        State: {{ project.address.state }}
        <br>
      </div>
      <div v-else>
        <h2>This project does not exist.</h2>
        <p>You can create on here</p>
        <v-btn color="primary" to="/projects/new">
          Create new project
        </v-btn>
      </div>
    </v-row>
    <v-row v-if="project !== null" class="btn-delete">
      <v-dialog v-model="deleteDialog" max-width="290">
        <template #activator="{ on, attrs }">
          <v-btn color="error" outlined v-bind="attrs" v-on="on">
            Delete Project
          </v-btn>
        </template>
        <v-card>
          <v-card-title class="headline">
            Are you sure you want to delete this project?
          </v-card-title>
          <v-card-text>
            If you submit now the project
            <e>"{{ project.name }}"</e> will be permanently deleted.
          </v-card-text>
          <v-card-actions>
            <v-spacer />
            <v-btn color="secondary" text @click="deleteDialog = false">
              Cancel
            </v-btn>
            <v-btn color="error" outlined @click="deleteProject">
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
    project: null,
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
e {
  font-style: italic;
  color: teal;
}
.content {
  margin-top: 10px;
  margin-left: 20px;
  margin-right: 20px;
}
.theme--light .boxed-heading {
  text-align: center;
  box-shadow: 0px 2px 4px -3px teal;
  border-radius: 10px;
  border: 2px solid teal;
}
.theme--dark .boxed-heading {
  text-align: center;
  box-shadow: 0px 2px 4px -3px lightgrey;
  border-radius: 10px;
  border: 2px solid teal;
}
.chip-group {
  width: 50%;
}
.single-chip {
  margin-right: 4px;
}
.btn-delete {
  margin-top: 30px;
  margin-bottom: 10px;
}
</style>
