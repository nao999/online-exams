<template>
  <div id="app">
    <router-view/>
    <el-backtop :visibility-height="60">
      <i class="el-icon-caret-top"></i>
    </el-backtop>
  </div>
</template>

<script>
// import test from './views/test/data-statistics.vue'

export default {
  name: 'App'
}
</script>


