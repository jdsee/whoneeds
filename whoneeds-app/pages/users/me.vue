<template>
  <v-container>
    <v-row justify="center">
      <div class="content">
        <h1>My Profile</h1>
        <hr>
        <p>
          <strong>Username:</strong>
          Daniel Duesentrieb
          <!-- {{ $auth.user.username }} TODO: send username on api request -->
        </p>
        <p>
          <strong>Email:</strong>
          {{ $auth.user.email }}
        </p>
      </div>
    </v-row>
  </v-container>
</template>
