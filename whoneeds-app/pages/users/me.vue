<template>
  <v-container fill-height>
    <v-row justify="center" align="center">
      <v-col cols="12">
        <div class="content">
          <h1>My Profile</h1>
          <v-divider />
          <div class="user-details">
            <p>
              <strong>Name:</strong>
              {{ `${user.name} ${user.surname}` }}
              <br />
              <strong>E-Mail:</strong>
              {{ user.email }}
            </p>
          </div>
        </div>
      </v-col>
      <v-col>
        <v-list>
          <v-list-group
            :value="true"
            prepend-icon="mdi-earth"
            no-action
            sub-group
            color="teal"
          >
            <template #activator>
              <v-list-item-title>Your Projects</v-list-item-title>
            </template>

            <v-list-item
              v-for="project in user.projects"
              :key="project.id"
              :to="'/projects/' + project.id"
            >
              <v-list-item-title v-text="project.name" />
              <div class="chip-group">
                <v-chip
                  v-for="(category, index) in project.categories"
                  :key="category"
                  class="single-chip"
                  :color="getChipColor(index)"
                  x-small
                >
                  {{ category }}
                </v-chip>
              </div>
              <v-divider :key="project.id" />
            </v-list-item>
          </v-list-group>
        </v-list>
      </v-col>
    </v-row>
    <v-divider />
    <v-row>
      <v-col cols="12">
        <v-btn color="primary" to="/projects/new" class="btn-create">
          <v-icon left>
            mdi-plus
          </v-icon>
          Create new project
        </v-btn>
      </v-col>
      <v-col cols="12">
        <v-btn
          to="/changePassword"
          class="ma-2 btn-reset"
          text
          color="secondary"
        >
          <v-icon left color="primary">
            mdi-refresh
          </v-icon>
          Change Password
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    user: null,
    chipColors: ["green", "blue", "purple", "red", "orange", "yellow"]
  }),
  async fetch() {
    await this.$axios.get("/projects").then(request => {
      this.user.projects = request.data;
    });
  },
  created() {
    this.user = this.$auth.user;
  },
  methods: {
    getChipColor(i) {
      return this.chipColors[i % 7];
    }
  }
};
</script>

<style scoped>
v-btn {
  margin-top: 5px;
  margin-bottom: 5px;
}
.user-details {
  margin-top: 50 px;
}
.chip-group {
  width: 100%;
}
.single-chip {
  margin-right: 2px;
}
.btn-create {
  margin-top: 40px;
  margin-bottom: -20px;
}
.btn-reset {
  margin-top: 5px;
  margin-bottom: 40px;
}
</style>
