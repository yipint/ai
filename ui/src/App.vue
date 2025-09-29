<template>
  <div id="app">
    <el-container>
      <el-header>
        <div class="header">
          <h1>IMIS 管理系统</h1>
          <el-button type="primary" @click="$router.push('/users')">
            用户管理
          </el-button>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'

onMounted(() => {
  console.log('IMIS 管理系统启动')
})
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.header h1 {
  margin: 0;
  color: #409eff;
}

.el-header {
  background-color: #f5f5f5;
  border-bottom: 1px solid #e4e7ed;
}
</style>
