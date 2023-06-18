<template>
    <div class="page-background">
      <top-bar :userType="'Carrier'"></top-bar>
      <h1 class="title-text">待发货运输信息</h1>
      <table class="transport-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>起点</th>
            <th>终点</th>
            <th>类型</th>
            <th>载重</th>
            <th>运单数</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in transportationList" :key="item.id">
            <td>{{ item.id }}</td>
            <td class="bold">{{ item.beginCity }}</td>
            <td class="bold">{{ item.endCity }}</td>
            <td>{{ item.type }}</td>
            <td>{{ item.weight }}</td>
            <td>{{ item.orderNum }}</td>
            <td>
              <button class="start-transport" @click="startTransportation(item.id, index)">发车</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  import TopBar from '../TopBar.vue'
  
  export default {
    components: {
    TopBar
  },
    data() {
      return {
        transportationList: [],
      };
    },
    mounted() {
      this.getTransportationWaitingInfo();
    },
    methods: {
      async getTransportationWaitingInfo() {
        try {
          const response = await axios.get("/carrier/get-transportation-waiting-info");
          if (response.data.success && response.data.code === 20000) {
            this.transportationList = response.data.data.list;
          }
        } catch (error) {
          console.error(error);
        }
      },
      async startTransportation(id, index) {
        try {
          const response = await axios.post("/carrier/start-transportation", { id });
          if (response.data.code === 20000) {
            this.transportationList.splice(index, 1);
            this.$message({
              message: "发货成功",
              type: "success",
            });
          }
        } catch (error) {
          console.error(error);
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .page-background {
    padding-top: 60px;
}
  .title-text {
    text-align:left;
  }
.transport-table {
  border-collapse: collapse;
  width: 100%;
}

.transport-table th,
.transport-table td {
  border: none;
  border-bottom: 3px solid #272343;
  text-align: center;
  padding: 8px;
  font-size: 18px;
}

.transport-table th {
  padding-bottom: 12px;
}

.bold {
  font-weight: bold;
}

.start-transport {
  background-color: #ffd803;
  border-radius: 5px;
  border: none;
  padding: 6px 12px;
  font-size: 18px;
  cursor: pointer;
}
</style>

  