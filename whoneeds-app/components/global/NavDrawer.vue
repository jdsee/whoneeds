<template>
  <v-navigation-drawer
    top
    color="#303f69"
    fixed
    app
    absolute
    temporary
    v-bind="$attrs"
    v-on="$listeners"
  >
    <v-list color="#303f69" shaped>
      <v-list-item v-for="item in items" :key="item.title" :to="item.path" color="green">
        <v-list-item-icon>
          <v-icon v-text="item.icon" />
        </v-list-item-icon>
        <v-list-item-content class="orange--text">
          <v-list-item-title v-text="item.title" />
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>
<script>
export default {
  name: 'HomeDrawer',

  props: {
    items:
      {
        type: Array,
        default: () => ([])
      }
  }
}
</script>
